package com.codefather.inshortsoutlinks.home;

import com.codefather.inshortsoutlinks.model.News;

import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface NewsApiManager {

    void fetchNews(NewsApiListener listener);

    public interface NewsApiListener{
        void onNewsFetched(List<News> newsList);

        void onNewsFetchFailure(Throwable t);
    }
}
