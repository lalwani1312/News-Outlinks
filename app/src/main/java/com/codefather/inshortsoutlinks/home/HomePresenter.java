package com.codefather.inshortsoutlinks.home;

import com.codefather.inshortsoutlinks.factory.DbRepository;
import com.codefather.inshortsoutlinks.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public class HomePresenter extends HomeMvp.Presenter implements NewsApiManager.NewsApiListener {

    private static final int PER_PAGE = 30;

    private DbRepository mDbRepository;
    private NewsApiManager mNewsApiManager;
    private Executor mDbThreadPool;
    private State mState;

    public HomePresenter(DbRepository dbRepository, NewsApiManager newsApiManager,
                         Executor dbThreadPool, State state) {
        mDbRepository = dbRepository;
        mNewsApiManager = newsApiManager;
        mDbThreadPool = dbThreadPool;
        mState = state;
    }

    @Override
    public void attachView(HomeMvp.View view) {
        super.attachView(view);
        mNewsApiManager.fetchNews(this);
    }

    @Override
    public void onNewsFetched(List<News> newsList) {
        mState.newsList = newsList;
        if (newsList != null) {
            addDataToList();
            getView().hideEmpty();
            getView().showFilterSortContainer();
        } else {
            getView().showEmpty();
            getView().hideFilterSortContainer();
        }
        getView().hideLoading();
    }

    @Override
    public void onNewsFetchFailure(Throwable t) {
        getView().hideLoading();
    }

    private void saveDataToDb(final List<News> newsList) {
        final List<News> finalNewsList = new ArrayList<>(newsList);
        mDbThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mDbRepository.saveDataToDb(finalNewsList);
            }
        });
    }

    @Override
    public void onLoadMore(int page) {
        addDataToList();
    }

    @Override
    public void onBookmarkClicked(int position) {
        News news = mState.newsList.get(position);
        if (news.getIsBookmarked() == 1) {
            news.setIsBookmarked(0);
        } else {
            news.setIsBookmarked(1);
        }
        mDbRepository.saveNewsToDb(news);
        getView().updateBookmark(position, news);
    }

    @Override
    public void onNewsClicked(int position) {
        getView().navigateToWebActivity(position, mState.newsList.get(position));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDbThreadPool.onDestroy();
    }

    private void addDataToList() {
        if (mState.newsList != null) {
            ++mState.currentPage;
            int size = mState.newsList.size();
            int startPosition = mState.currentPage * PER_PAGE;
            if (startPosition <= size) {
                int endPosition = startPosition + PER_PAGE;
                if (endPosition > size) {
                    endPosition = size;
                }
                List<News> newsList = mState.newsList.subList(startPosition, endPosition);
                saveDataToDb(newsList);
                getView().addToRvData(newsList);
                getView().log("startPosition: " + startPosition + ", endPosition" + endPosition);
            }
        }
    }

    public static class State {
        List<News> newsList;
        int currentPage = -1;
    }
}
