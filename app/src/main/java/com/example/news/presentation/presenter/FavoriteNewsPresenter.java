package com.example.news.presentation.presenter;

import com.example.news.domain.FavoriteNewsInteractorImpl;
import com.example.news.entities.FavoriteArticle;
import com.example.news.navigation.Router;
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
    private Router router;

    public FavoriteNewsPresenter() {
        disposableFavoriteArticlesUpdateListener = favoriteInteractorImpl.favoriteArticlesUpdateListener
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteArticles ->
                                getViewState().updateFavoriteNewsList(favoriteArticles),
                        throwable -> getViewState().showSuccessMassage(throwable.getMessage()));
    }

    @Override
    public void attachView(FavoriteNewsView view) {
        super.attachView(view);
        favoriteInteractorImpl.getListNews();
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void openDetailFavoriteScreen(FavoriteArticle favoriteArticle) {
        favoriteInteractorImpl.saveFavoriteArticle(favoriteArticle);
        router.openDetailFavoriteScreen();
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
