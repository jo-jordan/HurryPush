package com.lzjlxebr.hurrypush.entity;

public class TimerInnerNotificationEntry extends EmptyEvent{
    private String mTitle;
    private String mContent;
    private int mTime;

    public int getmTime() {
        return mTime;
    }

    public void setmTime(int mTime) {
        this.mTime = mTime;
    }

    public TimerInnerNotificationEntry(String mTitle, String mContent, int mTime) {

        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTime = mTime;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
