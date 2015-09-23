package com.example.owner.safermaster;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.example.owner.adapter.CallLogAdapter;
import com.example.owner.entity.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class CallLogActivity extends AppCompatActivity {

    private ListView items;
    private Button addToList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        items = (ListView) findViewById(R.id.contact_list_view);
        List<CallLog> logs = readLog();
        CallLogAdapter adapter = new CallLogAdapter(this, logs);
        items.setAdapter(adapter);
    }

    private List<CallLog> readLog() {
        List<CallLog> logs = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri uri = android.provider.CallLog.Calls.CONTENT_URI;
        Cursor cursor = resolver.query(uri, null, null, null,
                android.provider.CallLog.Calls.DATE + " DESC");
        while (cursor.moveToNext()) {
            CallLog log = new CallLog();
            log.setName(cursor.getString(
                    cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME)));
            log.setNumber(cursor.getString(
                    cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER)));
            log.setDuration(cursor.getString(
                    cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION)));
            log.setDate(cursor.getString(
                    cursor.getColumnIndex(android.provider.CallLog.Calls.DATE)));
            switch (Integer.parseInt(cursor.getString(
                    cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE)))) {
                case android.provider.CallLog.Calls.INCOMING_TYPE:
                    log.setType("呼入");
                    break;
                case android.provider.CallLog.Calls.OUTGOING_TYPE:
                    log.setType("呼出");
                    break;
                case android.provider.CallLog.Calls.MISSED_TYPE:
                    log.setType("未接");
                    break;
                default:
                    log.setType("挂断");
                    break;
            }
            logs.add(log);
        }
        return logs;
    }
}
