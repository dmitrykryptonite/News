package com.example.news.domain;

import com.example.news.entities.FavoriteArticle;

public interface FavoriteNewsInteractor {
    void getListNews();

    void saveFavoriteArticle(FavoriteArticle favoriteArticle);
}
