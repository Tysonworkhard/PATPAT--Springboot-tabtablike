package com.app.taptap.pojo;

import java.util.Date;

public class Message extends ResponseMessage{
    private String profile;
    private String roomID;
    private String time;

    private Date timeSelector;

    public void setTimeSelector(Date timeSelector) {
        this.timeSelector = timeSelector;
    }

    public Date getTimeSelector() {
        return timeSelector;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
}
