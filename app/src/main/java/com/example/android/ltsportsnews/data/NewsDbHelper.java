package com.example.android.ltsportsnews.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by berto on 7/21/2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SportsNews.db";
    private static final int VERSION = 1;

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
                ItemsContract.NewsItemsEntry.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ItemsContract.NewsItemsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
