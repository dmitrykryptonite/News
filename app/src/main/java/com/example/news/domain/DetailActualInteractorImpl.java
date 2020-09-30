package com.example.news.domain;

import android.graphics.Bitmap;

import com.example.news.data.repository.MainRepositoryImpl;
import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DetailActualInteractorImpl implements DetailActualInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();

    @Override
    public Single<ApiArticle> getApiArticle() {
        return mainRepositoryImpl.getApiArticle();
    }

    @Override
    public String saveImage(Bitmap image, String imageName) {
        return mainRepositoryImpl.saveImage(image, imageName);
    }

    @Override
    public FavoriteArticle getNewsByTitleApiArticle(String title) {
        return mainRepositoryImpl.getNewsByTitleApiArticle(title);
    }

    @Override
    public Completable saveNewsToFavorites(String title, String appbarTitle,
                                           String appbarSubtitle, String date, String author,
                                           String description, String pathToImage, String url) {
        return mainRepositoryImpl.saveNewsToFavorites(title, appbarTitle,
                appbarSubtitle, date, author, description, pathToImage, url);
    }


    @Override
    public Completable deleteNewsFromFavorites(String title) {
        return mainRepositoryImpl.deleteNewsFromFavorites(title);
    }
}
