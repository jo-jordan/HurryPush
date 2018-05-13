package com.lzjlxebr.hurrypush.ui.base;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.db.HurryPushContract;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lzjlxebr.hurrypush.ui.base.MainActivity.TODAY_PROJECTION;

public class TodayFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = TodayFragment.class.getSimpleName();

    //private TodayFragmentAdapter todayFragmentAdapter;
    //private int mPosition = RecyclerView.NO_POSITION;

//    @BindView(R.id.today_recycler_view)
//    RecyclerView mRecyclerView;


    @BindView(R.id.today_load_error_msg)
    TextView mTvErrorMsg;

    @BindView(R.id.today_load_indicator)
    ProgressBar mLoadingIndicator;

    @BindString(R.string.load_error_msg)
    String loadErrorMsg;


    public static final int INDEX_COLUMN_START_TIME = 0;
    public static final int INDEX_COLUMN_END_TIME = 1;
    public static final int INDEX_COLUMN_IS_USER_FINISH = 2;
    public static final int INDEX_COLUMN_GAIN_EXP = 3;
    public static final int INDEX_COLUMN_SMELL = 4;
    public static final int INDEX_COLUMN_CONSTIPATION = 5;
    public static final int INDEX_COLUMN_STICKINESS = 6;
    public static final int INDEX_COLUMN_OVERALL_RATING = 7;
    public static final int INDEX_COLUMN_SERVER_CALL_BACK = 8;
    public static final int INDEX_COLUMN_INSERT_TIME = 9;

    private static final int LOADER_ID = 2002;
    public static String[] DEFECATION_RECORD_PROJECTION = {
            HurryPushContract.DefecationRecordEntry.COLUMN_START_TIME,
            HurryPushContract.DefecationRecordEntry.COLUMN_END_TIME,
            HurryPushContract.DefecationRecordEntry.COLUMN_IS_USER_FINISH,
            HurryPushContract.DefecationRecordEntry.COLUMN_GAIN_EXP,
            HurryPushContract.DefecationRecordEntry.COLUMN_SMELL,
            HurryPushContract.DefecationRecordEntry.COLUMN_CONSTIPATION,
            HurryPushContract.DefecationRecordEntry.COLUMN_STICKINESS,
            HurryPushContract.DefecationRecordEntry.COLUMN_OVERALL_RATING,
            HurryPushContract.DefecationRecordEntry.COLUMN_SERVER_CALL_BACK,
            HurryPushContract.DefecationRecordEntry.COLUMN_INSERT_TIME
    };

    private static final int TODAY_LOADER_ID = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_today, container, false);
        //todayFragmentAdapter = new TodayFragmentAdapter(getActivity());

        ButterKnife.bind(this, view);


        showLoading();

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        //mRecyclerView.setLayoutManager(layoutManager);
        //mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.setAdapter(todayFragmentAdapter);

        getLoaderManager().initLoader(TODAY_LOADER_ID, null, this);

        Log.d(LOG_TAG, "Todat fragment on creating.");
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case TODAY_LOADER_ID: {
                Uri todayQueryUri = HurryPushContract.ClientInfoEntry.CLIENT_INFO_URI;

                return new CursorLoader(
                        getActivity(),
                        todayQueryUri,
                        TODAY_PROJECTION,
                        null,
                        new String[]{"1"},
                        null
                );
            }
            default:
                throw new RuntimeException("Loader Not Implemented" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        //todayFragmentAdapter.swapCursor(data);
        //if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        //mRecyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0) showTodayDataView();
        else showErrorMsg();

    }

    private void showErrorMsg() {
        mTvErrorMsg.setText(loadErrorMsg);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //todayFragmentAdapter.swapCursor(null);
    }

    public void showTodayDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.INVISIBLE);
        //mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        //mRecyclerView.setVisibility(View.INVISIBLE);
        mTvErrorMsg.setVisibility(View.INVISIBLE);
    }

}
