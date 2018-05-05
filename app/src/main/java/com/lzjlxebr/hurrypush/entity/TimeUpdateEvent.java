package com.lzjlxebr.hurrypush.entity;

public class TimeUpdateEvent extends EmptyEvent {
    private final String mHour;

    private final String mMin;

    private final String mSec;

    public TimeUpdateEvent(String mHour, String mMin, String mSec) {
        this.mHour = mHour;
        this.mMin = mMin;
        this.mSec = mSec;
    }

    public String getmHour() {
        return mHour;
    }

    public String getmMin() {
        return mMin;
    }

    public String getmSec() {
        return mSec;
    }
}
