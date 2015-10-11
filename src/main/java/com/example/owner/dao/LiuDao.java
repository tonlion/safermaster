package com.example.owner.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.database.DBHelper;
import com.example.owner.database.TableManager;
import com.example.owner.entity.LiuLiang;
import com.example.owner.entity.SMS;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/22.
 */
public class LiuDao {

    private DBHelper helper;
    private Context context;

    public LiuDao(Context context) {
        helper = new DBHelper(context);
        this.context = context;
    }

    public void insertValues(LiuLiang liuLiang) throws Exception {
        SQLiteDatabase database = helper.getWritableDatabase();
        String sql = "insert into " + TableManager.LiuTable.TABLE_NAME + " values(?,?,?,?)";
        database.execSQL(sql, new Object[]{
                liuLiang.getUid(), liuLiang.getName(), liuLiang.getType(), liuLiang.getData()});
        database.close();
    }

    /**
     * 找到符合条件的
     *
     * @param select
     * @param values
     * @return
     */
    public List<LiuLiang> findValues(String[] select, String[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        List<LiuLiang> smses = new ArrayList<>();
        String findSQL = "select * from " + TableManager.LiuTable.TABLE_NAME;
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
                    cursor.getString(cursor.getColumnIndex(TableManager.LiuTable.COL_PHONE_UID));
            String name = cursor.getString(cursor.getColumnIndex(TableManager.LiuTable.COL_APP_NAME));
            String message = cursor.getString(cursor.getColumnIndex(TableManager.LiuTable.COL_COME_TYPE));
            String date = cursor.getString(cursor.getColumnIndex(TableManager.LiuTable.COL_DATA));
            LiuLiang sms = new LiuLiang(phoneNumber, name, message, date);
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
    public List<LiuLiang> findAll() {
        return findValues(null, null);
    }
}
