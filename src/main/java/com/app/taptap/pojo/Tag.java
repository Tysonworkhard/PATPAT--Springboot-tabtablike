package com.app.taptap.pojo;

import java.util.List;

public class Tag extends Game{
    private String tagID;
    private String userID;
    private String title;
    private String text;
    private List<String> pictureAddress;
    private int scanCount;
    private int good;

    private String time;

    //默认构造函数
    public Tag(){

    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getGood() {
        return good;
    }

    public void setScanCount(int scanCount) {
        this.scanCount = scanCount;
    }

    public int getScanCount() {
        return scanCount;
    }

    public void setPictureAddress(List<String> pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public List<String> getPictureAddress() {
        return pictureAddress;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTagID() {
        return tagID;
    }

    public String getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
