package com.example.news.presentation.view;

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
}
