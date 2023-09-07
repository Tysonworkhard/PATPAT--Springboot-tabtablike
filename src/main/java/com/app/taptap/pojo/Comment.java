package com.app.taptap.pojo;

import java.util.List;

public class Comment {
    private float score;
    private String text;
    private String commentID;
    private List<String> pictureAddress;
    private String commentDate;
    private String UID;
    private String tagID;
    private String gameID;

    //set get方法

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public List<String> getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(List<String> pictureAddress) {
        this.pictureAddress = pictureAddress;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
