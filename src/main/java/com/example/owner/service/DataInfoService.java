package com.example.owner.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.owner.dao.GlobalDao;
import com.example.owner.dao.LiuDao;
import com.example.owner.database.TableManager;
import com.example.owner.entity.LiuLiang;

import java.util.List;

/**
 * Created by Owner on 2015/9/25.
 */
public class DataInfoService extends Service {

    private static final int REF_MESSAGE = 1;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REF_MESSAGE:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            readData();
                        }
                    }, 1000 * 60);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        readData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void readData() {
        //判断网络类型
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        //判断当前的网络连接类型
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (!info.isAvailable())
            return;
        //网络连接有效
        //获取当前网络类型
        String type = "";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            //流量
            type = "m";

        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            type = "w";
        }
        //读取目前为止的数据总量
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> infoss = packageManager.getInstalledPackages(
                PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
        for (PackageInfo info1 : infoss) {
            int uid = info1.applicationInfo.uid;
            long total = TrafficStats.getUidRxBytes(uid) +
                    TrafficStats.getUidTxBytes(uid);
            //UID，网络类型，数据大小
            LiuDao dao = new LiuDao(DataInfoService.this);
            try {
                dao.insertValues(new LiuLiang(uid + "",
                        info1.applicationInfo.loadLabel(packageManager).toString(),
                        type, total + ""));
            } catch (Exception e) {
                e.printStackTrace();
                GlobalDao globalDao = new GlobalDao(this, TableManager.LiuTable.TABLE_NAME);
                globalDao.updateMes(new String[]{TableManager.LiuTable.COL_DATA}
                        , new String[]{TableManager.LiuTable.COL_PHONE_UID}, new Object[]{total, uid});
            }
        }
        handler.sendEmptyMessage(REF_MESSAGE);

    }
}
