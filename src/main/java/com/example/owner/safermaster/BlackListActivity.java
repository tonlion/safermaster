package com.example.owner.safermaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owner.adapter.CacheAdapter;
import com.example.owner.dao.BlackListDao;
import com.example.owner.entity.BlackList;
import com.example.owner.entity.Cache;

import java.util.ArrayList;
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
        BlackListDao dao = new BlackListDao(this);
        List<BlackList> lists = dao.findAll();
        List<Cache> caches = new ArrayList<>();
        for (BlackList b : lists) {
            Cache cache = new Cache();
            cache.setAppName(b.getName());
            cache.setCacheSize(b.getPhoneNumber());
            cache.setIcon(getResources().getDrawable(R.drawable.android));
            caches.add(cache);
        }
        CacheAdapter adapter = new CacheAdapter(caches, this);
        listView.setAdapter(adapter);
    }
}
