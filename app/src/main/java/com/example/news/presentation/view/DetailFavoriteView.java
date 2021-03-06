package com.example.news.presentation.view;

import moxy.MvpView;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface DetailFavoriteView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void setTextAppbarTitle(String strAppbarTitle);

    @StateStrategyType(SkipStrategy.class)
    void setTextAppbarSubtitle(String strAppbarSubtitle);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvDate(String date);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvBriefInfo(String briefInfo);

    @StateStrategyType(SkipStrategy.class)
    void setTextTvTitle(String title);

    @StateStrategyType(SkipStrategy.class)
    void showErrorPanelFromImageIfPermissionGranted(String error);

    @StateStrategyType(SkipStrategy.class)
    void showErrorPanelFromImageIfPermissionDenied(String error);

    @StateStrategyType(SkipStrategy.class)
    void hideErrorPanel();

    @StateStrategyType(SkipStrategy.class)
    void setImage(String pathToImage);

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
