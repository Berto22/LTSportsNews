package com.example.android.ltsportsnews.ui;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.remote.NewsUpdaterService;
import com.example.android.ltsportsnews.widget.NewsWidgetProvider;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static com.example.android.ltsportsnews.R.id.container;

public class NewsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private NewsAdapter newsAdapter;
    private static RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NewsActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsAdapter = new NewsAdapter(getActivity(), null);

        if (savedInstanceState == null) {
            refresh();
        }

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {
            String url = bundle.getString("articleUrl");
            Uri articleUri = Uri.parse(url);
            Intent websIntent = new Intent(Intent.ACTION_VIEW, articleUri);
            startActivity(websIntent);
        } else {
            return;
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MobileAds.initialize(getContext(), "ca-app-pub-1822618669019557/2990058229");
        View rootView = inflater.inflate(R.layout.fragment_news_activity, container, false);

        // Create ad request
        AdView adView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("3A1D3943584BE41A8DFCD807D62E70DD")
                .build();
        adView.loadAd(adRequest);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout =(SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newsAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(recyclerView.getChildCount() > 0) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }



    private void refresh() {
        getActivity().startService(new Intent(getActivity().getApplicationContext(), NewsUpdaterService.class));
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mRefreshingReceiver,
                new IntentFilter(NewsUpdaterService.BROADCAST_ACTION_STATE_CHANGE));
        updateRefreshingUi();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mRefreshingReceiver,
                new IntentFilter(NewsUpdaterService.BROADCAST_ACTION_STATE_CHANGE));
        updateRefreshingUi();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mRefreshingReceiver);
    }

    private boolean mIsRefreshing = false;

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(NewsUpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                mIsRefreshing = intent.getBooleanExtra(NewsUpdaterService.EXTRA_REFRESHING, false);
                updateRefreshingUi();
            }
        }
    };

    private void updateRefreshingUi() {
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.getActivity(),
                ItemsContract.NewsItemsEntry.CONTENT_URI,
                ItemsContract.NewsItemsEntry.NEWS_COLUMNS.toArray(new String[]{}),
                null, null, ItemsContract.NewsItemsEntry.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        newsAdapter.setmCursor(cursor);

        Context context = getContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, NewsWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.news_widget_list);
        DatabaseUtils.dumpCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        newsAdapter.setmCursor(null);

    }
}
