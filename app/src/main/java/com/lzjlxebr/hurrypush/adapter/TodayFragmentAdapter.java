package com.lzjlxebr.hurrypush.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.ui.base.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mConetxt;
    private Cursor mCursor;

    public TodayFragmentAdapter(@NonNull Context context) {
        mConetxt = context;
    }

    @NonNull
    @Override
    public TodayAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                View view = LayoutInflater.from(mConetxt).inflate(R.layout.today_list_items, parent, false);
                return new TodayAdapterViewHolder(view);
            }
            case 2: {
                View view = LayoutInflater.from(mConetxt).inflate(R.layout.today_list_items_survey, parent, false);
                return new TodayAdapterViewHolder(view);
            }
            default:
                View view = LayoutInflater.from(mConetxt).inflate(R.layout.today_list_items, parent, false);
                return new TodayAdapterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0: {
                TodayAdapterViewHolder todayHolder = (TodayAdapterViewHolder) holder;
                mCursor.moveToPosition(position);

                String appId = mCursor.getString(MainActivity.INDEX_COLUMN_APP_ID);
                int gender = mCursor.getInt(MainActivity.INDEX_COLUMN_GENDER);
                int currentLevelId = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_LEVEL_ID);
                int CurrentLevelExp = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_EXP);
                int upgradeExp = mCursor.getInt(MainActivity.INDEX_COLUMN_UPGRADE_EXP);
                int isFirstTime = mCursor.getInt(MainActivity.INDEX_COLUMN_IS_FIRST_START);

                todayHolder.todayTextViewContent.setText("APP_ID: " + appId + "\n");
                todayHolder.todayTextViewContent.append("GENDER: " + gender + "\n");
                todayHolder.todayTextViewContent.append("CURRENT LEVEL ID: " + currentLevelId + "\n");
                todayHolder.todayTextViewContent.append("CURRENT LEVEL EXP: " + CurrentLevelExp + "\n");
                todayHolder.todayTextViewContent.append("UPGRADE EXP: " + upgradeExp + "\n");
                todayHolder.todayTextViewContent.append("IS_FIRST_START EXP: " + isFirstTime);

                break;
            }
            case 2: {
                TodayAdapterViewHolderSurvey todaySurveyHolder = (TodayAdapterViewHolderSurvey) holder;

                mCursor.moveToPosition(position);

                String appId = mCursor.getString(MainActivity.INDEX_COLUMN_APP_ID);
                int gender = mCursor.getInt(MainActivity.INDEX_COLUMN_GENDER);
                int currentLevelId = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_LEVEL_ID);
                int CurrentLevelExp = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_EXP);
                int upgradeExp = mCursor.getInt(MainActivity.INDEX_COLUMN_UPGRADE_EXP);
                int isFirstTime = mCursor.getInt(MainActivity.INDEX_COLUMN_IS_FIRST_START);

                todaySurveyHolder.todayTextViewSurveyContent.setText("APP_ID: " + appId + "\n");
                todaySurveyHolder.todayTextViewSurveyContent.append("GENDER: " + gender + "\n");
                todaySurveyHolder.todayTextViewSurveyContent.append("CURRENT LEVEL ID: " + currentLevelId + "\n");
                todaySurveyHolder.todayTextViewSurveyContent.append("CURRENT LEVEL EXP: " + CurrentLevelExp + "\n");
                todaySurveyHolder.todayTextViewSurveyContent.append("UPGRADE EXP: " + upgradeExp + "\n");
                todaySurveyHolder.todayTextViewSurveyContent.append("IS_FIRST_START EXP: " + isFirstTime);

                break;
            }
            default: {
                return;
            }
        }
        mCursor.moveToPosition(position);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 2;
    }

    class TodayAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_today_title)
        TextView todayTextViewTitle;
        @BindView(R.id.tv_today_content)
        TextView todayTextViewContent;

        TodayAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class TodayAdapterViewHolderSurvey extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_today_survey_title)
        TextView todayTextViewSurveyTitle;
        @BindView(R.id.tv_today_survey_content)
        TextView todayTextViewSurveyContent;

        TodayAdapterViewHolderSurvey(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
