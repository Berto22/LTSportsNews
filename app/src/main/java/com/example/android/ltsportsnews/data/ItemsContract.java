package com.example.android.ltsportsnews.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.provider.BaseColumns;

import com.google.common.collect.ImmutableList;

import java.util.AbstractMap;

/**
 * Created by berto on 7/20/2017.
 */

public class ItemsContract {
    public static final String AUTHORITY = "com.example.android.ltsportsnews";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "news";

    private ItemsContract () {
        throw new AssertionError("No contract instance");
    }

    @SuppressWarnings("unused")
    public static final class NewsItemsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + AUTHORITY + "/" + PATH;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + AUTHORITY + "/" + PATH;

        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ARTICLE_URL = "article_url";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_PUBLISH_DATE = "publish_date";
        public static final String TABLE_NAME = "news";
        public static final int POSITION_ID = 0;
        public static final int POSITION_TITLE = 1;
        public static final int POSITION_AUTHOR = 2;
        public static final int POSITION_DESCRIPTION = 3;
        public static final int POSITION_ARTICLE_URL = 4;
        public static final int POSITION_IMAGE_URL = 5;
        public static final int POSITION_PUBLISH_DATE = 6;
        public static final ImmutableList<String> NEWS_COLUMNS = ImmutableList.of(
                _ID,
                COLUMN_TITLE,
                COLUMN_AUTHOR,
                COLUMN_DESCRIPTION,
                COLUMN_ARTICLE_URL,
                COLUMN_IMAGE_URL,
                COLUMN_PUBLISH_DATE
        );

        public static final String DEFAULT_SORT = COLUMN_PUBLISH_DATE + " DESC";

        public static Uri buildNewsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
