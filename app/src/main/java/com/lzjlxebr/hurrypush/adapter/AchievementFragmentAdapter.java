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
import com.lzjlxebr.hurrypush.base.AchievementFragment;

public class AchievementFragmentAdapter extends RecyclerView.Adapter<AchievementFragmentAdapter.AchievementFragmentViewHolder> {
    private final Context mConetxt;
    private Cursor mCursor;

    public AchievementFragmentAdapter(@NonNull Context context){
        mConetxt = context;
    }
    @NonNull
    @Override
    public AchievementFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mConetxt).inflate(R.layout.achievement_list_items,parent,false);

        return new AchievementFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementFragmentViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int level_id = mCursor.getInt(AchievementFragment.INDEX_COLUMN_LEVEL_ID);
        int levelNumber = mCursor.getInt(AchievementFragment.INDEX_COLUMN_LEVEL_NUMBER);
        int levelExpSingle = mCursor.getInt(AchievementFragment.INDEX_COLUMN_LEVEL_EXP_SINGLE);
        int levelExpTotal = mCursor.getInt(AchievementFragment.INDEX_COLUMN_LEVEL_EXP_TOTAL);

        holder.achievementTextView.setText("LEVEL_ID: " + level_id + "\n");
        holder.achievementTextView.append("LEVEL NUMBER: " + levelNumber + "\n");
        holder.achievementTextView.append("LEVEL EXP SINGLE: " + levelExpSingle + "\n");
        holder.achievementTextView.append("LEVEL EXP TOTAL: " + levelExpTotal);
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

    class AchievementFragmentViewHolder extends RecyclerView.ViewHolder{
        final TextView achievementTextView;
        AchievementFragmentViewHolder(View view){
            super(view);

            achievementTextView = (TextView) view.findViewById(R.id.achievement_tv);

        }

    }
}
