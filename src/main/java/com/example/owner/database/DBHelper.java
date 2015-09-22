package com.example.owner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Owner on 2015/9/22.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String NAME = "safer.db";
    private static final int VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TableManager.SMSTable.createTable());
        sqLiteDatabase.execSQL(TableManager.PhoneTable.createTable());
        sqLiteDatabase.execSQL(TableManager.BlackListTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
