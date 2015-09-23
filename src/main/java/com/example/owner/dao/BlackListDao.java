package com.example.owner.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.database.DBHelper;
import com.example.owner.database.TableManager;
import com.example.owner.entity.BlackList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class BlackListDao {

    private DBHelper helper;
    private Context context;

    public BlackListDao(Context context) {
        this.context = context;
        helper = new DBHelper(context);
    }

    public void insertValues(BlackList blackList) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql = "insert into " + TableManager.BlackListTable.TABLE_NAME + " values(?,?)";
        try {
            database.execSQL(sql, new Object[]{
                    blackList.getPhoneNumber(), blackList.getName()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
    }

    public List<BlackList> findAll() {
        return findValues(null, null);
    }

    public List<BlackList> findValues(String[] select, String[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<BlackList> blackLists = new ArrayList<BlackList>();
        String findSQL = "select * from " + TableManager.BlackListTable.TABLE_NAME;
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
                    cursor.getString(cursor.getColumnIndex(TableManager.BlackListTable.COL_PHONE_NUMBER));
            String name =
                    cursor.getString(cursor.getColumnIndex(TableManager.BlackListTable.COL_COME_NAME));
            BlackList p = new BlackList(name, phoneNumber);
            blackLists.add(p);
        }
        cursor.close();
        database.close();
        return blackLists;
    }
}
