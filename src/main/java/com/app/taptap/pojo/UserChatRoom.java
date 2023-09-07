package com.app.taptap.pojo;

public class UserChatRoom extends User{
    private String roomID;
    private String senderID;
    private String time;
    private String content;

    public void setTime(String time) {
        this.time = time;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
