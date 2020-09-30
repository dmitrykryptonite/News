package com.example.news.presentation.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.app.App;
import com.example.news.entities.FavoriteArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.presenter.FavoriteNewsPresenter;
import com.example.news.presentation.ui.adapters.ListFavoriteNewsRecyclerViewAdapter;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class FavoriteNewsActivity extends MvpAppCompatActivity implements FavoriteNewsView {
    @InjectPresenter
    FavoriteNewsPresenter presenter;

    private RecyclerView rvFavoriteNews;
    private ListFavoriteNewsRecyclerViewAdapter adapter;
    private int lastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_news);
        rvFavoriteNews = findViewById(R.id.rvFavoriteNews);
        rvFavoriteNews.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvFavoriteNews.setLayoutManager(llm);
        adapter = new ListFavoriteNewsRecyclerViewAdapter(this);
        rvFavoriteNews.setAdapter(adapter);
        Router router = new Router(this);
        presenter.setRouter(router);
    }

    @Override
    public void recyclerViewSavedScrollPosition() {
        assert rvFavoriteNews.getLayoutManager() != null;
        lastFirstVisiblePosition = ((LinearLayoutManager) rvFavoriteNews.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void recyclerViewRestoreScrollPosition() {
        assert rvFavoriteNews.getLayoutManager() != null;
        rvFavoriteNews.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
    }

    @Override
    public void updateFavoriteNewsList(List<FavoriteArticle> favoriteArticles) {
        adapter.updateFavoriteNewsList(favoriteArticles);
    }

    @Override
    public void showSuccessMassage(String massage) {
        Toast.makeText(App.getApp(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMassage(String massage) {
        Toast.makeText(App.getApp(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openDetailFavoriteScreen(FavoriteArticle favoriteArticle) {
        presenter.openDetailFavoriteScreen(favoriteArticle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumeView();
    }
}
