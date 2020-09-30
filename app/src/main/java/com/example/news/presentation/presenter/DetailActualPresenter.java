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
    private DetailActualInteractorImpl detailActualInteractorImpl = new DetailActualInteractorImpl();
    private Disposable disposableGetApiArticle, disposableSaveNewsToFavorites,
            disposableDeleteNewsFromFavorites;
    private Router router;
    private ApiArticle apiArticle;
    private String pathToImage;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        disposableGetApiArticle = detailActualInteractorImpl.getApiArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apiArticle -> {
                    this.apiArticle = apiArticle;
                    getViewState().setTextAppbarTitle(apiArticle.getSource().getName());
                    getViewState().setTextAppbarSubtitle(apiArticle.getUrl());
                    getViewState().setTextTvDate(Utils.DateFormat(apiArticle.getPublishedAt()));
                    String author = apiArticle.getAuthor();
                    author = " \u2022" + author;
                    getViewState().setTextTvTime(String.format("%s%s •%s",
                            apiArticle.getSource().getName(), author,
                            Utils.DateToTimeFormat(apiArticle.getPublishedAt())));
                    getViewState().setImage(apiArticle.getUrlToImage(), apiArticle.getSource().getName());
                    getViewState().setUrlToWebView(apiArticle.getUrl());
                    getViewState().setTextTvTitle(apiArticle.getTitle());
                    if (detailActualInteractorImpl.getNewsByTitleApiArticle(apiArticle.getTitle()) != null) {
                        getViewState().btnAddToFavoriteSetColorYellow();
                    } else {
                        getViewState().btnAddToFavoriteSetColorWhite();
                    }
                });
    }

    public void onLoadImageFailed() {
        getViewState().showNotFoundPanel();
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
        return detailActualInteractorImpl.saveImage(image, imageName);
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public void onBtnAddToFavoriteClicked() {
        if (detailActualInteractorImpl.getNewsByTitleApiArticle(apiArticle.getTitle()) == null) {
            disposableSaveNewsToFavorites = detailActualInteractorImpl.saveNewsToFavorites(
                    apiArticle.getTitle(), apiArticle.getSource().getName(), apiArticle.getUrl(),
                    apiArticle.getPublishedAt(), apiArticle.getAuthor(),
                    apiArticle.getDescription(), pathToImage, apiArticle.getUrl())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getViewState().btnAddToFavoriteSetColorYellow();
                        getViewState().showSuccessMassage("News added to favorites");
                    }, throwable -> getViewState().showErrorMassage(throwable.getMessage()));
        } else {
            disposableDeleteNewsFromFavorites = detailActualInteractorImpl.deleteNewsFromFavorites(
                    apiArticle.getTitle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getViewState().btnAddToFavoriteSetColorWhite();
                        getViewState().showSuccessMassage("News deleted from favorites");
                    }, throwable -> getViewState().showErrorMassage(throwable.getMessage()));
        }
    }

    public void onBackPressed() {
        getViewState().finishActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableGetApiArticle != null && disposableGetApiArticle.isDisposed())
            disposableGetApiArticle.dispose();
        if (disposableSaveNewsToFavorites != null && disposableSaveNewsToFavorites.isDisposed())
            disposableSaveNewsToFavorites.dispose();
        if (disposableDeleteNewsFromFavorites != null && disposableDeleteNewsFromFavorites.isDisposed())
            disposableDeleteNewsFromFavorites.dispose();
    }
}
