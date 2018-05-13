package com.lzjlxebr.hurrypush.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.ui.base.AchievementFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementFragmentAdapter extends RecyclerView.Adapter<AchievementFragmentAdapter.AchievementFragmentViewHolder> {
    private final Context mConetxt;
    private Cursor mCursor;

    public AchievementFragmentAdapter(@NonNull Context context) {
        mConetxt = context;
    }

    @NonNull
    @Override
    public AchievementFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mConetxt).inflate(R.layout.achievement_list_items, parent, false);

        return new AchievementFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementFragmentViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String achi_name = mCursor.getString(AchievementFragment.INDEX_COLUMN_ACHI_NAME);
        String achi_description = mCursor.getString(AchievementFragment.INDEX_COLUMN_ACHI_DESCRIPTION);
        int achiId = mCursor.getInt(AchievementFragment.INDEX_COLUMN_ACHI_ID);
        int achi_progress = mCursor.getInt(AchievementFragment.INDEX_COLUMN_ACHI_PROGRESS);
        int achi_condition = mCursor.getInt(AchievementFragment.INDEX_COLUMN_ACHI_CONDITION);

        holder.imageViewDone.setTag(achiId);

        if (achi_condition == achi_progress) {
            holder.imageViewDone.setImageDrawable(mConetxt.getResources().getDrawable(R.drawable.ic_achievement_done));
        } else {
            holder.imageViewDone.setImageDrawable(mConetxt.getResources().getDrawable(R.drawable.ic_achievement));
        }
        String rate = achi_progress + "/" + achi_condition;

        holder.textViewAchiName.setText(achi_name);
        holder.textViewAchiDescription.setText(achi_description);
        holder.achiProgressBar.setMax(achi_condition);
        holder.achiProgressBar.setProgress(achi_progress);
        holder.textViewProgressRate.setText(rate);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

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

    class AchievementFragmentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.achievement_progress_bar)
        ProgressBar achiProgressBar;

        @BindView(R.id.tv_achi_name)
        TextView textViewAchiName;

        @BindView(R.id.tv_avhi_description)
        TextView textViewAchiDescription;

        @BindView(R.id.achi_progress_rate)
        TextView textViewProgressRate;

        @BindView(R.id.iv_achievement)
        ImageView imageViewDone;

        AchievementFragmentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
