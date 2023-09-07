package com.app.taptap.pojo;

public class SocketMessage {
    private int type = 1;   //聊天类型0：群聊，1：单聊.
    private String fromUser;//发送者.
    private String toUser;//接受者.
    private String msg;//消息
    private String roomID;//房间ID
    private String time;//时间

    //默认构造函数
    public SocketMessage(){

    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
