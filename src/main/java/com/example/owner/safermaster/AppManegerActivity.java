package com.example.owner.safermaster;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.internal.telephony.ITelephony;
import com.daimajia.swipe.SwipeLayout;
import com.example.owner.adapter.DelAppAdapter;
import com.example.owner.entity.RunningProcess;
import com.example.owner.service.ClearService;
import com.example.owner.service.UninstallService;

import java.util.List;

public class AppManegerActivity extends AppCompatActivity implements
        UninstallService.OnScanTaskListener {

    private LinearLayout container;
    private ProgressBar bar;
    private TextView text;
    private UninstallService service;
    private ListView items;
    private DelAppAdapter adapter;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((UninstallService.LoadService) iBinder).getService();
            service.setListener(AppManegerActivity.this);
            service.scanRunningProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_maneger);
        container = (LinearLayout) findViewById(R.id.app_container);
        bar = (ProgressBar) container.findViewById(R.id.app_progressbar);
        text = (TextView) container.findViewById(R.id.app_progress_text);
        items = (ListView) findViewById(R.id.app_listview);
        Intent intent = new Intent(this, UninstallService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_maneger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartScan() {
        container.setVisibility(View.VISIBLE);
        text.setText("正在扫描");
        bar.setProgress(0);
    }

    @Override
    public void onProcessScan(Integer... values) {
        int total = values[0];
        int current = values[1];
        int process = values[2];
        text.setText(current + "/" + total);
        bar.setProgress(process);
    }

    @Override
    public void onAfterScan(List<RunningProcess> processes) {
        adapter = new DelAppAdapter(AppManegerActivity.this, processes);
        items.setAdapter(adapter);
        container.setVisibility(View.GONE);
    }
}
