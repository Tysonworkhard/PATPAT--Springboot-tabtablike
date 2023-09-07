package com.app.taptap.pojo;

import org.glassfish.hk2.api.Rank;

public class RankAllGame extends Game{
    private String rankSystem;
    private String rankType;
    private String rankTag;
    private Float rankScore;
    private int userCount;

    //默认构造函数
    public RankAllGame(){

    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setRankSystem(String rankSystem) {
        this.rankSystem = rankSystem;
    }

    public void setRankScore(Float rankScore) {
        this.rankScore = rankScore;
    }

    public void setRankTag(String rankTag) {
        this.rankTag = rankTag;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public Float getRankScore() {
        return rankScore;
    }

    public String getRankSystem() {
        return rankSystem;
    }

    public String getRankTag() {
        return rankTag;
    }

    public String getRankType() {
        return rankType;
    }
}
