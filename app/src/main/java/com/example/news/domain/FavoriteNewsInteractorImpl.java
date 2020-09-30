package com.example.news.domain;

import com.example.news.data.repository.MainRepositoryImpl;
import com.example.news.entities.FavoriteArticle;

import java.util.List;

import io.reactivex.Observable;

public class FavoriteNewsInteractorImpl implements FavoriteNewsInteractor {
    private MainRepositoryImpl mainRepositoryImpl = MainRepositoryImpl.getInstance();
    public Observable<List<FavoriteArticle>> favoriteArticlesUpdateListener =
            mainRepositoryImpl.favoriteArticlesUpdateListener;

    @Override
    public void getListNews() {
        mainRepositoryImpl.getListNews();
    }

    @Override
    public void saveFavoriteArticle(FavoriteArticle favoriteArticle) {
        mainRepositoryImpl.saveFavoriteArticle(favoriteArticle);
    }
}
