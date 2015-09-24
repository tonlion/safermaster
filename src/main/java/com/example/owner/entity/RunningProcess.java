package com.example.owner.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Owner on 2015/9/17.
 */
public class RunningProcess {

    private int pid;
    private int uid;
    private String processName;
    private String appName;     //文件名
    private long memorySize;    //所占内存
    private Drawable icon;      //图标
    private boolean isSystem;   //是否系统应用
    private String version;     //版本号
    private String packageName; //包名

    public RunningProcess() {
    }

    public int getPid() {
        return this.pid;
    }

    public int getUid() {
        return this.uid;
    }

    public String getProcessName() {
        return this.processName;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public long getMemorySize() {
        return memorySize;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}