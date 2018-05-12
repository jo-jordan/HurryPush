package com.lzjlxebr.hurrypush.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.lzjlxebr.hurrypush.ui.base.SurveyCardAbstractFragment;
import com.lzjlxebr.hurrypush.ui.base.SurveyCardFragment;
import com.lzjlxebr.hurrypush.ui.base.SurveyFinalCardFragment;

import java.util.ArrayList;
import java.util.List;

public class SurveyCardFragmentAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<SurveyCardAbstractFragment> mFragments;
    private float mBaseElevation;

    public SurveyCardFragmentAdapter(FragmentManager fm, float baseElevation) {
        super(fm);

        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for (int i = 0; i < 3; i++) {
            SurveyCardAbstractFragment fragment = new SurveyCardFragment();
            Bundle bundle = new Bundle();
            bundle.putString("card_fragment", "crad_fragment_" + i);
            fragment.setArguments(bundle);

            addCardFragment(fragment);
        }

        SurveyCardAbstractFragment fragment = new SurveyFinalCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("card_fragment", "crad_fragment_3");
        fragment.setArguments(bundle);
        //fm.beginTransaction().add(fragment,"card_fragment_3").commit();

        addCardFragment(fragment);
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
        return (CardView) mFragments.get(position).getCardView();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (SurveyCardAbstractFragment) fragment);
        return fragment;
    }

    public void addCardFragment(SurveyCardAbstractFragment surveyCardFragment) {
        mFragments.add(surveyCardFragment);
    }
}
