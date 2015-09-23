package com.example.owner.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 2015/9/23.
 */
public class Contaction implements Parcelable {
    private int contactId; //id
    private String desplayName;//姓名
    private String phoneNum; // 电话号码
    private String sortKey; // 排序用的
    private Long photoId; // 图片id
    private String lookUpKey;
    private int selected = 0;
    private String formattedNumber;
    private String pinyin; // 姓名拼音

    public Contaction() {

    }

    protected Contaction(Parcel in) {
        contactId = in.readInt();
        desplayName = in.readString();
        phoneNum = in.readString();
        sortKey = in.readString();
        lookUpKey = in.readString();
        selected = in.readInt();
        formattedNumber = in.readString();
        pinyin = in.readString();
    }

    public static final Creator<Contaction> CREATOR = new Creator<Contaction>() {
        @Override
        public Contaction createFromParcel(Parcel in) {
            return new Contaction(in);
        }

        @Override
        public Contaction[] newArray(int size) {
            return new Contaction[size];
        }
    };

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

    public int getSelected() {
        return selected;
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

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(contactId);
        parcel.writeString(desplayName);
        parcel.writeString(phoneNum);
        parcel.writeString(sortKey);
        parcel.writeString(lookUpKey);
        parcel.writeInt(selected);
        parcel.writeString(formattedNumber);
        parcel.writeString(pinyin);
    }
}
