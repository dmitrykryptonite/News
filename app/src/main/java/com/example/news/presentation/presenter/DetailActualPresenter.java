package com.example.news.presentation.presenter;

import android.graphics.Bitmap;

import com.example.news.data.utils.Utils;
import com.example.news.domain.DetailActualInteractorImpl;
import com.example.news.entities.data.ApiArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.view.DetailActualView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailActualPresenter extends MvpPresenter<DetailActualView> {
    private DetailActualInteractorImpl detailInteractorImpl = new DetailActualInteractorImpl();
    private Disposable disposableGetApiArticle, disposableSaveNewsToFavorites;
    private Router router;
    private ApiArticle apiArticle;
    private String pathToImage;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        disposableGetApiArticle = detailInteractorImpl.getApiArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiArticle -> {
                    this.apiArticle = apiArticle;
                    getViewState().setTextAppbarTitle(apiArticle.getSource().getName());
                    getViewState().setTextAppbarSubtitle(apiArticle.getUrl());
                    getViewState().setTextTvDate(apiArticle.getPublishedAt());
                    String author = apiArticle.getAuthor();
                    author = " \u2022" + author;
                    getViewState().setTextTvTime(String.format("%s%s •%s",
                            apiArticle.getSource().getName(), author,
                            Utils.DateToTimeFormat(apiArticle.getPublishedAt())));
                    getViewState().setImage(apiArticle.getUrlToImage(), apiArticle.getSource().getName());
                    getViewState().setUrlToWebView(apiArticle.getUrl());
                    getViewState().setTextTvTitle(apiArticle.getTitle());
                });
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void onBtnShareClicked() {
        router.shareNews(apiArticle.getSource().getName(), apiArticle.getTitle(), apiArticle.getUrl());
    }

    public void onBtnOpenInBrowserClicked() {
        router.openNewsInBrowser(apiArticle.getUrl());
    }

    public String saveImage(Bitmap image, String imageName) {
        return detailInteractorImpl.saveImage(image, imageName);
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public void onBtnAddToFavoriteClicked() {
        disposableSaveNewsToFavorites = detailInteractorImpl.saveNewsToFavorites(apiArticle.getTitle(),
                apiArticle.getSource().getName(), apiArticle.getUrl(), apiArticle.getPublishedAt(),
                apiArticle.getAuthor(), apiArticle.getDescription(), pathToImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getViewState().btnAddToFavoriteSetColorYellow();
                    getViewState().showSuccessMassage("News added to favorites");
                }, throwable -> getViewState().showErrorMassage(throwable.getMessage()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableGetApiArticle != null && disposableGetApiArticle.isDisposed())
            disposableGetApiArticle.dispose();
        if (disposableSaveNewsToFavorites != null && disposableSaveNewsToFavorites.isDisposed())
            disposableSaveNewsToFavorites.dispose();
    }
}
