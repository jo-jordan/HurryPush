package com.lzjlxebr.hurrypush.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.ui.base.AchievementFragment;
import com.lzjlxebr.hurrypush.util.NumbersUtils;

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

        String achi_name = mCursor.getString(AchievementFragment.INDEX_COLUMN_ACHI_NAME);
        String achi_description = mCursor.getString(AchievementFragment.INDEX_COLUMN_ACHI_DESCRIPTION);
        int achi_progress = mCursor.getInt(AchievementFragment.INDEX_COLUMN_ACHI_PROGRESS);
        int achi_condition = mCursor.getInt(AchievementFragment.INDEX_COLUMN_ACHI_CONDITION);

        int molecular = NumbersUtils.convertBinaryStringToInt(Integer.toBinaryString(achi_progress));
        int denominator = NumbersUtils.convertBinaryStringToInt(Integer.toBinaryString(achi_condition));

        String rate = molecular + "/" + denominator;

        holder.textViewAchiName.setText(achi_name);
        holder.textViewAchiDescription.setText(achi_description);
        holder.achiProgressBar.setMax(denominator);
        holder.achiProgressBar.setProgress(molecular);
        holder.textViewProgressRate.setText(rate);
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
        final ProgressBar achiProgressBar;
        final TextView textViewAchiName,textViewAchiDescription,textViewProgressRate;

        AchievementFragmentViewHolder(View view){
            super(view);
            textViewAchiName = view.findViewById(R.id.tv_achi_name);
            textViewAchiDescription = view.findViewById(R.id.tv_avhi_description);
            achiProgressBar = view.findViewById(R.id.achievement_progress_bar);
            textViewProgressRate = view.findViewById(R.id.achi_progress_rate);


        }

    }
}
