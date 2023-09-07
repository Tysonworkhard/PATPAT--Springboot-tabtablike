package com.app.taptap.pojo;

import java.util.List;

public class TagsResponse extends TagProposed{
    //补充信息
    private List<TagProposed> tagProposed;
    private String userProfile;
    private String username;

    //默认构造函数
    public TagsResponse(){

    }

    public List<TagProposed> getTagProposed() {
        return tagProposed;
    }

    public void setTagProposed(List<TagProposed> tagProposed) {
        this.tagProposed = tagProposed;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUsername() {
        return username;
    }

    public String getUserProfile() {
        return userProfile;
    }
}
