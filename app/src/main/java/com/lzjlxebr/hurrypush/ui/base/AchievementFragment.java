package com.lzjlxebr.hurrypush.ui.base;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.AchievementFragmentAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.entity.NetSyncTaskComplete;
import com.lzjlxebr.hurrypush.service.HurryPushSyncUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = AchievementFragment.class.getSimpleName();

    private AchievementFragmentAdapter achievementFragmentAdapter;
    private int mPosition = RecyclerView.NO_POSITION;

    @BindView(R.id.achievement_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.achi_swipe_refresh_vieww)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.achievement_load_indicator)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.achievement_load_error_msg)
    TextView mTvErrorMsg;

    @BindString(R.string.load_error_msg)
    String loadErrorMsg;

    public static final int INDEX_COLUMN_ACHI_TYPE = 4;

    public static final int INDEX_COLUMN_ACHI_NAME = 0;
    public static final int INDEX_COLUMN_ACHI_DESCRIPTION = 1;
    public static final int INDEX_COLUMN_ACHI_PROGRESS = 2;
    public static final int INDEX_COLUMN_ACHI_CONDITION = 3;
    public static final int INDEX_COLUMN_ACHI_REQUIRED_MINUTES = 5;
    public static final int INDEX_COLUMN_ACHI_REQUIRED_DAYS = 6;
    public static final int INDEX_COLUMN_UPDATE_TIME = 7;
    public static final int INDEX_COLUMN_ACHI_ID = 8;
    public static String[] ACHIEVEMNET_PROJECTION = {
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_NAME,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_DESCRIPTION,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_PROGRESS,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_CONDITION,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_TYPE,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_MINUTES,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_REQUIRED_DAYS,
            HurryPushContract.AchievementProgressEntry.COLUMN_UPDATE_TIME,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_ID
    };

    private static final int ACHIEVEMENT_LOADER_ID = 2000;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        //HurryPushSyncUtils.startImmediateSync((MainActivity)getActivity());
        Log.d(LOG_TAG, "MainActivity was created.");

        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_achievement,container,false);
        achievementFragmentAdapter = new AchievementFragmentAdapter(getActivity());

        ButterKnife.bind(this, view);

        showLoading();

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(achievementFragmentAdapter);

        getLoaderManager().initLoader(ACHIEVEMENT_LOADER_ID,null,this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startMySyncWork();
            }
        });
        return view;
    }

    private void startMySyncWork() {
        HurryPushSyncUtils.startImmediateSync(getActivity());
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case ACHIEVEMENT_LOADER_ID: {
                Uri achievementQueryUri = HurryPushContract.AchievementProgressEntry.ACHIEVEMENT_PROGRESS_URI;

                return new CursorLoader(
                        getActivity(),
                        achievementQueryUri,
                        ACHIEVEMNET_PROJECTION,
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
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        achievementFragmentAdapter.swapCursor(data);
        if (RecyclerView.NO_POSITION == mPosition) mPosition = 0;
        mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) showAchievementDataView();
        else showErrorMsg();
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        achievementFragmentAdapter.swapCursor(null);
    }

    void showAchievementDataView(){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveNetSyncEvent(NetSyncTaskComplete event) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showErrorMsg() {
        mTvErrorMsg.setText(loadErrorMsg);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.INVISIBLE);
    }
}
