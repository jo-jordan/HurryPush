package com.lzjlxebr.hurrypush.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.entity.DefecationEvent;
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
    private long insertedId;

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

    private void sendEvent() {
        Log.d(LOG_TAG,"hour: " + mHour + ", min: " + mMin + " ,sec: " + mSec);
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
        mSec = "-1";


    }

    private void stopTimer() {

        if (mTimer != null) {
            mTimer.cancel();
            Calendar calendar = Calendar.getInstance();
            mEndTimeInMillis = calendar.getTimeInMillis();
        }

        running = false;
    }

    private void updateTime() {


        int hours = 0;
        int minutes = 0;
        int seconds = -1;

        //if ("00".equals(mSec))
            seconds = Integer.parseInt(mSec);
        //if ("00".equals(mMin))
            minutes = Integer.parseInt(mMin);
        //if ("00".equals(mHour))
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
                if("01".equals(mHour)){
                    stopTimer();
                    stopSelf();
                }
            }
            sendEvent();
            return;
        }
    }

    public void saveUncompletedDataToDatabase(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME,Calendar.getInstance().getTimeInMillis());
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME,mStartTimeInMillis);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME,mEndTimeInMillis);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_SMELL,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP,0.0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING,0.0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH,0);
        contentValues.put(HurryPushContract.DefecationRecordEntry.COLUMN_UPLOAD_TO_SERVER,0);

        Uri insertUri = HurryPushContract.DefecationRecordEntry.DEFECATION_RECORD_URI;
        Uri insertedUri = getContentResolver().insert(insertUri,contentValues);
        insertedId = Long.parseLong(insertedUri.getQueryParameter("insertedId"));

        sendDefecationEvent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        saveUncompletedDataToDatabase();
    }

    private void sendDefecationEvent(){
        EventBus.getDefault().postSticky(new DefecationEvent(mStartTimeInMillis,mEndTimeInMillis,insertedId));
    }
}
