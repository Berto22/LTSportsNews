package com.example.android.ltsportsnews.remote;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

/**
 * Created by berto on 7/20/2017.
 */

public class Config {
    public static String TAG = Config.class.toString();
    public static final URL BASE_URL;

    static {
        URL url = null;
        try {
            url = new URL(" ");
        } catch (MalformedURLException e) {
            Timber.e(e);
        }
        BASE_URL = url;
    }

}
