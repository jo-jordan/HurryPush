package com.lzjlxebr.hurrypush.entity;

public class DefecationEvent extends EmptyEvent {
    private final long startTime;
    private final long endTime;
    private final long id;

    public DefecationEvent(long startTime, long endTime, long id) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getId() {
        return id;
    }
}
