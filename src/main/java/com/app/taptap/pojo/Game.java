package com.app.taptap.pojo;

import com.app.taptap.utils.DateChange;

import java.util.Date;

public class Game {
    private String gameName;
    private float score;
    private String detail;
    private String gameID;
    private String profile;
    private String gameDate;
    private String tag;
    private int reservationCount;
    private int searchCount;
    private String background;

    private String download;
    //处理时间，非数据库
    private Date timeSelector;

    //默认构造函数
    public Game(){

    }

    //构造函数
    public Game(String gameID, String profile, String gameName, Float score, String tag, String background){
        this.gameID = gameID;
        this.profile = profile;
        this.gameName = gameName;
        this.score = score;
        this.tag = tag;
        this.background = background;
    }
    //set get方法
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setReservationCount(int reservationCount) {
        this.reservationCount = reservationCount;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setTimeSelector(Date timeSelector) {
        this.timeSelector = timeSelector;
    }

    public Date getTimeSelector() {
        return timeSelector;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public String getGameName() {return gameName;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getDownload() {
        return download;
    }


}
