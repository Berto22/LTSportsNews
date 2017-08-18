package com.example.android.ltsportsnews.data;

/**
 * Created by berto on 8/3/2017.
 */

public class SportsTeams {
    private String mTeam;
    private int mImageResourceId;
    boolean mSelected = false;

    public SportsTeams (String team, int imageResourceId) {
        mTeam = team;
        mImageResourceId = imageResourceId;
    }

    public String getmTeam() {
        return mTeam;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public void setmTeam(String mTeam) {
        this.mTeam = mTeam;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public void setmSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }

    public boolean ismSelected() {
        return mSelected;
    }
}
