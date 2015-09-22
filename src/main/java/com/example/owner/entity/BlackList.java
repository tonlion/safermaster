package com.example.owner.entity;

/**
 * Created by Owner on 2015/9/22.
 */
public class BlackList {
    private String phoneNumber;
    private String name;

    public BlackList(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public BlackList() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
