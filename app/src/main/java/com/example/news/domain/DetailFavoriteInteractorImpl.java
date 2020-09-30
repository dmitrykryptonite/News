package com.example.news.domain;

import com.example.news.data.repository.MainRepositoryImpl;
import com.example.news.entities.FavoriteArticle;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DetailFavoriteInteractorImpl implements DetailFavoriteInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();

    @Override
    public Single<FavoriteArticle> getFavoriteArticle() {
        return mainRepositoryImpl.getFavoriteArticle();
    }

    @Override
    public FavoriteArticle getNewsByTitleApiArticle(String title) {
        return mainRepositoryImpl.getNewsByTitleApiArticle(title);
    }

    @Override
    public Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                           String date, String author, String description,
                                           String pathToImage, String url) {
        return mainRepositoryImpl.saveNewsToFavorites(title, appbarTitle, appbarSubtitle, date,
                author, description, pathToImage, url);
    }

    @Override
    public Completable deleteNewsFromFavorites(String title) {
        return mainRepositoryImpl.deleteNewsFromFavorites(title);
    }
}
