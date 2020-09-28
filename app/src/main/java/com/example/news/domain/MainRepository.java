package com.example.news.domain;

import android.graphics.Bitmap;

import com.example.news.entities.data.ApiArticle;
import com.example.news.entities.data.ApiNews;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainRepository {
    Single<ApiNews> getNews(String apiKey);

    Single<ApiNews> getNewsSearch(String keyword, String apiKey);

    void saveApiArticle(ApiArticle apiArticle);

    Single<ApiArticle> getApiArticle();

    String saveImage(Bitmap image, String imageName);

    Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                    String date, String author, String description, String pathToImage);

    void getListNews();

    Completable deleteNewsFromFavorites(int id);
}
