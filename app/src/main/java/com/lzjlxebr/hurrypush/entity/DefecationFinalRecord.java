package com.lzjlxebr.hurrypush.entity;

public class DefecationFinalRecord extends EmptyEvent {
    private long id;
    private int smell;
    private int constipation;
    private int stickiness;
    private double overallRatting;
    private double gainExp;
    private int isUserFinished;

    public DefecationFinalRecord(long id, int smell, int constipation, int stickiness, double overallRatting, double gainExp, int isUserFinished) {
        this.id = id;
        this.smell = smell;
        this.constipation = constipation;
        this.stickiness = stickiness;
        this.overallRatting = overallRatting;
        this.gainExp = gainExp;
        this.isUserFinished = isUserFinished;
    }

    public long getId() {
        return id;
    }

    public int getSmell() {
        return smell;
    }

    public int getConstipation() {
        return constipation;
    }

    public int getStickiness() {
        return stickiness;
    }

    public double getOverallRatting() {
        return overallRatting;
    }

    public double getGainExp() {
        return gainExp;
    }

    public int getIsUserFinished() {
        return isUserFinished;
    }
}
