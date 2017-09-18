package com.codefather.inshortsoutlinks.factory.impl;

import com.codefather.inshortsoutlinks.factory.DbRepository;
import com.codefather.inshortsoutlinks.model.News;

import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public class AppDbRepository implements DbRepository {
    @Override
    public void saveDataToDb(List<News> newsList) {
        for (News news : newsList) {
            news.save();
        }
    }

    @Override
    public void saveNewsToDb(News news) {
        news.save();
    }
}
