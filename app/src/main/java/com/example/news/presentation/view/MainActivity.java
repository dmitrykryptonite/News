package com.example.news.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.news.R;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvActualNews;
    private SwipeRefreshLayout swipeRefresherLayout;
    private ImageButton btnSearch, btnCloseSearchView, btnFavoriteNews;
    private TextView tvQuery;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvActualNews = findViewById(R.id.rvActualNews);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvActualNews.setLayoutManager(llm);
        swipeRefresherLayout = findViewById(R.id.swipeRefresherLayout);
        swipeRefresherLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        btnSearch = findViewById(R.id.btnSearch);
        btnCloseSearchView = findViewById(R.id.btnCloseSearchView);
        btnFavoriteNews = findViewById(R.id.btnFavoriteNews);
        tvQuery = findViewById(R.id.tvQuery);
        etSearch = findViewById(R.id.etSearch);
    }
}
