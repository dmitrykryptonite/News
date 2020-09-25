package com.example.news.domain;

import com.example.news.entities.data.ApiArticle;

import io.reactivex.Single;

public interface DetailInteractor {
    Single<ApiArticle> getApiArticle();
}
