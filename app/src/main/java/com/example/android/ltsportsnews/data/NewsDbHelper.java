package com.example.android.ltsportsnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berto on 7/21/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SportsNews.db";
    private static final int VERSION = 2;

    NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " +
                ItemsContract.NewsItemsEntry.TABLE_NAME + " (" +
                ItemsContract.NewsItemsEntry._ID + " INTEGER PRIMARY KEY, " +
                ItemsContract.NewsItemsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ItemsContract.NewsItemsEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                ItemsContract.NewsItemsEntry.COLUMN_PUBLISH_DATE + " TEXT NOT NULL, " +
                ItemsContract.NewsItemsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                ItemsContract.NewsItemsEntry.COLUMN_ARTICLE_URL + " TEXT NOT NULL, " +
                ItemsContract.NewsItemsEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL);";

        final String SQL_CREATE_MY_TEAM_TABLE = "CREATE TABLE " +
                ItemsContract.TeamsEntry.TABLE_NAME + " (" +
                ItemsContract.TeamsEntry._ID + " INTEGER PRIMARY KEY, " +
                ItemsContract.TeamsEntry.COLUMN_TEAM_NAME + " TEXT NOT NULL, " +
                ItemsContract.TeamsEntry.LOGO_IMAGE_RESOURCE_ID + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MY_TEAM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemsContract.NewsItemsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemsContract.TeamsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
