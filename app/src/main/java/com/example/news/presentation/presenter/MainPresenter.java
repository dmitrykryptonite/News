package com.example.news.presentation.presenter;

import com.example.news.presentation.view.MainView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private boolean isSearchingMode = false;

    public void onBtnSearchClicked() {
        isSearchingMode = true;
        getViewState().showPanel(isSearchingMode);
        getViewState().editTextSearchRequestFocus();
        getViewState().showKeyboardForEditTextSearch();
    }

    public void onBtnCloseSearchViewClicked() {
        isSearchingMode = false;
        getViewState().showPanel(isSearchingMode);
        getViewState().setTextEditTextSearch("");
        getViewState().hideKeyboard();
    }

    public void onScrollStateChanged() {
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
    }

    public void onImeActionSearchClicked() {
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
    }

    public void onPauseView() {
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
    }

    public void onResumeView() {
        getViewState().showPanel(isSearchingMode);
    }
}
