package com.example.news.domain;

import com.example.news.entities.FavoriteArticle;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DetailFavoriteInteractor {
    Single<FavoriteArticle> getFavoriteArticle();

    FavoriteArticle getNewsByTitleApiArticle(String title);

    Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                    String date, String author, String description,
                                    String pathToImage, String url);

    Completable deleteNewsFromFavorites(String title);

    Completable deleteImageByPath(String pathToImage);
}
