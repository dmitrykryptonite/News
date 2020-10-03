package com.example.news.presentation.presenter;

import com.example.news.data.utils.Utils;
import com.example.news.domain.DetailFavoriteInteractorImpl;
import com.example.news.entities.FavoriteArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.view.DetailFavoriteView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailFavoritePresenter extends MvpPresenter<DetailFavoriteView> {
    private DetailFavoriteInteractorImpl detailFavoriteInteractorImpl =
            new DetailFavoriteInteractorImpl();
    private Disposable disposableGetFavoriteArticle, disposableSaveNewsToFavorites,
            disposableDeleteNewsFromFavorites, disposableDeleteImageByPath;
    private Router router;
    private FavoriteArticle favoriteArticle;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        disposableGetFavoriteArticle = detailFavoriteInteractorImpl.getFavoriteArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteArticle -> {
                    this.favoriteArticle = favoriteArticle;
                    getViewState().setTextAppbarTitle(favoriteArticle.getAppbarTitle());
                    getViewState().setTextAppbarSubtitle(favoriteArticle.getAppbarSubtitle());
                    getViewState().setTextTvDate(Utils.DateFormat(favoriteArticle.getDate()));
                    String author = favoriteArticle.getAuthor();
                    author = " \u2022" + author;
                    getViewState().setTextTvTime(String.format("%s%s •%s",
                            favoriteArticle.getAppbarTitle(), author,
                            Utils.DateToTimeFormat(favoriteArticle.getDate())));
                    getViewState().setImage(favoriteArticle.getPathToImage());
                    getViewState().setUrlToWebView(favoriteArticle.getUrl());
                    getViewState().setTextTvTitle(favoriteArticle.getTitle());
                });
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void onBtnShareClicked() {
        router.shareNews(favoriteArticle.getAppbarTitle(), favoriteArticle.getTitle(),
                favoriteArticle.getUrl());
    }

    public void onBtnOpenInBrowserClicked() {
        router.openNewsInBrowser(favoriteArticle.getUrl());
    }


    public void onLoadImageFailedWithPermissionDenied() {
        getViewState().showErrorPanelFromImageIfPermissionDenied(
                "Permissions denied to save pictures");
    }

    public void onLoadImageFailedWithPermissionGranted() {
        getViewState().showErrorPanelFromImageIfPermissionGranted("Image not found");
    }

    public void onResourceReady() {
        getViewState().hideErrorPanel();
    }

    public void onBtnTapToChangePermissionClicked() {
        router.openSettingsApp();
    }

    public void onBtnAddToFavoriteClicked() {
        if (detailFavoriteInteractorImpl.getNewsByTitleApiArticle(favoriteArticle.getTitle()) == null) {
            disposableSaveNewsToFavorites = detailFavoriteInteractorImpl.saveNewsToFavorites(
                    favoriteArticle.getTitle(), favoriteArticle.getAppbarTitle(),
                    favoriteArticle.getAppbarSubtitle(), favoriteArticle.getDate(),
                    favoriteArticle.getAuthor(), favoriteArticle.getDescription(),
                    favoriteArticle.getPathToImage(), favoriteArticle.getUrl())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        getViewState().btnAddToFavoriteSetColorYellow();
                        getViewState().showSuccessMassage("News added to favorites");
                    }, throwable -> getViewState().showErrorMassage(throwable.getMessage()));
        } else {
            disposableDeleteImageByPath = detailFavoriteInteractorImpl
                    .deleteImageByPath(favoriteArticle.getPathToImage())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> disposableDeleteNewsFromFavorites = detailFavoriteInteractorImpl
                                    .deleteNewsFromFavorites(favoriteArticle.getTitle())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        getViewState().btnAddToFavoriteSetColorWhite();
                                        getViewState()
                                                .showSuccessMassage("News deleted from favorites");
                                    }, throwable -> getViewState()
                                            .showErrorMassage(throwable.getMessage())),
                            throwable -> {
                                getViewState().showErrorMassage(throwable.getMessage());
                                disposableDeleteNewsFromFavorites = detailFavoriteInteractorImpl
                                        .deleteNewsFromFavorites(favoriteArticle.getTitle())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            getViewState().btnAddToFavoriteSetColorWhite();
                                            getViewState()
                                                    .showSuccessMassage("News deleted from favorites");
                                        }, mThrowable -> getViewState()
                                                .showErrorMassage(mThrowable.getMessage()));
                            });
        }
    }

    public void onBackPressed() {
        getViewState().finishActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableGetFavoriteArticle != null && disposableGetFavoriteArticle.isDisposed())
            disposableGetFavoriteArticle.dispose();
        if (disposableSaveNewsToFavorites != null && disposableSaveNewsToFavorites.isDisposed())
            disposableSaveNewsToFavorites.dispose();
        if (disposableDeleteNewsFromFavorites != null && disposableDeleteNewsFromFavorites.isDisposed())
            disposableDeleteNewsFromFavorites.dispose();
        if (disposableDeleteImageByPath != null && disposableDeleteImageByPath.isDisposed())
            disposableDeleteImageByPath.dispose();
    }
}
