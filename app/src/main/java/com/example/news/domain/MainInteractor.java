package com.example.news.domain;

import com.example.news.entities.data.ApiArticle;
import com.example.news.entities.data.ApiNews;

import io.reactivex.Single;

public interface MainInteractor {
    Single<ApiNews> getNews(String apiKey);

    Single<ApiNews> getNewsSearch(String keyword, String apiKey);

    void saveApiArticle(ApiArticle apiArticle);
}
