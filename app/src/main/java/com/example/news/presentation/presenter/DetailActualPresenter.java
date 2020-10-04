package com.example.news.presentation.presenter;

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
            disposableDeleteNewsFromFavorites, disposableSaveImageByUrl, disposableDeleteImageByPath;
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
                    if (author != null) {
                        author = " \u2022 " + author;
                    } else {
                        author = " \u2022 " + "Unknown author";
                    }
                    getViewState().setTextTvBriefInfo(String.format("%s%s • %s",
                            apiArticle.getSource().getName(), author,
                            Utils.DateToTimeFormat(apiArticle.getPublishedAt())));
                    getViewState().setImage(apiArticle.getUrlToImage());
                    getViewState().setUrlToWebView(apiArticle.getUrl());
                    getViewState().setTextTvTitle(apiArticle.getTitle());
                    if (detailActualInteractorImpl
                            .getNewsByTitleApiArticle(apiArticle.getTitle()) != null) {
                        getViewState().btnAddToFavoriteSetColorYellow();
                    } else {
                        getViewState().btnAddToFavoriteSetColorWhite();
                    }
                });
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void onBtnShareClicked() {
        router.shareNews(apiArticle.getSource().getName(),
                apiArticle.getTitle(), apiArticle.getUrl());
    }

    public void onBtnOpenInBrowserClicked() {
        router.openNewsInBrowser(apiArticle.getUrl());
    }

    public void onBtnAddToFavoriteClicked() {
        if (detailActualInteractorImpl.getNewsByTitleApiArticle(apiArticle.getTitle()) == null) {
            disposableSaveImageByUrl = detailActualInteractorImpl.saveImageByUrl(
                    apiArticle.getUrlToImage(), apiArticle.getSource().getName())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(pathToImage -> {
                        this.pathToImage = pathToImage;
                        disposableSaveNewsToFavorites = detailActualInteractorImpl
                                .saveNewsToFavorites(apiArticle.getTitle(),
                                        apiArticle.getSource().getName(),
                                        apiArticle.getUrl(),
                                        apiArticle.getPublishedAt(), apiArticle.getAuthor(),
                                        apiArticle.getDescription(), pathToImage,
                                        apiArticle.getUrl())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    getViewState().btnAddToFavoriteSetColorYellow();
                                    getViewState().showSuccessMassage("News added to favorites");
                                }, throwable -> getViewState()
                                        .showErrorMassage(throwable.getMessage()));
                    });
        } else {
            disposableDeleteImageByPath = detailActualInteractorImpl.deleteImageByPath(pathToImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> disposableDeleteNewsFromFavorites = detailActualInteractorImpl
                                    .deleteNewsFromFavorites(apiArticle.getTitle())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        getViewState().btnAddToFavoriteSetColorWhite();
                                        getViewState()
                                                .showSuccessMassage("News deleted from favorites");
                                    }, throwable -> getViewState()
                                            .showErrorMassage(throwable.getMessage())),
                            throwable -> {
                                disposableDeleteNewsFromFavorites = detailActualInteractorImpl
                                        .deleteNewsFromFavorites(apiArticle.getTitle())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            getViewState().btnAddToFavoriteSetColorWhite();
                                            getViewState()
                                                    .showSuccessMassage("News deleted from favorites");
                                        }, mThrowable -> getViewState()
                                                .showErrorMassage(mThrowable.getMessage()));
                                getViewState().showErrorMassage(throwable.getMessage());
                            });
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
        if (disposableSaveImageByUrl != null && disposableSaveImageByUrl.isDisposed())
            disposableSaveImageByUrl.dispose();
        if (disposableDeleteImageByPath != null && disposableDeleteImageByPath.isDisposed())
            disposableDeleteImageByPath.dispose();
    }
}
