package com.example.android.ltsportsnews.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.ltsportsnews.R;

/**
 * Created by berto on 8/1/2017.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new NewsActivityFragment();
            case 1:
                return new FavoriteTeams();

        }
        return null;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0) {
            return "News";
        } else {
            return "My Teams";
        }
    }
}
