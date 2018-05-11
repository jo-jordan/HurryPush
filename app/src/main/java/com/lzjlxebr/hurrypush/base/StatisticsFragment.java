package com.lzjlxebr.hurrypush.base;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.util.DateFormatterUtils;

import java.util.ArrayList;
import java.util.List;


public class StatisticsFragment extends Fragment implements CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnMonthChangeListener,
        View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final int INDEX_COLUMN_START_TIME = 0;

    CalendarLayout mCalendarLayout;
    public static final int INDEX_COLUMN_END_TIME = 1;
    public static final int INDEX_COLUMN_IS_USER_FINISH = 2;
    public static final int INDEX_COLUMN_GAIN_EXP = 3;
    public static final int INDEX_COLUMN_SMELL = 4;
    public static final int INDEX_COLUMN_CONSTIPATION = 5;
    public static final int INDEX_COLUMN_STICKINESS = 6;
    public static final int INDEX_COLUMN_OVERALL_RATING = 7;
    public static final int INDEX_COLUMN_SERVER_CALL_BACK = 8;
    private static final int LOADER_ID = 2002;
    public static String[] DEFECATION_RECORD_PROJECTION = {
            HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME,
            HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME,
            HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH,
            HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP,
            HurryPushContract.DefecationRecordEntry.COLUMN_SMELL,
            HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION,
            HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS,
            HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING,
            HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK
    };
    CalendarView mCalendarView;
    TextView mCurrentDate, mTvDefecationDesc;
    Calendar mCalendar;
    List<Calendar> schemes;
    private Cursor mCursor;


    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);


        initView(view);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        return view;
    }

    private void initView(View view) {

        mCalendarView = view.findViewById(R.id.calendarView);
        mCurrentDate = view.findViewById(R.id.tv_current_date_indicator);
        mTvDefecationDesc = view.findViewById(R.id.tv_defecation_desc);

        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        int day = mCalendarView.getCurDay();
        String indicator_text = year + "年" + month + "月" + day + "日";
        mCurrentDate.setText(indicator_text);


        mCalendarLayout = view.findViewById(R.id.calendarLayout);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnMonthChangeListener(this);
    }

    private void initData() {
        mCalendar = mCalendarView.getSelectedCalendar();
        schemes = new ArrayList<>();

        int count = mCursor.getCount();
        int itemCount = mCursor.getColumnCount();

        for (int i = 0; i < count; i++) {
            mCursor.moveToPosition(i);

            long startTime = mCursor.getLong(INDEX_COLUMN_START_TIME);
            long endTime = mCursor.getLong(INDEX_COLUMN_END_TIME);
            float gain_exp = mCursor.getFloat(INDEX_COLUMN_GAIN_EXP);
            int smell = mCursor.getInt(INDEX_COLUMN_SMELL);
            int constipation = mCursor.getInt(INDEX_COLUMN_CONSTIPATION);
            int stickiness = mCursor.getInt(INDEX_COLUMN_STICKINESS);
            float overall_rating = mCursor.getLong(INDEX_COLUMN_OVERALL_RATING);
            int server_call_back = mCursor.getInt(INDEX_COLUMN_SERVER_CALL_BACK);

            int year = DateFormatterUtils.getYearFromLong(startTime);
            int month = DateFormatterUtils.getMonthFromLong(startTime);
            int day = DateFormatterUtils.getDayFromLong(startTime);

            //String smellStr = smell == 0 ? "" : "";

            String text = "获得经验：" +
                    gain_exp + "，闻起来：" + smell + "，通畅度：" + constipation + "，黏稠度：" +
                    stickiness + "，综合评分：" + overall_rating + "。上传服务器状态：" + server_call_back;


            schemes.add(getSchemeCalendar(year, month, day, 0xff3e3e3e, text));
        }


        mCalendarView.setSchemeDate(schemes);
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mCalendar = calendar;
        int year = calendar.getYear();
        int month = calendar.getMonth();
        int day = calendar.getDay();
        String indicator_text = year + "年" + month + "月" + day + "日";
        mCurrentDate.setText(indicator_text);

        int count = 0;

        String text = "";
        if (schemes != null) {
            for (int i = 0; i < schemes.size(); i++) {
                Calendar schemeCalendar = schemes.get(i);
                int yearInScheme = schemeCalendar.getYear();
                int monthInScheme = schemeCalendar.getMonth();
                int dayInScheme = schemeCalendar.getDay();


                if (year == yearInScheme) {
                    if (month == monthInScheme) {
                        if (day == dayInScheme) {
                            if (count == 0) {
                                text = schemeCalendar.getScheme();
                                mTvDefecationDesc.setText(text + "\n");
                                ++count;
                            } else {
                                text = schemeCalendar.getScheme();
                                mTvDefecationDesc.append(text + "\n");
                                ++count;
                            }
                        } else {
                            if (text == null || "".equals(text)) {
                                text = "当前所选日期暂无详情查看。\n";
                                mTvDefecationDesc.setText(text);
                            }
                        }
                    } else {
                        if (text == null || "".equals(text)) {
                            text = "当前所选日期暂无详情查看。\n";
                            mTvDefecationDesc.setText(text);
                        }
                    }
                } else {
                    if (text == null || "".equals(text)) {
                        text = "当前所选日期暂无详情查看。\n";
                        mTvDefecationDesc.setText(text);
                    }

                }
            }
        } else {
            text = "当前所选日期暂无详情查看。" + "\n";
            mTvDefecationDesc.setText(text);
        }
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //long startOfDay = DateFormatterUtils.getLongDateValueFromIntValues(mCalendar.getYear(), mCalendar.getMonth(), mCalendar.getDay());
        //long endOfDay = DateFormatterUtils.getEndOfMonthLongValueFromStart(startOfDay);

        //String selection = "start_time between ? and ?";

        //String sortOrder = "start_time";

        switch (id) {
            case LOADER_ID: {
                Uri defecationRecordUri = HurryPushContract.DefecationRecordEntry.DEFECATION_RECORD_URI;
                return new CursorLoader(
                        getActivity(),
                        defecationRecordUri,
                        DEFECATION_RECORD_PROJECTION,
                        null,
                        null,
                        null
                );
            }
            default:
                throw new RuntimeException("Loader Not Implemented" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        swapCursor(data);
        initData();
        //mCursor.close();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        swapCursor(null);
    }

    private void swapCursor(Cursor cursor) {
        mCursor = cursor;
    }


    @Override
    public void onMonthChange(int year, int month) {
        getLoaderManager().getLoader(LOADER_ID).forceLoad();
    }
}
