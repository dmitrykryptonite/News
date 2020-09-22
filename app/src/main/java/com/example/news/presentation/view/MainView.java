package com.example.news.presentation.view;

import com.example.news.entities.data.ApiArticle;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showPanel(boolean isSearchingMode);

    @StateStrategyType(SkipStrategy.class)
    void setTextEditTextSearch(String text);

    @StateStrategyType(SkipStrategy.class)
    void hideKeyboard();

    @StateStrategyType(SkipStrategy.class)
    void editTextSearchRequestFocus();

    @StateStrategyType(SkipStrategy.class)
    void showKeyboardForEditTextSearch();

    @StateStrategyType(SkipStrategy.class)
    void editTextSearchClearFocus();

    @StateStrategyType(SkipStrategy.class)
    void updateActualNewsList(List<ApiArticle> apiArticles);

    @StateStrategyType(SkipStrategy.class)
    void stopRefreshing();

    @StateStrategyType(SkipStrategy.class)
    void recyclerViewMovingToStartPosition();

    @StateStrategyType(SkipStrategy.class)
    void editTextSearchSetText(String query);

    @StateStrategyType(SkipStrategy.class)
    void finishActivity();
}
