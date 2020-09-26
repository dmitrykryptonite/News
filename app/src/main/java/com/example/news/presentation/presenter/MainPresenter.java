package com.example.news.presentation.presenter;

import com.example.news.domain.MainInteractorImpl;
import com.example.news.entities.data.ApiArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.view.MainView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    private MainInteractorImpl mainInteractorImpl = new MainInteractorImpl();
    private boolean isSearchingMode = false;
    private Disposable disposableGetNews, disposableGetNewsSearch;
    private List<String> searchHistory = new ArrayList<>();
    private Router router;

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

    public void onScrollStateChanged(String query) {
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
        if (query.length() == 0) {
            isSearchingMode = false;
            getViewState().showPanel(isSearchingMode);
        }
    }

    public void onImeActionSearchClicked() {
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
        getViewState().recyclerViewMovingScrollToStartPosition();
    }

    public void searchHistoryAddItem(String query) {
        searchHistory.add(query);
    }

    public void onBackPressed(String apiKey) {
        if (searchHistory.size() > 1) {
            final String query = searchHistory.get(searchHistory.size() - 2);
            getNewsSearch(query, apiKey);
            getViewState().editTextSearchSetText(query);
            getViewState().recyclerViewMovingScrollToStartPosition();
            searchHistory.remove(searchHistory.size() - 1);
        } else if (searchHistory.size() == 1) {
            getNews(apiKey);
            getViewState().editTextSearchSetText("");
            isSearchingMode = false;
            getViewState().showPanel(isSearchingMode);
            getViewState().recyclerViewMovingScrollToStartPosition();
            searchHistory.remove(searchHistory.size() - 1);
        } else {
            searchHistory.size();
            getViewState().finishActivity();
        }
    }

    public void getNews(String apiKey) {
        disposableGetNews = mainInteractorImpl.getNews(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiNews -> {
                    getViewState().updateActualNewsList(apiNews.getArticles());
                    getViewState().stopRefreshing();
                });
    }

    public void getNewsSearch(String keyword, String apiKey) {
        disposableGetNewsSearch = mainInteractorImpl.getNewsSearch(keyword, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiNews -> {
                    getViewState().updateActualNewsList(apiNews.getArticles());
                    getViewState().stopRefreshing();
                });
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void openDetailScreen(ApiArticle apiArticle) {
        mainInteractorImpl.saveApiArticle(apiArticle);
        router.openDetailScreen();
    }

    public void onBtnFavoriteNewsClicked() {
        router.openFavoriteScreen();
    }

    public void onPauseView(String query) {
        getViewState().recyclerViewSavedScrollPosition();
        getViewState().editTextSearchClearFocus();
        getViewState().hideKeyboard();
        if (query.length() == 0) {
            isSearchingMode = false;
            getViewState().showPanel(isSearchingMode);
        }
    }

    public void onResumeView(String apiKey) {
        getViewState().showPanel(isSearchingMode);
        if (searchHistory.size() >= 1) {
            String query = searchHistory.get(searchHistory.size() - 1);
            getNewsSearch(query, apiKey);
        } else {
            getNews(apiKey);
        }
        getViewState().recyclerViewRestoreScrollPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableGetNews != null && disposableGetNews.isDisposed())
            disposableGetNews.dispose();
        if (disposableGetNewsSearch != null && disposableGetNewsSearch.isDisposed())
            disposableGetNewsSearch.dispose();
    }
}
