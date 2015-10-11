package com.example.owner.entity;

/**
 * Created by Owner on 2015/9/23.
 */
public class CallLog {

    private String number;
    private String type;
    private String date;
    private String name;
    private String duration;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public String getDate() {
        return date;
    }

    public String getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CallLog() {
    }
}
