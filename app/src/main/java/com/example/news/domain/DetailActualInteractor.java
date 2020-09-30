package com.example.news.domain;

import android.graphics.Bitmap;

import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DetailActualInteractor {
    Single<ApiArticle> getApiArticle();

    String saveImage(Bitmap image, String imageName);

    FavoriteArticle getNewsByTitleApiArticle(String title);

    Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                    String date, String author, String description,
                                    String pathToImage, String url);

    Completable deleteNewsFromFavorites(String title);
}