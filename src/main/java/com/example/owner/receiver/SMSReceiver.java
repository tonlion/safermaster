package com.example.owner.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.example.owner.dao.BlackListDao;
import com.example.owner.dao.SMSDao;
import com.example.owner.database.TableManager;
import com.example.owner.entity.BlackList;
import com.example.owner.entity.SMS;
import com.example.owner.safermaster.CrankActivity;
import com.example.owner.safermaster.R;

import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class SMSReceiver extends BroadcastReceiver {

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage smsMessage : messages) {
                    String sendNumber = smsMessage.getOriginatingAddress();
                    BlackListDao dao = new BlackListDao(context);
                    //查看黑名单是否有这个人
                    List<BlackList> lists = dao.findValues(
                            new String[]{TableManager.BlackListTable.COL_PHONE_NUMBER},
                            new String[]{sendNumber});
                    if (lists.size() != 0 && lists != null && sendNumber != null) {
                        String messageBody = smsMessage.getMessageBody();
                        long time = System.currentTimeMillis();
                        SMS sms = new SMS(sendNumber, messageBody, time + "");
                        SMSDao sDao = new SMSDao(context);
                        //如果有，将此信息插入到数据库
                        sDao.insertValues(sms);
                        //广播信息
                        Notification.Builder builder = new Notification.Builder(context);
                        builder.setContentTitle("骚扰短信");
                        builder.setContentText("来自" + sendNumber + "的一条信息");
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

                        abortBroadcast();//广播到此为止，不再向下传播
                    }
                }
            }
        }
    }
}
