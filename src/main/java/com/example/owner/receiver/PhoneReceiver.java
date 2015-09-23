package com.example.owner.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.example.owner.dao.BlackListDao;
import com.example.owner.dao.PhoneDao;
import com.example.owner.database.TableManager;
import com.example.owner.entity.BlackList;
import com.example.owner.entity.Phone;
import com.example.owner.safermaster.CrankActivity;
import com.example.owner.safermaster.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class PhoneReceiver extends BroadcastReceiver {
    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            //来电，响铃
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);//得到电话好吗
                BlackListDao dao = new BlackListDao(context);
                //查看黑名单是否有这个人
                List<BlackList> lists = dao.findValues(
                        new String[]{TableManager.BlackListTable.COL_PHONE_NUMBER},
                        new String[]{phoneNumber});
                if (lists.size() != 0 && lists != null && phoneNumber != null) {
                    ITelephony i = getTelephony(context);
                    try {
                        i.endCall();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //插入数据库
                    long time = System.currentTimeMillis();
                    String name = lists.get(0).getName();
                    String attribution = "归属地";
                    Phone p = new Phone(attribution, time + "", name, phoneNumber);
                    PhoneDao pDao = new PhoneDao(context);
                    pDao.insertValues(p);
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setContentTitle("骚扰电话");
                    builder.setContentText("来自" + attribution + "的" + phoneNumber);
                    builder.setSmallIcon(R.drawable.android);
                    Intent in = new Intent(context, CrankActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("type", 1);
                    in.putExtra("type", "bundle");
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);
                    manager.notify(0, builder.build());
                }
            }
        }
    }

    private static ITelephony getTelephony(Context context) {
        ITelephony iTelephony = null;
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telephonyManager, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return iTelephony;
    }
}
