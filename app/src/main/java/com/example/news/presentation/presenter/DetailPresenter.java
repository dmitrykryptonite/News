package com.example.news.presentation.presenter;

import com.example.news.data.utils.Utils;
import com.example.news.domain.DetailInteractorImpl;
import com.example.news.entities.data.ApiArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.view.DetailView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {
    private DetailInteractorImpl detailInteractorImpl = new DetailInteractorImpl();
    private Disposable disposableGetApiArticle;
    private Router router;
    private ApiArticle apiArticle;

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
                    getViewState().setImage(apiArticle.getUrlToImage());
                    getViewState().setUrlToView(apiArticle.getUrl());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableGetApiArticle != null && disposableGetApiArticle.isDisposed())
            disposableGetApiArticle.dispose();
    }
}
