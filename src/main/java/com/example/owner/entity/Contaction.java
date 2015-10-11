package com.example.owner.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 2015/9/23.
 */
public class Contaction {
    private int contactId; //id
    private String desplayName;//姓名
    private String phoneNum; // 电话号码
    private String sortKey; // 排序用的
    private Long photoId; // 图片id
    private String lookUpKey;
    private boolean selected;
    private String formattedNumber;
    private String pinyin; // 姓名拼音

    public Contaction() {

    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getContactId() {
        return contactId;
    }

    public String getDesplayName() {
        return desplayName;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setDesplayName(String desplayName) {
        this.desplayName = desplayName;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

}
