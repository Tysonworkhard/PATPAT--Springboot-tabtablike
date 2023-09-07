package com.app.taptap.pojo;

import java.util.Date;

public class CommentResponse extends Comment{
    private String username;
    private String userprofile;

    private Date timeSelector;

    public Date getTimeSelector() {
        return timeSelector;
    }

    public void setTimeSelector(Date timeSelector) {
        this.timeSelector = timeSelector;
    }

    public String getProfile() {
        return userprofile;
    }

    public void setProfile(String profile) {
        this.userprofile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
