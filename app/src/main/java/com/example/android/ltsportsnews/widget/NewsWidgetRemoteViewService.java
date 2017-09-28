package com.example.android.ltsportsnews.widget;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.ui.FavoriteTeams;

/**
 * Created by berto on 8/25/2017.
 */

public class NewsWidgetRemoteViewService extends RemoteViewsService {
    public String TAG = NewsWidgetRemoteViewService.class.getSimpleName();
    private final String[] NEW_COLUMNS = {
            ItemsContract.NewsItemsEntry._ID,
            ItemsContract.NewsItemsEntry.COLUMN_TITLE,
            ItemsContract.NewsItemsEntry.COLUMN_AUTHOR,
            ItemsContract.NewsItemsEntry.COLUMN_ARTICLE_URL
    };

    private static final int INDEX_ID = 0;
    private static final int INDEX_TITLE = 1;
    private static final int INDEX_AUTHOR = 2;
    private static final int INDEX_ARTICLE_URL = 3;


    public NewsWidgetRemoteViewService() {
        super();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        //return null;
        return new RemoteViewsFactory() {
            private Cursor data = null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if(data != null) {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(ItemsContract.NewsItemsEntry.CONTENT_URI,
                        NEW_COLUMNS,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
                DatabaseUtils.dumpCursor(data);

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }

            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                if (i == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(i)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.news_widget_detail);
                String title = data.getString(INDEX_TITLE);
                String author = data.getString(INDEX_AUTHOR);
                String articleUrl = data.getString(INDEX_ARTICLE_URL);

                Log.d(TAG, title + "///////// " + author);

                views.setTextViewText(R.id.news_widget_title, title);
                views.setTextViewText(R.id.news_widget_author, author);

                Intent fillIntent = new Intent();
                fillIntent.putExtra("articleUrl", articleUrl);
                views.setOnClickFillInIntent(R.id.news_widget_detail, fillIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.news_widget_detail);


            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                if (data.moveToPosition(i))
                    return data.getLong(INDEX_ID);
                return  i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
