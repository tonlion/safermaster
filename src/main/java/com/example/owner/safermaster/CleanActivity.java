package com.example.owner.safermaster;

import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.owner.adapter.CacheAdapter;
import com.example.owner.entity.Cache;
import com.example.owner.tools.StorageUtil;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CleanActivity extends AppCompatActivity {

    private Method getPackageSizeInfo;
    private long totalSize;
    private Method freeStorageAndNotify;
    private Button click;
    private ListView items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        items = (ListView) findViewById(R.id.clear_list);
        click = (Button) findViewById(R.id.clear_clear);
        totalSize = 0;
        new MyAsyncTask().execute();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    freeStorageAndNotify = PackageManager.class.getDeclaredMethod
                            ("freeStorageAndNotify", Long.TYPE, IPackageDataObserver.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                PackageManager pm = getPackageManager();
                File data = Environment.getDataDirectory();
                if (data == null) {
                    return;
                }
                StatFs statFs = new StatFs(data.getAbsolutePath());
                long total = statFs.getBlockCount() * statFs.getBlockSize();
                try {
                    freeStorageAndNotify.invoke(pm, total, IPackageDataObserver.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getappCache() {

    }

    public void clean() {

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, List<Cache>> {

        @Override
        protected List<Cache> doInBackground(Void... voids) {
            final PackageManager pm = getPackageManager();
            try {
                getPackageSizeInfo = PackageManager.class.getDeclaredMethod
                        ("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                getPackageSizeInfo.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            final List<Cache> caches = new ArrayList<>();
            List<PackageInfo> infos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            final CountDownLatch countDownLatch = new CountDownLatch(infos.size());
            for (final PackageInfo p : infos) {
                try {
                    getPackageSizeInfo.invoke(pm, p.packageName, new IPackageStatsObserver.Stub() {
                        //异步方法
                        @Override
                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                            if (succeeded && pStats != null) {
                                Cache cache = new Cache();
                                cache.setCacheSize(StorageUtil.convertStorage(pStats.cacheSize));
                                cache.setIcon(p.applicationInfo.loadIcon(pm));
                                cache.setAppName(p.applicationInfo.loadLabel(pm).toString());
                                totalSize += pStats.cacheSize;
                                caches.add(cache);
                            }
                            countDownLatch.countDown();

                        }
                    });
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return caches;
        }

        @Override
        protected void onPostExecute(List<Cache> caches) {
            super.onPostExecute(caches);
            CacheAdapter adapter = new CacheAdapter(caches, CleanActivity.this);
            items.setAdapter(adapter);
        }
    }
}
