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
import com.lzjlxebr.hurrypush.base.TodayFragment;

public class StatisticsFragmentAdapter extends RecyclerView.Adapter<StatisticsFragmentAdapter.StatisticsAdapterViewHolder> {
    private final Context mConetxt;
    private Cursor mCursor;

    public StatisticsFragmentAdapter(@NonNull Context context){
        mConetxt = context;
    }
    @NonNull
    @Override
    public StatisticsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mConetxt).inflate(R.layout.today_list_items,parent,false);

        return new StatisticsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String appId = mCursor.getString(TodayFragment.INDEX_COLUMN_APP_ID);
        int gender = mCursor.getInt(TodayFragment.INDEX_COLUMN_GENDER);
        int currentLevelId = mCursor.getInt(TodayFragment.INDEX_COLUMN_CURRENT_LEVEL_ID);
        int CurrentLevelExp = mCursor.getInt(TodayFragment.INDEX_COLUMN_CURRENT_EXP);
        int upgradeExp = mCursor.getInt(TodayFragment.INDEX_COLUMN_UPGRADE_EXP);

        holder.statisticsTextView.setText("APP_ID: " + appId + "\n");
        holder.statisticsTextView.append("GENDER: " + gender + "\n");
        holder.statisticsTextView.append("CURRENT LEVEL ID: " + currentLevelId + "\n");
        holder.statisticsTextView.append("CURRENT LEVEL EXP: " + CurrentLevelExp + "\n");
        holder.statisticsTextView.append("UPGRADE EXP: " + upgradeExp);

    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class StatisticsAdapterViewHolder extends RecyclerView.ViewHolder{
        final TextView statisticsTextView;

        StatisticsAdapterViewHolder(View view){
            super(view);

            statisticsTextView = (TextView) view.findViewById(R.id.today_tv);
        }
    }
}
