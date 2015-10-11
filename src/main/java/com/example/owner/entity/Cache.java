package com.example.owner.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Owner on 2015/9/29.
 */
public class Cache {

    private String appName;
    private String packageName;
    private Drawable icon;
    private boolean isSys;
    private String cacheSize;

    public void setCacheSize(String cacheSize) {
        this.cacheSize = cacheSize;
    }

    public String getCacheSize() {
        return cacheSize;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setIsSys(boolean isSys) {
        this.isSys = isSys;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public boolean isSys() {
        return isSys;
    }

    public String getPackageName() {
        return packageName;
    }
}
