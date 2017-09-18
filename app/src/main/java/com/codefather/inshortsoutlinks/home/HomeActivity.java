package com.codefather.inshortsoutlinks.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.codefather.inshortsoutlinks.Outlinks;
import com.codefather.inshortsoutlinks.R;
import com.codefather.inshortsoutlinks.browser.WebActivity;
import com.codefather.inshortsoutlinks.model.News;
import com.codefather.inshortsoutlinks.mvp.MvpBaseActivity;
import com.codefather.inshortsoutlinks.ui.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends MvpBaseActivity<HomeMvp.View, HomeMvp.Presenter>
        implements HomeMvp.View, NewsAdapter.NewsAdapterInteraction {

    private static final int REQUEST_CODE_WEB_ACTIVITY = 0;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.rv_news)
    RecyclerView mRecyclerView;

    @BindView(R.id.filter_sort_container)
    LinearLayout mFilterSortContainer;

    private NewsAdapter mAdapter;
    private RecyclerView.OnScrollListener mScrollListener;
    private boolean mIsBookmarkAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new NewsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mScrollListener
                = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                getPresenter().onLoadMore(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(mScrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_bookmark).setVisible(mIsBookmarkAvailable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_bookmark) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_WEB_ACTIVITY && resultCode == RESULT_OK) {
            int position = data.getIntExtra(WebActivity.EXTRA_POSITION, -1);
            News news = data.getParcelableExtra(WebActivity.EXTRA_NEWS);
            if (position != -1) {
                mAdapter.notifyItemChanged(position);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected HomeMvp.Presenter createPresenter() {
        return new HomePresenter(Outlinks.getInstance().factory().dbRepository(),
                new RetrofitNewsApiManager(), new DbThreadPoolExecutor(),
                new HomePresenter.State());
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void addToRvData(List<News> newsList) {
        mAdapter.addNews(newsList);
    }

    @Override
    public void showBookmark() {
        mIsBookmarkAvailable = true;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void hideBookmark() {
        mIsBookmarkAvailable = false;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void showFilterSortContainer() {
        mFilterSortContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideFilterSortContainer() {
        mFilterSortContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNews() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNews() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void hideEmpty() {

    }

    @Override
    public void showDataLoading() {

    }

    @Override
    public void hideDataLoading() {

    }

    @Override
    public void log(String msg) {
        Log.d("HomePresenter", msg);
    }

    @Override
    public void updateBookmark(int position, News news) {
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void navigateToWebActivity(int position, News news) {
        WebActivity.launchForResult(this, REQUEST_CODE_WEB_ACTIVITY, position, news);
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
//        startActivity(intent);
    }

    @Override
    public void onNewsAdded(final int startPosition, final int itemCount) {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemRangeInserted(startPosition, itemCount);
            }
        });
    }

    @Override
    public void onNewsClicked(int position) {
        getPresenter().onNewsClicked(position);
    }

    @Override
    public void onBookmarkClicked(int position) {
        getPresenter().onBookmarkClicked(position);
    }
}
