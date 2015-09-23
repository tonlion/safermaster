package com.example.owner.safermaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.dao.BlackListDao;
import com.example.owner.entity.BlackList;

import java.util.List;

/**
 * Created by Owner on 2015/9/23.
 */
public class BlackListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_view);
        ListView listView = (ListView) findViewById(R.id.list_view);
        TextView textView = new TextView(this);
        BlackListDao dao = new BlackListDao(this);
        List<BlackList> lists = dao.findAll();
        String text = "";
        for (BlackList b : lists) {
            text += b.getName() + "电话" + b.getPhoneNumber() + "\n";
        }
        textView.setText(text);
        listView.addHeaderView(textView);
    }
}
