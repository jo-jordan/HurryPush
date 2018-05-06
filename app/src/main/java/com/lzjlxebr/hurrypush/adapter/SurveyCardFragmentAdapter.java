package com.lzjlxebr.hurrypush.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.lzjlxebr.hurrypush.base.CardAdapter;
import com.lzjlxebr.hurrypush.base.SurveyCardFragment;

import java.util.ArrayList;
import java.util.List;

public class SurveyCardFragmentAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<SurveyCardFragment> mFragments;
    private float mBaseElevation;


    public SurveyCardFragmentAdapter(FragmentManager fm, float baseElevation){
        super(fm);

        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for (int i = 0; i < 4; i++) {
            addCardFragment(new SurveyCardFragment());
        }
    }
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position,(SurveyCardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(SurveyCardFragment surveyCardFragment){
        mFragments.add(surveyCardFragment);
    }
}
