package com.example.android.ltsportsnews.remote;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.DatabaseUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.data.NewsItemsProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

import timber.log.Timber;

import static android.R.attr.handle;

/**
 * Created by berto on 7/23/2017.
 */

public class NewsUpdaterService extends IntentService {
    private static final String TAG = NewsUpdaterService.class.getSimpleName();
    public static final String BROADCAST_ACTION_STATE_CHANGE = "com.example.ltsportsnews.intent.action.STATE.CHANGE";
    public static final String EXTRA_REFRESHING = "com.example.ltsports.intent.extra.REFRESHING";

    public NewsUpdaterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Timber.d("Network not available");
            return;
        }

        sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));

        ArrayList<ContentProviderOperation> cpo = new ArrayList<ContentProviderOperation>();

        Uri uri = ItemsContract.NewsItemsEntry.CONTENT_URI;

        try {
            JSONArray jsonArray = FetchNewsUtil.fetchJsonArray();
            if (jsonArray == null) {
                throw new JSONException("No JSONArray");
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                ContentValues values = new ContentValues();
                JSONObject object = jsonArray.getJSONObject(i);
                values.put(ItemsContract.NewsItemsEntry.COLUMN_TITLE, object.getString("title" ));
                String sTitle = object.getString("title");
                Timber.d(sTitle);
                Log.d(TAG, sTitle);
                values.put(ItemsContract.NewsItemsEntry.COLUMN_AUTHOR, object.getString("author"));
                values.put(ItemsContract.NewsItemsEntry.COLUMN_DESCRIPTION, object.getString("description"));
                values.put(ItemsContract.NewsItemsEntry.COLUMN_ARTICLE_URL, object.getString("url"));
                values.put(ItemsContract.NewsItemsEntry.COLUMN_IMAGE_URL, object.getString("urlToImage"));
                values.put(ItemsContract.NewsItemsEntry.COLUMN_PUBLISH_DATE, object.getString("publishedAt"));

                cpo.add(ContentProviderOperation.newInsert(uri).withValues(values).build());

            }


            getContentResolver().applyBatch(ItemsContract.AUTHORITY, cpo);

        } catch (JSONException | RemoteException | OperationApplicationException e) {
            Timber.e(TAG, "Error updating news", e);

        }
        sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));
    }
}
