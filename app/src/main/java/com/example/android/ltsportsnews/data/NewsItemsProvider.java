package com.example.android.ltsportsnews.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaCasException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.R.attr.y;

/**
 * Created by berto on 7/21/2017.
 */

public class NewsItemsProvider extends ContentProvider {
    public static final int NEWS = 100;
    public static final int NEWS_WITH_ID = 101;
    public static final int MY_TEAM = 200;
    public static final int MY_TEAM_WITH_ID = 201;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ItemsContract.AUTHORITY, ItemsContract.PATH, NEWS);
        uriMatcher.addURI(ItemsContract.AUTHORITY, ItemsContract.PATH + "/*", NEWS_WITH_ID);
        uriMatcher.addURI(ItemsContract.AUTHORITY, ItemsContract.PATH_TEAMS, MY_TEAM);
        uriMatcher.addURI(ItemsContract.AUTHORITY, ItemsContract.PATH_TEAMS + "/*", MY_TEAM_WITH_ID );
        return uriMatcher;
    }

    private NewsDbHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new NewsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NEWS:
                return ItemsContract.NewsItemsEntry.CONTENT_TYPE;
            case NEWS_WITH_ID:
                return ItemsContract.NewsItemsEntry.CONTENT_ITEM_TYPE;
            case MY_TEAM:
                return ItemsContract.TeamsEntry.CONTENT_TYPE;
            case MY_TEAM_WITH_ID:
                return ItemsContract.TeamsEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (match) {
            case NEWS:
                returnCursor = db.query(ItemsContract.NewsItemsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case NEWS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String[] mSelectionArgs = new String[]{id};

                returnCursor = db.query(ItemsContract.NewsItemsEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MY_TEAM:
                returnCursor = db.query(ItemsContract.TeamsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
                break;
            case MY_TEAM_WITH_ID:
                String teamId = uri.getPathSegments().get(1);
                String teamSelection = "_id=?";
                String[] teamSelectionArgs = new String[]{teamId};

                returnCursor = db.query(ItemsContract.TeamsEntry.TABLE_NAME,
                        projection,
                        teamSelection,
                        teamSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case NEWS:
                db.insert(ItemsContract.NewsItemsEntry.TABLE_NAME, null, contentValues);
                returnUri = ItemsContract.NewsItemsEntry.CONTENT_URI;
                break;

            case MY_TEAM:
                db.insert(ItemsContract.TeamsEntry.TABLE_NAME, null, contentValues);
                returnUri = ItemsContract.TeamsEntry.CONTENT_URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if (selection == null) selection = "1";
        switch (match) {
            case NEWS:
                rowsDeleted =db.delete(ItemsContract.NewsItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MY_TEAM:
                rowsDeleted = db.delete(ItemsContract.TeamsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NEWS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ItemsContract.NewsItemsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case MY_TEAM:
                db.beginTransaction();
                int teamReturnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ItemsContract.TeamsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            teamReturnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return teamReturnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
