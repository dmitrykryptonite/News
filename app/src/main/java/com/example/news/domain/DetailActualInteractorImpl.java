package com.example.news.domain;

import android.graphics.Bitmap;

import com.example.news.data.repository.MainRepositoryImpl;
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
    public Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle, String date, String author, String description, String pathToImage) {
        return mainRepositoryImpl.saveNewsToFavorites(title, appbarTitle, appbarSubtitle, date, author, description, pathToImage);
    }

    @Override
    public Completable deleteNewsFromFavorites(int id) {
        return null;
    }
}
