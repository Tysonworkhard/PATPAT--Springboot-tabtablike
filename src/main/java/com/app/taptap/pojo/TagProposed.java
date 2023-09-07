package com.app.taptap.pojo;

public class TagProposed extends Tag{
    private int UserCount;

    private double proposedMetrics;

    public void setProposedMetrics(double proposedMetrics) {
        this.proposedMetrics = proposedMetrics;
    }

    public double getProposedMetrics() {
        return proposedMetrics;
    }

    public void setUserCount(int userCount) {
        UserCount = userCount;
    }

    public int getUserCount() {
        return UserCount;
    }
}
