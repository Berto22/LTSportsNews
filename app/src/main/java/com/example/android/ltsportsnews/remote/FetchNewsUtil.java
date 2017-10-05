package com.example.android.ltsportsnews.remote;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.android.ltsportsnews.data.ItemsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by berto on 7/20/2017.
 */

public class FetchNewsUtil {
    private static final String TAG = FetchNewsUtil.class.getSimpleName();
    private static Context mContext;

    private FetchNewsUtil() {

    }

    public static JSONArray fetchJsonArray() {
        String jsonArrayResponse = null;

        try {
            jsonArrayResponse = fetchNews(Config.BASE_URL);
        } catch (IOException e) {
            Timber.e(TAG, "Error fetching news", e);
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonArrayResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            return jsonArray;
        } catch (JSONException e) {
            Timber.e(TAG, "Error parsing JSON");
        }

        return null;
    }

    static String fetchNews(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void addMyTeam(Context context, String team, int teamLogo) {

        ContentValues values = new ContentValues();
        values.put(ItemsContract.TeamsEntry.COLUMN_TEAM_NAME, team);
        values.put(ItemsContract.TeamsEntry.LOGO_IMAGE_RESOURCE_ID, teamLogo);

        context.getContentResolver().insert(ItemsContract.TeamsEntry.CONTENT_URI, values);
    }

    public static void removeMyTeam(Context mContext, String team, int teamLogo) {

        Uri uri = ItemsContract.TeamsEntry.CONTENT_URI;
        String stringTeamLogo = Integer.toString(teamLogo);
        String[] selectionArgs = {team, stringTeamLogo};
        mContext.getContentResolver().delete(uri, ItemsContract.TeamsEntry._ID + "=?", selectionArgs);

    }
}
