package com.example.news.presentation.view;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface DetailActualView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void setTextAppbarTitle(String strAppbarTitle);

    @StateStrategyType(SkipStrategy.class)
    void setTextAppbarSubtitle(String strAppbarSubtitle);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvDate(String date);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvTime(String time);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvTitle(String title);

    @StateStrategyType(SkipStrategy.class)
    void setImage(String urlToImage);

    @StateStrategyType(SkipStrategy.class)
    void setUrlToWebView(String url);

    @StateStrategyType(SkipStrategy.class)
    void btnAddToFavoriteSetColorYellow();

    @StateStrategyType(SkipStrategy.class)
    void btnAddToFavoriteSetColorWhite();

    @StateStrategyType(SkipStrategy.class)
    void showSuccessMassage(String massage);

    @StateStrategyType(SkipStrategy.class)
    void showErrorMassage(String massage);

    @StateStrategyType(SkipStrategy.class)
    void finishActivity();
}
