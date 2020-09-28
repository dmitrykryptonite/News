package com.example.news.data.api.services;

import com.example.news.entities.data.ApiNews;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Single<ApiNews> getNews(@Query("country") String country,
                            @Query("language") String language,
                            @Query("apiKey") String apiKey);

    @GET("everything")
    Single<ApiNews> getNewsSearch(@Query("q") String keyword,
                                  @Query("language") String language,
                                  @Query("sortBy") String sortBy,
                                  @Query("apiKey") String apiKey
    );
}
