package com.example.owner.receiver;

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

import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
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
                    dao.insertValues(new BlackList("18363821327", "刘静"));
                    //查看黑名单是否有这个人
                    List<BlackList> lists = dao.findValues(
                            new String[]{TableManager.BlackListTable.COL_PHONE_NUMBER},
                            new String[]{sendNumber.substring(3)});
                    if (lists.size() != 0 && sendNumber != null) {
                        String messageBody = smsMessage.getMessageBody();
                        long time = System.currentTimeMillis();
                        SMS sms = new SMS(sendNumber, messageBody, time + "");
                        SMSDao sDao = new SMSDao(context);
                        //如果有，将此信息插入到数据库
                        sDao.insertValues(sms);
                        abortBroadcast();//广播到此为止，不再向下传播
                    }
                }
            }
        }
    }
}
