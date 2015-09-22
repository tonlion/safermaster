package com.example.owner.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.owner.entity.RunningProcess;
import com.example.owner.safermaster.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/9/21.
 */
public class ClearService extends Service {

    private ActivityManager activityManager;
    private PackageManager packageManager;
    private OnScanTaskListener listener;

    public void setListener(OnScanTaskListener listener) {
        this.listener = listener;
    }

    public class LoadService extends Binder {
        public ClearService getService() {
            return ClearService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LoadService();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        packageManager = getPackageManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void scanRunningProgress() {
        new MyAsyncTask().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, List<RunningProcess>> {

        @Override
        protected List<RunningProcess> doInBackground(Void... voids) {
            ////获取当前正在运行的APPProcess，所有正在运行的APP集合
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos =
                    activityManager.getRunningAppProcesses();
            List<RunningProcess> processes = new ArrayList<>();
            int count = 0;
            publishProgress(runningAppProcessInfos.size(), count);
            for (ActivityManager.RunningAppProcessInfo r : runningAppProcessInfos) {
                publishProgress(runningAppProcessInfos.size(), ++count);
                RunningProcess rp = new RunningProcess();
                rp.setPid(r.pid);
                rp.setUid(r.uid);
                rp.setProcessName(r.processName);
                //得到正常的名字,显示信息
                try {
                    ApplicationInfo applicationInfo =
                            packageManager.getApplicationInfo(r.processName, 0);
                    rp.setAppName(applicationInfo.loadLabel(packageManager).toString());//设置appname
                    Drawable icon = applicationInfo.loadIcon(packageManager);
                    rp.setIcon(icon);//设置图标
                    //是否为系统应用
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        rp.setIsSystem(true);
                    } else {
                        rp.setIsSystem(false);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    //得到应用中服务名，并给相应的服务设置与之对应的app ICON
                    if (r.processName.indexOf(":") != -1) {
                        String packageName = r.processName.substring(0, r.processName.indexOf(":"));
                        ApplicationInfo applicationInfo = null;
                        //得到所有的安装和已经卸载的应用
                        List<ApplicationInfo> applicationInfos =
                                packageManager.getInstalledApplications(
                                        PackageManager.GET_UNINSTALLED_PACKAGES);
                        for (ApplicationInfo info : applicationInfos) {
                            if (info.processName.equals(packageName)) {
                                applicationInfo = info;
                                break;
                            }
                        }
                        if (applicationInfo != null) {
                            Drawable icon = applicationInfo.loadIcon(packageManager);
                            rp.setIcon(icon);
                            rp.setAppName(applicationInfo.loadLabel(packageManager).toString());
                        } else {//没有找到对应的
                            rp.setAppName(r.processName);
                            rp.setIcon(getResources().getDrawable(R.drawable.android));
                        }
                    } else {//没有 ： 后面的名字
                        rp.setAppName(r.processName);
                        rp.setIcon(getResources().getDrawable(R.drawable.android));
                    }
                }
                //获取app的大小
                Debug.MemoryInfo[] info = activityManager.getProcessMemoryInfo(new int[]{r.pid});
                long memorySize = info[0].getTotalPrivateDirty() * 1024;
                rp.setMemorySize(memorySize);
                processes.add(rp);
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
