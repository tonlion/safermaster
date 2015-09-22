package com.example.owner.entity;

/**
 * Created by Owner on 2015/9/22.
 */
public class Phone {
    private String phoneNumber;
    private String name;
    private String date;
    private String attribution;

    public Phone() {
    }

    public Phone(String attribution, String date, String name, String phoneNumber) {
        this.attribution = attribution;
        this.date = date;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getAttribution() {
        return attribution;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
