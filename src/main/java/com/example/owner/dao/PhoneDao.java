package com.example.owner.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.database.DBHelper;
import com.example.owner.database.TableManager;
import com.example.owner.entity.Phone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class PhoneDao {

    private DBHelper helper;
    private Context context;

    public PhoneDao(Context context) {
        helper = new DBHelper(context);
        this.context = context;
    }

    public void insertValues(Phone phone) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql = "insert into " + TableManager.PhoneTable.TABLE_NAME + " values(?,?,?,?)";
        try {
            database.execSQL(sql, new Object[]{
                    phone.getPhoneNumber(), phone.getName(),
                    phone.getDate(), phone.getAttribution()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
    }

    public List<Phone> findValues(String[] select, String[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<Phone> phones = new ArrayList<Phone>();
        String findSQL = "select * from " + TableManager.PhoneTable.TABLE_NAME;
        if (!(select == null || select.length == 0)) {
            findSQL += " where ";
            for (int i = 0; i < select.length - 1; i++) {
                findSQL += select[i] + "=? and ";
            }
            findSQL += select[select.length - 1] + "=?";
        }
        Cursor cursor = database.rawQuery(findSQL, values);
        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(
                    TableManager.PhoneTable.COL_PHONE_NUMBER));
            String name = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_COME_NAME));
            String date = cursor.getString(cursor.getColumnIndex(TableManager.PhoneTable.COL_DATE));
            String attribution = cursor.getString(
                    cursor.getColumnIndex(TableManager.PhoneTable.COL_ATTRIBUTION));
            Phone phone = new Phone(attribution, date, name, phoneNumber);
            phones.add(phone);
        }
        cursor.close();
        database.close();
        return phones;
    }

    public List<Phone> findAll() {
        return findValues(null, null);
    }
}
