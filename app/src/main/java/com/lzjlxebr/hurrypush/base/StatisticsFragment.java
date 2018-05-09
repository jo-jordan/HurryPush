package com.lzjlxebr.hurrypush.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.lzjlxebr.hurrypush.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment implements CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener,
        View.OnClickListener{
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    CalendarLayout mCalendarLayout;

    @Override
    public void onClick(View v) {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {

        mTextMonthDay = (TextView) view.findViewById(R.id.tv_month_day);
        mTextYear = (TextView) view.findViewById(R.id.tv_year);
        mTextLunar = (TextView) view.findViewById(R.id.tv_lunar);
        mRelativeTool = (RelativeLayout) view.findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mTextCurrentDay = (TextView) view.findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        mCalendarLayout = (CalendarLayout) view.findViewById(R.id.calendarLayout);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }

    private void initData() {
        List<Calendar> schemes = new ArrayList<>();
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFF40db25,"假"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138,"事"));
        schemes.add(getSchemeCalendar(year, month, 9, 0xFFdf1356,"议"));
        schemes.add(getSchemeCalendar(year, month, 13, 0xFFedc56d,"记"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFedc56d,"记"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFaacc44,"假"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0,"记"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFF13acf0,"假"));
        schemes.add(getSchemeCalendar(year, month, 27, 0xFF13acf0,"多"));
        mCalendarView.setSchemeDate(schemes);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color,String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800,"假");
        calendar.addScheme(0xFF008800,"节");
        return calendar;
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {

        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }


}
