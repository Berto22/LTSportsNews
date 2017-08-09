package com.example.android.ltsportsnews.ui;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.remote.NewsUpdaterService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.button;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity {
    public static String TAG = MainActivity.class.toString();
    //private NewsPagerAdapter newsPagerAdapter;
    //private ViewPager viewPager;
    //@SuppressWarnings("WeakerAccess")
    //@BindView(R.id.recyclerView)
    //RecyclerView mRecyclerView;


    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager.setAdapter(newsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*RecyclerView recl = (RecyclerView) findViewById(R.id.recyclerView);

        DividerItemDecoration horizontalDecor = new DividerItemDecoration(recl.getContext(),
                DividerItemDecoration.HORIZONTAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.horizontal_divider);
        horizontalDecor.setDrawable(horizontalDivider);
        recl.addItemDecoration(horizontalDecor);

        adapter = new NewsAdapter(getApplicationContext(), null);

        if (savedInstanceState == null) {
            refresh();
        }

        getLoaderManager().initLoader(0, null, this);
        int numColumns = 1;
        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recl.setLayoutManager(layoutManager);
        recl.setLayoutManager(new GridLayoutManager(this, numColumns));

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recl.setAdapter(adapter); */



    }

    /*private void refresh() {
        startService(new Intent(this, NewsUpdaterService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver, new IntentFilter(NewsUpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    private boolean mIsRefreshing = false;

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(NewsUpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                mIsRefreshing = intent.getBooleanExtra(NewsUpdaterService.EXTRA_REFRESHING, false);
            }
        }
    };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                ItemsContract.NewsItemsEntry.CONTENT_URI,
                ItemsContract.NewsItemsEntry.NEWS_COLUMNS.toArray(new String[]{}),
                null, null, ItemsContract.NewsItemsEntry.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //adapter.setHasStableIds(true);
        //adapter = new NewsAdapter(this);
        //adapter.setHasStableIds(true);
        //mRecyclerView.setAdapter(adapter);
        adapter.setmCursor(cursor);
        Log.d(TAG, "MMMMMMMM");
        DatabaseUtils.dumpCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setmCursor(null);

    } */
}
