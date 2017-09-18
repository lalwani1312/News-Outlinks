package com.codefather.inshortsoutlinks.factory;

import com.codefather.inshortsoutlinks.model.News;

import java.util.List;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface DbRepository {

    void saveDataToDb(List<News> newsList);

    void saveNewsToDb(News news);
}
