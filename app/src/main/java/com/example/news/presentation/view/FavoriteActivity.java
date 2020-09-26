package com.example.news.presentation.view;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.news.R;
import com.example.news.presentation.presenter.FavoritePresenter;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class FavoriteActivity extends MvpAppCompatActivity implements FavoriteView {
    @InjectPresenter
    FavoritePresenter presenter;

    private RecyclerView rvFavoriteNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rvFavoriteNews = findViewById(R.id.rvFavoriteNews);
    }
}
