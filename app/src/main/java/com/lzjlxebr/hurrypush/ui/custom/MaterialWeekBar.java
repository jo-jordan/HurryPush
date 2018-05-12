package com.lzjlxebr.hurrypush.ui.custom;

import android.content.Context;
import android.view.LayoutInflater;

import com.haibin.calendarview.WeekBar;
import com.lzjlxebr.hurrypush.R;

public class MaterialWeekBar extends WeekBar {
    public MaterialWeekBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.custom_week_bar,this,true);
        setBackgroundColor(0xffffffff);
    }
}
