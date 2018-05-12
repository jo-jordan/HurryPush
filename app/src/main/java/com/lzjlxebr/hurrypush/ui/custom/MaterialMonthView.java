package com.lzjlxebr.hurrypush.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

public class MaterialMonthView extends MonthView {

    /**
     *
     */
    private int mRadius;
    /**
     * scheme text paint
     */
    private Paint mTextPaint = new Paint();
    /**
     * background color paint
     */
    private Paint mSchemeBackgroundPaint = new Paint();
    private float mRadio;
    private int mPadding;
    private float mSchmeBaseLine;

    /**
     *
     * @param context
     */
    public MaterialMonthView(Context context) {
        super(context);
    }

    private static int dpToPixels(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return false;
    }

    @Override @TargetApi(21)
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        float left = (float) x * mItemWidth;
        float top = (float) (y + 1) * mItemHeight;
        float right = left + mItemWidth;
        float bottom = top - mItemHeight;

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);

        //canvas.drawArc(left,top,right,bottom,0.0f,360.0f,true,mSchemeBackgroundPaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        }else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
