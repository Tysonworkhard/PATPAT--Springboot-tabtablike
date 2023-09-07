package com.app.taptap.pojo;

public class RequestMessage {

    private String senderUID;//消息发送者
    private String room;//房间号
    private String type;//消息类型类型为1为单聊
    private String content;//消息内容
    private String time;

    public RequestMessage() {
    }

    public RequestMessage(String sender, String room, String type, String content, String time) {
        this.senderUID = sender;
        this.room = room;
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public String getRoom() {
        return room;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSenderUID(String sender) {
        this.senderUID = sender;
    }

    public void setReceiver(String room) {
        this.room = room;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}