package com.lzjlxebr.hurrypush.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.entity.EmptyEvent;
import com.lzjlxebr.hurrypush.entity.TimeUpdateEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HurryPushTimerView extends LinearLayout{
    private TextView mTvHour;
    private TextView mTvMin;
    private TextView mTvSec;
    private TextView mTvColon1;
    private TextView mTvColon2;

    @DimenRes
    private float mClockWidth = 0.0f;
    @DimenRes
    private float mHourWidth = 0.0f;
    @DimenRes
    private float mMinWidth = 0.0f;
    @DimenRes
    private float mSecWidth = 0.0f;
    @DimenRes
    private float mDividerVertical1 = 0.0f;
    @DimenRes
    private float mDividerVertical2 = 0.0f;

    private int mHour = -1;

    private int mMin = -1;

    private int mSec = -1;

    private Context mContext;

    public HurryPushTimerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public HurryPushTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public HurryPushTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        EventBus.getDefault().register(this);
        View.inflate(context, R.layout.hurrypush_timer_view, this);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HurryPushTimerView, defStyleAttr, 0);
            try {
                mClockWidth = typedArray.getDimension(R.styleable.HurryPushTimerView_timerWidth, 0.0f);
                mHourWidth = typedArray.getDimension(R.styleable.HurryPushTimerView_hourWidth, 0.0f);
                mMinWidth = typedArray.getDimension(R.styleable.HurryPushTimerView_minuteWidth, 0.0f);
                mSecWidth = typedArray.getDimension(R.styleable.HurryPushTimerView_secondWidth, 0.0f);
                mDividerVertical1 = typedArray.getDimension(R.styleable.HurryPushTimerView_divider1Width, 0.0f);
                mDividerVertical2 = typedArray.getDimension(R.styleable.HurryPushTimerView_divider2Width, 0.0f);
            } finally {
                typedArray.recycle();
            }
        }
        initView();
    }

    private void initView() {
        mTvHour = (TextView) findViewById(R.id.tv_hour);
        mTvMin = (TextView) findViewById(R.id.tv_minute);
        mTvSec = (TextView) findViewById(R.id.tv_second);
        mTvColon1 = (TextView) findViewById(R.id.divider_hour_minute);
        mTvColon2 = (TextView) findViewById(R.id.divider_minute_second);

        changeSize(mHourWidth, mClockWidth, mTvHour);
        changeSize(mMinWidth, mClockWidth, mTvMin);
        changeSize(mSecWidth, mClockWidth, mTvSec);
        changeSize(mDividerVertical1, mClockWidth, mTvColon1);
        changeSize(mDividerVertical2, mClockWidth, mTvColon2);

        View mViewDividerVertical1 = findViewById(R.id.divider_hour_minute);


        if (mDividerVertical1 != 0.0f) {
            ViewGroup.LayoutParams params = mViewDividerVertical1.getLayoutParams();
            params.height = (int) mDividerVertical1;
            mViewDividerVertical1.setLayoutParams(params);
        }
        if (mDividerVertical2 != 0.0f) {
            ViewGroup.LayoutParams params = mViewDividerVertical1.getLayoutParams();
            params.height = (int) mDividerVertical2;
            mViewDividerVertical1.setLayoutParams(params);
        }
    }

    /**
     * ChangeSize of target view
     */
    private void changeSize(float targetSize, float parentSize, View targetView) {
        ViewGroup.LayoutParams params = targetView.getLayoutParams();

        if (targetSize != 0.0f) {
            params.width = (int) targetSize;
            params.height = (int) targetSize;
        } else {
            if (parentSize != 0.0f) {
                params.width = (int) parentSize;
                params.height = (int) parentSize;
            }
        }
        targetView.setLayoutParams(params);
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EmptyEvent event) {
        if (event == null) {
            return;
        }
        if (event instanceof TimeUpdateEvent) {
            updateClock((TimeUpdateEvent) event);
        }
    }

    private void updateClock(TimeUpdateEvent event) {
        //if new value is equal to the old value , it don't need to change .
        mTvSec.setText(event.getmSec());

        mTvMin.setText(event.getmMin());

        mTvHour.setText(event.getmHour());
    }
}
