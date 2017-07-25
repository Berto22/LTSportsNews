package com.example.android.ltsportsnews.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by berto on 7/24/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context mContext;
    private Cursor mCursor;

    public NewsAdapter(Context context) {
        mContext = context;

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

        holder.titleTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_ARTICLE_URL));
        holder.authorTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_AUTHOR));
        holder.publishDateTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_PUBLISH_DATE));

        Picasso.with(mContext).load(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_IMAGE_URL))
                .into(holder.articleImageView);

        holder.descriptionTextView.setText(mCursor.getString(ItemsContract.NewsItemsEntry.POSITION_DESCRIPTION));


    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mCursor != null) {
            count = mCursor.getCount();
        }
        return count;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title_textView)
        TextView titleTextView;

        @BindView(R.id.author_textView)
        TextView authorTextView;

        @BindView(R.id.publish_date_textView)
        TextView publishDateTextView;

        @BindView(R.id.article_imageView)
        ImageView articleImageView;

        @BindView(R.id.description_textView)
        TextView descriptionTextView;


        NewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
