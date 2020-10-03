package com.example.news.domain;

import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;
import com.example.news.entities.data.ApiNews;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainRepository {
    Single<ApiNews> getNews(String apiKey);

    Single<ApiNews> getNewsSearch(String keyword, String apiKey);

    void saveApiArticle(ApiArticle apiArticle);

    Single<ApiArticle> getApiArticle();

    Single<String> saveImageByUrl(String url, String imageName);

    Completable deleteImageByPath(String pathToImage);

    FavoriteArticle getNewsByTitleApiArticle(String title);

    Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                    String date, String author, String description,
                                    String pathToImage, String url);

    Completable deleteNewsFromFavorites(String title);

    void getListNews();

    void saveFavoriteArticle(FavoriteArticle favoriteArticle);

    Single<FavoriteArticle> getFavoriteArticle();
}
