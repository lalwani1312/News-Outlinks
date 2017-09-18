package com.codefather.inshortsoutlinks.network;

import com.codefather.inshortsoutlinks.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface OutlinksService {

    @GET("newsjson")
    Call<List<News>> getNews();
}
