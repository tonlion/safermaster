package com.example.owner.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.database.DBHelper;

public class GlobalDao {

    private DBHelper helper;
    private String tableName;

    /**
     * @param context
     * @param tableName ָ���ı���
     */
    public GlobalDao(Context context, String tableName) {
        super();
        this.helper = new DBHelper(context);
        this.tableName = tableName;
    }

    /**
     * ɾ��ָ�������ļ�¼
     *
     * @param delete
     * @param values
     */
    public void deleteUser(String[] delete, Object[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String deleteSQL = "delete from " + tableName + " where ";
        for (int i = 0; i < delete.length - 1; i++) {
            deleteSQL += delete[i] + "=? and ";
        }
        deleteSQL += delete[delete.length - 1] + "=?";
        database.execSQL(deleteSQL, values);
        database.close();
    }

    /**
     * ����������±��е�ָ�����
     *
     * @param set
     * @param update
     * @param values
     */
    public void updateMes(String[] set, String[] update, Object[] values) {
        SQLiteDatabase database = helper.getReadableDatabase();
        String updataSQL = "update " + tableName + " set ";
        for (int i = 0; i < set.length - 1; i++) {
            updataSQL += set[i] + "=?, ";
        }
        updataSQL += set[set.length - 1] + "=?";
        updataSQL += " where ";
        for (int i = 0; i < update.length - 1; i++) {
            updataSQL += update[i] + "=? and ";
        }
        updataSQL += update[update.length - 1] + "=?";
        database.execSQL(updataSQL, values);
        database.close();
    }
}
