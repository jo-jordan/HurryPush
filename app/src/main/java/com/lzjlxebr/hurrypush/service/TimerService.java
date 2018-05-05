package com.lzjlxebr.hurrypush.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lzjlxebr.hurrypush.entity.TimeUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    private static final String LOG_TAG = TimerService.class.getSimpleName();
    private String mHour;

    private String mMin;

    private String mSec;

    private final int mOneHourInMillis = 1;
    private long mStartTimeInMillis;
    private long mEndTimeInMillis;

    private boolean running = false;

    private Timer mTimer;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        beginTimer();
        return START_REDELIVER_INTENT;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void sendEvent() {
        EventBus.getDefault().post(new TimeUpdateEvent(
                mHour,
                mMin,
                mSec)
        );
    }

    private void beginTimer() {
        if (!running) {
            initTimer();
            running = true;
        }
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateTime();
                }
            }, 0, 1000);
        }
    }

    private void initTimer() {

        Calendar calendar = Calendar.getInstance();
        mStartTimeInMillis = calendar.getTimeInMillis();


        mHour = "00";
        mMin = "00";
        mSec = "00";


    }

    private void stopTimer() {
        Calendar calendar = Calendar.getInstance();
        mEndTimeInMillis = calendar.getTimeInMillis();

        if (mTimer != null) {
            mTimer.cancel();
        }

        running = false;
    }

    private void updateTime() {


        int hours = 0;
        int minutes = 0;
        int seconds = -1;

        if (!"00".equals(mSec))
            seconds = Integer.parseInt(mSec);
        if (!"00".equals(mMin))
            minutes = Integer.parseInt(mMin);
        if (!"00".equals(mHour))
            hours = Integer.parseInt(mHour);


        if (seconds < 9) {
            mSec = "0" + (seconds + 1);

            sendEvent();
            return;
        }
        if (seconds >= 9 && seconds < 59) {
            mSec = "" + (seconds + 1);

            sendEvent();
            return;
        }
        if (seconds == 59) {
            mSec = "00";
            seconds = 0;

            if (minutes < 9) {
                mMin = "0" + (minutes + 1);
            } else if (minutes >= 9 && minutes < 59) {
                mMin = "" + (minutes + 1);
            } else {
                mMin = "00";
                minutes = 0;
                mHour = "01";
            }
            sendEvent();
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
