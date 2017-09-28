package com.example.android.ltsportsnews.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by berto on 7/24/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    public static String TAG = NewsAdapter.class.toString();

    private final Context mContext;
    private Cursor mCursor;

    public NewsAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;

    }

    void setmCursor(Cursor cursor) {
        mCursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);

        return new NewsViewHolder(item);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String title = mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_TITLE);
        Log.d(TAG, "TITLE " + title);

        holder.titleTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_TITLE));
        Timber.d(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_TITLE));
        holder.authorTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_AUTHOR));

        SimpleDateFormat outPutFormat = new SimpleDateFormat("MMM dd,yyyy");
        Date publishedDate = parsePublishedDate();

        String stringDate = outPutFormat.format(publishedDate);

        holder.publishDateTextView.setText(stringDate);

        Picasso.with(mContext).load(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_IMAGE_URL))
                .fit()
                .into(holder.articleImageView);

        holder.descriptionTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_DESCRIPTION));


    }

    private Date parsePublishedDate() {
        try {
            String date = mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_PUBLISH_DATE);
            SimpleDateFormat inPutFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");

            return inPutFormat.parse(date);

        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            return new Date();
        }
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (mCursor != null) {
            count = mCursor.getCount();
        }
        return count;
    }

    /*@Override
    public void onClick(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri newsUri = Uri.parse(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_ARTICLE_URL));
                Intent websIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                mContext.startActivity(websIntent);
            }
        });

    } */

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //@BindView(R.id.title_textView)
        public TextView titleTextView;

        //@BindView(R.id.author_textView)
        public TextView authorTextView;

        //@BindView(R.id.publish_date_textView)
        public TextView publishDateTextView;

        //@BindView(R.id.article_imageView)
        public ImageView articleImageView;

        //@BindView(R.id.description_textView)
        public TextView descriptionTextView;


        NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            titleTextView = (TextView)view.findViewById(R.id.title_textView);
            authorTextView = (TextView)view.findViewById(R.id.author_textView);
            publishDateTextView =(TextView)view.findViewById(R.id.publish_date_textView);
            articleImageView = (ImageView)view.findViewById(R.id.article_imageView);
            descriptionTextView = (TextView)view.findViewById(R.id.description_textView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Uri newsUri = Uri.parse(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_ARTICLE_URL));
                    Intent websIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                    mContext.startActivity(websIntent);

                    String articleUrl = mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_ARTICLE_URL);

                    /*ArticleWebViewFragment awf = new ArticleWebViewFragment();
                    Bundle extras = new Bundle();
                    extras.putString("url", articleUrl);
                    awf.setArguments(extras);
                    FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.add(R.id.webView, awf);
                    ft.commit(); */



                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}
