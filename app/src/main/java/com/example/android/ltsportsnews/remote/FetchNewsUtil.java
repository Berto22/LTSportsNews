package com.example.android.ltsportsnews.remote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by berto on 7/20/2017.
 */

public class FetchNewsUtil {
    private static final String TAG = FetchNewsUtil.class.getSimpleName();

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

        // Parse JSON
        try {
            JSONTokener tokener = new JSONTokener(jsonArrayResponse);
            Object val = tokener.nextValue();
            if (!(val instanceof JSONArray)) {
                throw new JSONException("Expected JSONArray");
            }
            return (JSONArray) val;
        } catch (JSONException e) {
            Timber.e(TAG, "Error parssng items", e);
        }

        return null;
    }

    static String fetchNews(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
