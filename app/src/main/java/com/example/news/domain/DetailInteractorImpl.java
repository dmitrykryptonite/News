package com.example.news.domain;

import com.example.news.data.repository.MainRepositoryImpl;
import com.example.news.entities.data.ApiArticle;

import io.reactivex.Single;

public class DetailInteractorImpl implements DetailInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();

    @Override
    public Single<ApiArticle> getApiArticle() {
        return mainRepositoryImpl.getApiArticle();
    }
}
