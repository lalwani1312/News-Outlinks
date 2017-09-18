package com.codefather.inshortsoutlinks.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codefather.inshortsoutlinks.R;
import com.codefather.inshortsoutlinks.model.News;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> mNewsData;
    private NewsAdapterInteraction mListener;

    interface NewsAdapterInteraction {
        void onNewsAdded(int startPosition, int itemCount);

        void onNewsClicked(int position);

        void onBookmarkClicked(int position);
    }

    NewsAdapter(NewsAdapterInteraction listener) {
        mListener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_view, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.onBind(mNewsData.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsData == null ? 0 : mNewsData.size();
    }

    void updateNews(List<News> newsData) {
        mNewsData = newsData;
        notifyDataSetChanged();
    }

    void addNews(List<News> newsData) {
        if (newsData != null) {
            if (mNewsData == null) {
                mNewsData = new ArrayList<>();
            }
            int oldSize = mNewsData.size();
            mNewsData.addAll(newsData);
            if (mListener != null) {
                mListener.onNewsAdded(oldSize, newsData.size());
            }
        }
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle, mPublisher, mCategory, mTimestamp;
        private ImageView mBookmark;
        private News mNews;

        NewsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mPublisher = (TextView) itemView.findViewById(R.id.tv_publisher);
            mCategory = (TextView) itemView.findViewById(R.id.tv_category);
            mTimestamp = (TextView) itemView.findViewById(R.id.tv_timestamp);
            mBookmark = (ImageView) itemView.findViewById(R.id.iv_bookmark);
            mBookmark.setOnClickListener(this);
        }

        void onBind(News news) {
            mNews = news;
            mTitle.setText(news.getTitle());
            mPublisher.setText(news.getPublisher());
            mCategory.setText(getCategory(news.getCategory()));
            if (news.getIsBookmarked() == 1) {
                mBookmark.setImageResource(R.drawable.ic_bookmark_selected);
            } else {
                mBookmark.setImageResource(R.drawable.ic_bookmark_unselected);
            }
            mPublisher.setText(news.getPublisher());
            mTimestamp.setText(getTime(news.getTimestamp()));
        }

        private String getCategory(String category) {
            if (TextUtils.isEmpty(category)) {
                return "";
            } else if ("b".equalsIgnoreCase(category)) {
                return "Business";
            } else if ("t".equalsIgnoreCase(category)) {
                return "Science & Technology";
            } else if ("e".equalsIgnoreCase(category)) {
                return "Entertainment";
            } else if ("m".equalsIgnoreCase(category)) {
                return "Health";
            } else {
                return "Custom";
            }
        }

        String getTime(long time) {
            if (time == -1) {
                return null;
            }
            Date date = new Date(time);
            Format format = new SimpleDateFormat("dd MMM, yyyy");
            return format.format(date);
        }

        @Override
        public void onClick(View v) {

            if (mListener != null) {
                if (v.getId() == R.id.iv_bookmark) {
                    mListener.onBookmarkClicked(getAdapterPosition());
                } else {
                    mListener.onNewsClicked(getAdapterPosition());
                }
            }
        }
    }
}
