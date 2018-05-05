package com.lzjlxebr.hurrypush.entity;

public class Client {
    private int currentLevelId;
    private int currentExp;
    private int upgradeExp;
    private String nickName;

    public int getCurrentLevelId() {
        return currentLevelId;
    }

    public void setCurrentLevelId(int currentLevelId) {
        this.currentLevelId = currentLevelId;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(int currentExp) {
        this.currentExp = currentExp;
    }

    public int getUpgradeExp() {
        return upgradeExp;
    }

    public void setUpgradeExp(int upgradeExp) {
        this.upgradeExp = upgradeExp;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return currentLevelId + currentExp + upgradeExp + nickName;
    }
}
