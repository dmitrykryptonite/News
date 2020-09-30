package com.example.news.presentation.view;

import com.example.news.entities.FavoriteArticle;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface FavoriteNewsView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void recyclerViewSavedScrollPosition();

    @StateStrategyType(SkipStrategy.class)
    void recyclerViewRestoreScrollPosition();

    @StateStrategyType(SkipStrategy.class)
    void updateFavoriteNewsList(List<FavoriteArticle> favoriteArticles);

    @StateStrategyType(SkipStrategy.class)
    void showSuccessMassage(String massage);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMassage(String massage);

    @StateStrategyType(SkipStrategy.class)
    void openDetailFavoriteScreen(FavoriteArticle favoriteArticle);
}
