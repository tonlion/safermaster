package com.example.owner.safermaster;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.owner.adapter.CallLogAdapter;
import com.example.owner.entity.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class ShortMessageActivity extends AppCompatActivity {

    private ListView items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        items = (ListView) findViewById(R.id.contact_list_view);
        List<CallLog> logs = readMessages();
        CallLogAdapter adapter = new CallLogAdapter(this, logs);
        items.setAdapter(adapter);
    }

    private List<CallLog> readMessages() {
        List<CallLog> logs = new ArrayList<>();
        final String SMS_URI_ALL = "content://sms/";
        Uri uri = Uri.parse(SMS_URI_ALL);
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, "date DESC");
        while (cursor.moveToNext()) {
            CallLog log = new CallLog();
            log.setName(cursor.getString(cursor.getColumnIndex("address")));
            log.setDate(cursor.getString(cursor.getColumnIndex("date")));
            log.setType(cursor.getString(cursor.getColumnIndex("body")));
            logs.add(log);
        }
        return logs;
    }
}
