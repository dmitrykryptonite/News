package com.example.news.domain;

import com.example.news.data.repository.MainRepositoryImpl;
import com.example.news.entities.data.ApiArticle;
import com.example.news.entities.data.ApiNews;

import io.reactivex.Single;

public class MainInteractorImpl implements MainInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();

    @Override
    public Single<ApiNews> getNews(String apiKey) {
        return mainRepositoryImpl.getNews(apiKey);
    }

    @Override
    public Single<ApiNews> getNewsSearch(String keyword, String apiKey) {
        return mainRepositoryImpl.getNewsSearch(keyword, apiKey);
    }

    @Override
    public void saveApiArticle(ApiArticle apiArticle) {
        mainRepositoryImpl.saveApiArticle(apiArticle);
    }
}
