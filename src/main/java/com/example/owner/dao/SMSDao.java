package com.example.owner.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.database.DBHelper;
import com.example.owner.database.TableManager;
import com.example.owner.entity.SMS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class SMSDao {

    private DBHelper helper;
    private Context context;

    public SMSDao(Context context) {
        helper = new DBHelper(context);
        this.context = context;
    }

    public void insertValues(SMS sms) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql = "insert into " + TableManager.SMSTable.TABLE_NAME + " values(?,?,?)";
        try {
            database.execSQL(sql, new Object[]{
                    sms.getPhoneNumber(), sms.getMessage(), sms.getDate()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
    }

    /**
     * 找到符合条件的
     *
     * @param select
     * @param values
     * @return
     */
    public List<SMS> findValues(String[] select, String[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<SMS> smses = new ArrayList<SMS>();
        String findSQL = "select * from " + TableManager.SMSTable.TABLE_NAME;
        if (!(select == null || select.length == 0)) {
            findSQL += " where ";
            for (int i = 0; i < select.length - 1; i++) {
                findSQL += select[i] + "=? and ";
            }
            findSQL += select[select.length - 1] + "=?";
        }
        Cursor cursor = database.rawQuery(findSQL, values);
        while (cursor.moveToNext()) {
            String phoneNumber =
                    cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_PHONE_NUMBER));
            String message = cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_MESSAGE));
            String date = cursor.getString(cursor.getColumnIndex(TableManager.SMSTable.COL_DATE));
            SMS sms = new SMS(phoneNumber, message, date);
            smses.add(sms);
        }
        cursor.close();
        database.close();
        return smses;
    }

    /**
     * 找到所有信息
     *
     * @return
     */
    public List<SMS> findAll() {
        return findValues(null, null);
    }
}
