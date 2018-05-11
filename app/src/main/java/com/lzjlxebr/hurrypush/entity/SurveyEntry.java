package com.lzjlxebr.hurrypush.entity;

public class SurveyEntry {
    private int smell;
    private int constipation;
    private int stickiness;

    public SurveyEntry(int smell, int constipation, int stickiness) {
        this.smell = smell;
        this.constipation = constipation;
        this.stickiness = stickiness;
    }

    public int getSmell() {
        return smell;
    }

    public void setSmell(int smell) {
        this.smell = smell;
    }

    public int getConstipation() {
        return constipation;
    }

    public void setConstipation(int constipation) {
        this.constipation = constipation;
    }

    public int getStickiness() {
        return stickiness;
    }

    public void setStickiness(int stickiness) {
        this.stickiness = stickiness;
    }
}
