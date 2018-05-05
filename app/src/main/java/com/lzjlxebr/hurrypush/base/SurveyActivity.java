package com.lzjlxebr.hurrypush.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.entity.DefecationEvent;
import com.lzjlxebr.hurrypush.entity.EmptyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SurveyActivity extends AppCompatActivity {
    private static final String LOG_TAG = SurveyActivity.class.getSimpleName();


    private long startTime;
    private long endTime;
    private long id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        initToolBar();
        EventBus.getDefault().register(this);

        boolean reg = EventBus.getDefault().isRegistered(this);
        Log.d(LOG_TAG,"is reged? " + reg);


    }

    // set toolbar as actionbar
    public void initToolBar() {
        Toolbar toolbar = findViewById(R.id.survey_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventMainThread(EmptyEvent event) {
        Log.d(LOG_TAG,event.getClass().getName());

        if (event == null) {
            Log.d(LOG_TAG,"event is null.");
            return;
        }
        if (event instanceof DefecationEvent) {
            startTime = ((DefecationEvent) event).getStartTime();
            endTime = ((DefecationEvent) event).getEndTime();
            id = ((DefecationEvent) event).getId();

            EventBus.getDefault().removeStickyEvent(event);


            Log.d(LOG_TAG,"start_time: " + startTime);
            Log.d(LOG_TAG,"end_time: " + endTime);
            Log.d(LOG_TAG,"id: " + id);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
