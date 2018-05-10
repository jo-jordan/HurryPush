package com.lzjlxebr.hurrypush.base;


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

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.AchievementFragmentAdapter;
import com.lzjlxebr.hurrypush.db.HurryPushContract;
import com.lzjlxebr.hurrypush.service.HurryPushSyncUtils;

public class AchievementFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = AchievementFragment.class.getSimpleName();

    private AchievementFragmentAdapter achievementFragmentAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressBar mLoadingIndicator;

    public static String[] ACHIEVEMNET_PROJECTION = {
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_NAME,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_DESCRIPTION,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_PROGRESS,
            HurryPushContract.AchievementProgressEntry.COLUMN_ACHI_CONDITION
    };

    public static final int INDEX_COLUMN_ACHI_NAME = 0;
    public static final int INDEX_COLUMN_ACHI_DESCRIPTION = 1;
    public static final int INDEX_COLUMN_ACHI_PROGRESS = 2;
    public static final int INDEX_COLUMN_ACHI_CONDITION = 3;

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

        View view = inflater.inflate(R.layout.fragment_achievement,container,false);
        achievementFragmentAdapter = new AchievementFragmentAdapter(getActivity());

        mSwipeRefreshLayout = view.findViewById(R.id.achi_swipe_refresh_vieww);

        mLoadingIndicator = view.findViewById(R.id.achievement_load_indicator);
        mRecyclerView = view.findViewById(R.id.achievement_recycler_view);
        showLoding();

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(achievementFragmentAdapter);

        getLoaderManager().initLoader(ACHIEVEMENT_LOADER_ID,null,this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HurryPushSyncUtils.startImmediateSync(getActivity());
                mSwipeRefreshLayout.stopNestedScroll();
            }
        });
        return view;
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
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {
        achievementFragmentAdapter.swapCursor(null);
    }

    void showLoding(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    void showAchievementDataView(){
        mRecyclerView.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }
}
