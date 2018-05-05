package com.lzjlxebr.hurrypush.adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lzjlxebr.hurrypush.base.StatisticsFragment;
import com.lzjlxebr.hurrypush.base.TodayFragment;
import com.lzjlxebr.hurrypush.base.AchievementFragment;

public class MainActivityAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"Today","Achievement","Statistics"};

    public MainActivityAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new TodayFragment();
            case 1:
                return new AchievementFragment();
            case 2:
                return new StatisticsFragment();
            default:
                return new TodayFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
