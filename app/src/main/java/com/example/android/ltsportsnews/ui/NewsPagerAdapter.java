package com.example.android.ltsportsnews.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.ltsportsnews.R;

/**
 * Created by berto on 8/1/2017.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public NewsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
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

        switch (position) {
            case 0:
                return mContext.getResources().getString(R.string.news);

            case 1:
                return mContext.getResources().getString(R.string.my_teams);

        }

        return null;
    }
}
