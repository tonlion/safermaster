package com.example.owner.entity;

/**
 * Created by Owner on 2015/9/22.
 */
public class SMS {
    private String phoneNumber;
    private String date;
    private String message;

    public SMS(String phoneNumber, String message, String date) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }
}
