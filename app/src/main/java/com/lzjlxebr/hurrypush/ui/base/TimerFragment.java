package com.lzjlxebr.hurrypush.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.entity.TimerInnerNotificationEntry;
import com.lzjlxebr.hurrypush.ui.custom.HurryPushTimerView;

import org.greenrobot.eventbus.EventBus;

public class TimerFragment extends Fragment implements TextWatcher {

    private static final String LOG_TAG = "TimerFragment";

    private TextView mTextViewSecond;
    private TextView mTextViewMinute;
    private TextView mTextViewHour;

    private HurryPushTimerView mHurryPushTimerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        initVariables(view);
        mTextViewSecond.addTextChangedListener(this);
        mTextViewMinute.addTextChangedListener(this);
        mTextViewHour.addTextChangedListener(this);
        return view;
    }

    private void initVariables(View view) {
        mTextViewSecond = view.findViewById(R.id.tv_second);
        mTextViewMinute = view.findViewById(R.id.tv_minute);
        mTextViewHour = view.findViewById(R.id.tv_hour);

        mHurryPushTimerView = view.findViewById(R.id.hurry_push_timer_view);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    private void sendOnTimerChanged(String title, String content, int time) {
        EventBus.getDefault().post(new TimerInnerNotificationEntry(title, content, time));
    }


    @Override
    public void afterTextChanged(Editable s) {
        String hour = mTextViewHour.getText().toString();
        String min = mTextViewMinute.getText().toString();
        String sec = mTextViewSecond.getText().toString();

        String timer = hour+min+sec;

        if (timer.equals("000300")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_three_mins);
            sendOnTimerChanged(title, content, 3);
            Log.d(LOG_TAG,"title: " + title + ", content: " + content + " ,send by TimerFragment.");
            return;
        }
        if (timer.equals("000500")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_five_mins);
            sendOnTimerChanged(title, content, 5);
            Log.d(LOG_TAG,"title: " + title + ", content: " + content + " ,send by TimerFragment.");
            return;
        }
        if (timer.equals("000700")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_seven_mins);
            sendOnTimerChanged(title, content, 7);
            Log.d(LOG_TAG,"title: " + title + ", content: " + content + " ,send by TimerFragment.");
            return;
        }
        if (timer.equals("001000")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_ten_mins);
            sendOnTimerChanged(title, content, 10);
            Log.d(LOG_TAG,"title: " + title + ", content: " + content + " ,send by TimerFragment.");
            return;
        }
        if (timer.equals("003000")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_thirty_mins);
            sendOnTimerChanged(title, content, 30);
            Log.d(LOG_TAG,"title: " + title + ", content: " + content + " ,send by TimerFragment.");
            return;
        }
        if (timer.equals("005959")){
            String title = getResources().getString(R.string.timer_inner_notification_title);
            String content = getResources().getString(R.string.timer_inner_notification_body_one_hour);
            sendOnTimerChanged(title, content, 60);
            return;
        }
    }

    @Override
    public void onDestroy() {
        mHurryPushTimerView.unRegEventBus();
        super.onDestroy();
    }
}
