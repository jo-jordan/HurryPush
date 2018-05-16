package com.lzjlxebr.hurrypush.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lzjlxebr.hurrypush.R;
import com.lzjlxebr.hurrypush.adapter.SurveyCardFragmentAdapter;
import com.lzjlxebr.hurrypush.entity.DefecationEvent;
import com.lzjlxebr.hurrypush.entity.DefecationFinalRecord;
import com.lzjlxebr.hurrypush.entity.EmptyEvent;
import com.lzjlxebr.hurrypush.entity.SurveyEntry;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

public class SurveyActivity extends AppCompatActivity {
    private static final String LOG_TAG = SurveyActivity.class.getSimpleName();

    public SurveyCardFragmentAdapter mSurveyCardFragmentAdapter;
    public SurveyEntry mSurveyEntry = new SurveyEntry(0, 0, 0);
    private DefecationEvent mDefecationEvent;
    private ShadowTransformer mShadowTransformer;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        ButterKnife.bind(this);

        initToolBar();
        initVariables();

        mViewPager.setAdapter(mSurveyCardFragmentAdapter);
        mViewPager.setPageTransformer(false, mShadowTransformer);
        setScaleable();

        EventBus.getDefault().register(this);
    }

    // set toolbar as actionbar
    public void initToolBar() {
        Toolbar toolbar = findViewById(R.id.survey_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventMainThread(EmptyEvent event) {

        if (event == null) {
            Log.d(LOG_TAG, "event is null.");
            return;
        }
        if (event instanceof DefecationEvent) {
            mDefecationEvent = (DefecationEvent) event;

            EventBus.getDefault().removeStickyEvent(event);
        }
        if (event instanceof DefecationFinalRecord) {
            addNewResultCardToTheLastOfPager();

        }
    }

    private void addNewResultCardToTheLastOfPager() {
        SurveyResultCardFragment fragment = new SurveyResultCardFragment();
        mSurveyCardFragmentAdapter.addCardFragment(fragment);
        Bundle bundle = new Bundle();
        bundle.putString("card_fragment", "crad_fragment_5");
        fragment.setArguments(bundle);

        mSurveyCardFragmentAdapter.notifyDataSetChanged();

        mViewPager.setCurrentItem(5);
    }

    private void removeResultCard(int index) {
        mSurveyCardFragmentAdapter.removeCradView(index);
        mSurveyCardFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    private void initVariables() {
        mSurveyCardFragmentAdapter = new SurveyCardFragmentAdapter(getSupportFragmentManager(), dpToPixels(2, this));

        mViewPager = findViewById(R.id.survey_view_pager);
        mShadowTransformer = new ShadowTransformer(mViewPager, mSurveyCardFragmentAdapter);
    }

    private float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void setScaleable() {
        mShadowTransformer.enableScaling();

    }

    public SurveyEntry getSurveyEntry() {
        if (mSurveyEntry != null)
            return mSurveyEntry;
        else
            return new SurveyEntry(0, 0, 0);
    }

    public void setSurveyEntry(SurveyEntry surveyEntry) {
        this.mSurveyEntry = surveyEntry;
    }

    public void setSmell(int smell) {
        mSurveyEntry.setSmell(smell);
    }

    public void setConstipation(int constipation) {
        mSurveyEntry.setConstipation(constipation);
    }

    public void setStickiness(int stickiness) {
        mSurveyEntry.setStickiness(stickiness);
    }

    public DefecationEvent getDefecationEvent() {
        return mDefecationEvent;
    }
}
