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
import com.lzjlxebr.hurrypush.ui.base.TodayFragment;

public class TodayFragmentAdapter extends RecyclerView.Adapter<TodayFragmentAdapter.TodayAdapterViewHolder> {
    private final Context mConetxt;
    private Cursor mCursor;

    public TodayFragmentAdapter(@NonNull Context context) {
        mConetxt = context;
    }

    @NonNull
    @Override
    public TodayAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mConetxt).inflate(R.layout.today_list_items, parent, false);

        return new TodayAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String appId = mCursor.getString(TodayFragment.INDEX_COLUMN_APP_ID);
        int gender = mCursor.getInt(TodayFragment.INDEX_COLUMN_GENDER);
        int currentLevelId = mCursor.getInt(TodayFragment.INDEX_COLUMN_CURRENT_LEVEL_ID);
        int CurrentLevelExp = mCursor.getInt(TodayFragment.INDEX_COLUMN_CURRENT_EXP);
        int upgradeExp = mCursor.getInt(TodayFragment.INDEX_COLUMN_UPGRADE_EXP);
        int isFirstTime = mCursor.getInt(TodayFragment.INDEX_COLUMN_IS_FIRST_START);

        holder.todayTextView.setText("APP_ID: " + appId + "\n");
        holder.todayTextView.append("GENDER: " + gender + "\n");
        holder.todayTextView.append("CURRENT LEVEL ID: " + currentLevelId + "\n");
        holder.todayTextView.append("CURRENT LEVEL EXP: " + CurrentLevelExp + "\n");
        holder.todayTextView.append("UPGRADE EXP: " + upgradeExp + "\n");
        holder.todayTextView.append("IS_FIRST_START EXP: " + isFirstTime);
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

    class TodayAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView todayTextView;

        TodayAdapterViewHolder(View view) {
            super(view);

            todayTextView = view.findViewById(R.id.today_tv);
        }
    }
}
