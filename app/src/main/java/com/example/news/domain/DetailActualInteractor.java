package com.example.news.domain;

import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DetailActualInteractor {
    Single<ApiArticle> getApiArticle();

    Single<String> saveImageByUrl(String url, String imageName);

    Completable deleteImageByPath(String pathToImage);

    FavoriteArticle getNewsByTitleApiArticle(String title);

    Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                    String date, String author, String description,
                                    String pathToImage, String url);

    Completable deleteNewsFromFavorites(String title);
}
