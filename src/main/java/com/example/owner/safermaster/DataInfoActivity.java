package com.example.owner.safermaster;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.owner.adapter.CacheAdapter;
import com.example.owner.dao.LiuDao;
import com.example.owner.entity.BlackList;
import com.example.owner.entity.Cache;
import com.example.owner.entity.LiuLiang;
import com.example.owner.entity.RunningProcess;
import com.example.owner.service.DataInfoService;
import com.example.owner.tools.StorageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/25.
 */
public class DataInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        startService(new Intent(this, DataInfoService.class));
        findViewById(R.id.clear_clear).setVisibility(View.GONE);
        ListView listView = (ListView) findViewById(R.id.clear_list);
        LiuDao dao = new LiuDao(this);
        List<LiuLiang> lius = dao.findAll();
        Toast.makeText(this, lius.size() + "", Toast.LENGTH_SHORT).show();
        List<Cache> lists = new ArrayList<>();
        for (LiuLiang liu : lius) {
            Cache list = new Cache();
            list.setAppName(liu.getName() + " 类型:" + liu.getType());
            list.setCacheSize(StorageUtil.convertStorage(Long.parseLong(liu.getData())));
            list.setIcon(getResources().getDrawable(R.drawable.android));
            lists.add(list);
        }
        CacheAdapter adapter = new CacheAdapter(lists, this);
        listView.setAdapter(adapter);
    }
}
