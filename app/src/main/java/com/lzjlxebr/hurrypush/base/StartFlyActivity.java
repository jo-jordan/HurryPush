package com.lzjlxebr.hurrypush.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.service.TimerService;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartFlyActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingActionButton;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_fly);



        initView();

        startTimerService();

    }

    private void initView(){
        mContext = this;
        mFloatingActionButton = findViewById(R.id.stop_timer_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopTimer = new Intent(mContext, TimerService.class);
                stopService(stopTimer);
                startSurvey();
            }
        });
    }

    private void startTimerService(){
        Intent startTimer = new Intent(this, TimerService.class);
        startService(startTimer);
    }

    private void startSurvey(){
        Intent start = new Intent(this,SurveyActivity.class);
        startActivity(start);
    }


}
