package com.example.owner.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.owner.entity.RunningProcess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/24.
 */
public class UninstallService extends Service {

    private PackageManager packageManager;
    private OnScanTaskListener listener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LoadService();
    }

    public void setListener(OnScanTaskListener listener) {
        this.listener = listener;
    }

    public class LoadService extends Binder {
        public UninstallService getService() {
            return UninstallService.this;
        }

    }

    public void scanRunningProgress() {
        new MyAsyncTask().execute();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        packageManager = getPackageManager();
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, List<RunningProcess>> {

        @Override
        protected List<RunningProcess> doInBackground(Void... voids) {
            List<RunningProcess> processes = new ArrayList<>();
            List<PackageInfo> infos = packageManager.getInstalledPackages(0);
            int count = 0;
            publishProgress(infos.size(), count);
            if (infos != null) {
                for (PackageInfo p : infos) {
                    publishProgress(infos.size(), ++count);
                    RunningProcess process = new RunningProcess();
                    process.setPackageName(p.packageName);
                    process.setVersion(p.versionName);
                    ApplicationInfo appinfo = p.applicationInfo;
                    Drawable icon = appinfo.loadIcon(packageManager);
                    process.setIcon(icon);
                    //process.setMemorySize();
                    if ((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        process.setIsSystem(true);
                    } else {
                        process.setIsSystem(false);
                    }
                    process.setUid(appinfo.uid);
                    process.setAppName(appinfo.loadLabel(packageManager).toString());
                    process.setProcessName(appinfo.processName);
                    processes.add(process);
                }
            }
            return processes;
        }

        @Override
        protected void onPostExecute(List<RunningProcess> runningProcesses) {
            super.onPostExecute(runningProcesses);
            if (listener != null) {
                listener.onAfterScan(runningProcesses);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listener != null) {
                listener.onStartScan();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int total = values[0];
            int current = values[1];
            int progress = (int) (current * 1.0 / total * 100);
            if (listener != null) {
                listener.onProcessScan(total, current, progress);
            }
        }
    }

    public interface OnScanTaskListener {
        public void onStartScan();

        public void onProcessScan(Integer... values);

        public void onAfterScan(List<RunningProcess> processes);
    }
}
