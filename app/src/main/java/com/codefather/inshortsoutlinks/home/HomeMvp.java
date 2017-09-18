package com.codefather.inshortsoutlinks.home;

import com.codefather.inshortsoutlinks.model.News;
import com.codefather.inshortsoutlinks.mvp.MvpPresenter;
import com.codefather.inshortsoutlinks.mvp.MvpView;

import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface HomeMvp {

    interface View extends MvpView {

        void showLoading();

        void hideLoading();

        void addToRvData(List<News> newsList);

        void showBookmark();

        void hideBookmark();

        void showFilterSortContainer();

        void hideFilterSortContainer();

        void showNews();

        void hideNews();

        void showEmpty();

        void hideEmpty();

        void showDataLoading();

        void hideDataLoading();

        void log(String msg);

        void updateBookmark(int position, News news);

        void navigateToWebActivity(int position, News news);
    }

    abstract class Presenter extends MvpPresenter<View> {

        public Presenter() {
            super(HomeMvp.View.class);
        }

        public abstract void onLoadMore(int page);
        public abstract void onBookmarkClicked(int position);
        public abstract void onNewsClicked(int position);
    }
}
