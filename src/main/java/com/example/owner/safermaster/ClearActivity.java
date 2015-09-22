package com.example.owner.safermaster;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.adapter.ClearAdapter;
import com.example.owner.entity.RunningProcess;
import com.example.owner.service.ClearService;
import com.example.owner.tools.StorageUtil;

import java.util.ArrayList;
import java.util.List;

public class ClearActivity extends AppCompatActivity implements
        ClearService.OnScanTaskListener, ClearAdapter.OnChooseListener {

    private ListView items;
    private ProgressBar progressBar;
    private TextView textView;
    private LinearLayout progressContainer;
    private ClearService service;
    private ClearAdapter adapter;
    private List<RunningProcess> processes;
    private List<RunningProcess> clearProcesses;
    private ActivityManager activityManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((ClearService.LoadService) iBinder).getService();
            service.setListener(ClearActivity.this);
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
        setContentView(R.layout.activity_clear);
        items = (ListView) findViewById(R.id.clear_list);
        progressContainer = (LinearLayout) findViewById(R.id.progress_conteainer);
        progressBar = (ProgressBar) findViewById(R.id.progress_progressBar);
        textView = (TextView) findViewById(R.id.progress_text);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        Intent intent = new Intent(this, ClearService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
        processes = new ArrayList<>();
        clearProcesses = new ArrayList<>();
        adapter = new ClearAdapter(ClearActivity.this, processes);
        items.setAdapter(adapter);
        adapter.setListener(this);
        //设置点击清理事件
        findViewById(R.id.clear_clear).setOnClickListener(new View.OnClickListener() {
            int totalMemory = 0;

            @Override
            public void onClick(View view) {
                processes.removeAll(clearProcesses);
                for (RunningProcess p : clearProcesses) {
                    activityManager.killBackgroundProcesses(p.getProcessName());
                    totalMemory += p.getMemorySize();
                }
                Toast.makeText(getApplicationContext(),
                        "总共节省了" + StorageUtil.convertStorage(totalMemory) + "内存",
                        Toast.LENGTH_SHORT).show();
                totalMemory = 0;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clear, menu);
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
        progressContainer.setVisibility(View.VISIBLE);
        textView.setText("正在扫描");
        progressBar.setProgress(0);
    }

    @Override
    public void onProcessScan(Integer... values) {
        int total = values[0];
        int current = values[1];
        int process = values[2];
        textView.setText(current + "/" + total);
        progressBar.setProgress(process);
    }

    @Override
    public void onAfterScan(List<RunningProcess> processes) {
        this.processes.addAll(processes);
        adapter.notifyDataSetChanged();
        progressContainer.setVisibility(View.GONE);
    }

    @Override
    public void addItem(RunningProcess process) {
        clearProcesses.add(process);
    }

    @Override
    public void deleteItem(RunningProcess process) {
        clearProcesses.remove(process);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
//        unbindService(connection);
    }
}
