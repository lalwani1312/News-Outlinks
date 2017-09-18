package com.codefather.inshortsoutlinks.home;

import com.codefather.inshortsoutlinks.model.News;
import com.codefather.inshortsoutlinks.network.ApiClient;
import com.codefather.inshortsoutlinks.network.OutlinksService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public class RetrofitNewsApiManager implements NewsApiManager {

    private OutlinksService mOutlinksService;

    @Override
    public void fetchNews(final NewsApiListener listener) {
        if (mOutlinksService == null) {
            mOutlinksService = ApiClient.getClient().create(OutlinksService.class);
        }
        Call<List<News>> call = mOutlinksService.getNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (listener != null) {
                    listener.onNewsFetched(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                if (listener != null) {
                    listener.onNewsFetchFailure(t);
                }
            }
        });
    }
}
