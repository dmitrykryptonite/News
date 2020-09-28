package com.example.news.presentation.presenter;

import com.example.news.domain.FavoriteNewsInteractorImpl;
import com.example.news.presentation.view.FavoriteNewsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class FavoriteNewsPresenter extends MvpPresenter<FavoriteNewsView> {
    private FavoriteNewsInteractorImpl favoriteInteractorImpl = new FavoriteNewsInteractorImpl();
    private Disposable disposableFavoriteArticlesUpdateListener;

    public FavoriteNewsPresenter() {
        disposableFavoriteArticlesUpdateListener = favoriteInteractorImpl.favoriteArticlesUpdateListener
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteArticles -> getViewState().updateFavoriteNewsList(favoriteArticles));
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        favoriteInteractorImpl.getListNews();
    }

    @Override
    public void attachView(FavoriteNewsView view) {
        super.attachView(view);
        favoriteInteractorImpl.getListNews();
    }

    public void onPauseView() {
        getViewState().recyclerViewSavedScrollPosition();
    }

    public void onResumeView() {
        getViewState().recyclerViewRestoreScrollPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableFavoriteArticlesUpdateListener != null && disposableFavoriteArticlesUpdateListener.isDisposed())
            disposableFavoriteArticlesUpdateListener.dispose();
    }
}
