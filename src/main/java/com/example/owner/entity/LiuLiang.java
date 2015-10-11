package com.example.owner.entity;

/**
 * Created by Owner on 2015/9/29.
 */
public class LiuLiang {

    private String uid;
    private String type;
    private String data;
    private String name;

    public String getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LiuLiang(String data, String type, String uid) {
        this.data = data;
        this.type = type;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiuLiang(String data, String name, String type, String uid) {
        this.data = data;
        this.name = name;
        this.type = type;
        this.uid = uid;
    }
}
