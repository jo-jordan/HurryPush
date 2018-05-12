package com.lzjlxebr.hurrypush.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.ui.base.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientInfoAdapter extends RecyclerView.Adapter<ClientInfoAdapter.ClientInfoViewHolder> {
    private final static String LOG_TAG = ClientInfoAdapter.class.getSimpleName();
    private final Context mContext;
    private Cursor mCursor;

    public ClientInfoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ClientInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.client_info_list_items, parent, false);
        TextView mCurrentLevel = view.findViewById(R.id.nav_header_current_level);
        ProgressBar mExpIndicator = view.findViewById(R.id.nav_header_current_exp);

        int currentLevelId = 1;
        int CurrentLevelExp = 0;
        int upgradeExp = 0;
        String currentLevelText = "等级: " + currentLevelId;

        mCurrentLevel.setText(currentLevelText);
        mExpIndicator.setMax(upgradeExp);
        mExpIndicator.setProgress(CurrentLevelExp);
        return new ClientInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientInfoViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int currentLevelId = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_LEVEL_ID);
        int CurrentLevelExp = mCursor.getInt(MainActivity.INDEX_COLUMN_CURRENT_EXP);
        int upgradeExp = mCursor.getInt(MainActivity.INDEX_COLUMN_UPGRADE_EXP);

        String currentLevelText = "等级: " + currentLevelId;

        if (mCursor == null) {
            currentLevelId = 1;
            CurrentLevelExp = 0;
            upgradeExp = 0;
            currentLevelText = "等级: " + currentLevelId;
        }

        holder.mCurrentLevel.setText(currentLevelText);

        Log.d(LOG_TAG,"holder.indicator is null? " + (holder.mExpIndicator == null));
        holder.mExpIndicator.setMax(upgradeExp);
        holder.mExpIndicator.setProgress(CurrentLevelExp);
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


    class ClientInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nav_header_current_level)
        TextView mCurrentLevel;
        @BindView(R.id.nav_header_current_exp)
        ProgressBar mExpIndicator;

        ClientInfoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
