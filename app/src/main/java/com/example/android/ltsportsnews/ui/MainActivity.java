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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.remote.NewsUpdaterService;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    //@SuppressWarnings("WeakerAccess")
    //@BindView(R.id.recyclerView)
    //RecyclerView mRecyclerView;

    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        RecyclerView recl = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new NewsAdapter(this);

        if (savedInstanceState == null) {
            refresh();
        }

        getLoaderManager().initLoader(0, null, this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recl.setLayoutManager(layoutManager);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recl.setAdapter(adapter);


    }

    private void refresh() {
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
        DatabaseUtils.dumpCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setmCursor(null);

    }
}
