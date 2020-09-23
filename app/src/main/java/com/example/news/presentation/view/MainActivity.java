package com.example.news.presentation.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.news.R;
import com.example.news.entities.data.ApiArticle;
import com.example.news.navigation.Router;
import com.example.news.presentation.presenter.MainPresenter;
import com.example.news.presentation.ui.adapters.ListActualNewsRecyclerViewAdapter;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    public static final String API_KEY = "2fbec356e9104ecab368633bea1dd01c";
    @InjectPresenter
    MainPresenter presenter;
    private SwipeRefreshLayout swipeRefresherLayout;
    private ImageButton btnSearch, btnCloseSearchView, btnFavoriteNews;
    private EditText etSearch;
    private RecyclerView rvActualNews;
    private ListActualNewsRecyclerViewAdapter adapter;
    private int lastFirstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvActualNews = findViewById(R.id.rvActualNews);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvActualNews.setLayoutManager(llm);
        adapter = new ListActualNewsRecyclerViewAdapter(this);
        rvActualNews.setAdapter(adapter);
        rvActualNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.onScrollStateChanged(etSearch.getText().toString());
            }
        });
        swipeRefresherLayout = findViewById(R.id.swipeRefresherLayout);
        swipeRefresherLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> presenter.onBtnSearchClicked());
        btnCloseSearchView = findViewById(R.id.btnCloseSearchView);
        btnCloseSearchView.setOnClickListener(v -> presenter.onBtnCloseSearchViewClicked());
        btnFavoriteNews = findViewById(R.id.btnFavoriteNews);
        etSearch = findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = etSearch.getText().toString();
                presenter.getNewsSearch(query, API_KEY);
                presenter.onImeActionSearchClicked();
                presenter.searchHistoryAddItem(query);
                swipeRefresherLayout.setOnRefreshListener(() ->
                        presenter.getNewsSearch(query, API_KEY));
            }
            return false;
        });
        presenter.getNews(API_KEY);
        swipeRefresherLayout.setOnRefreshListener(() -> presenter.getNews(API_KEY));
        Router router = new Router(this);
        presenter.setRouter(router);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(API_KEY);
    }

    @Override
    public void showPanel(boolean isSearchingMode) {
        if (isSearchingMode) {
            btnSearch.setVisibility(View.GONE);
            etSearch.setVisibility(View.VISIBLE);
            btnCloseSearchView.setVisibility(View.VISIBLE);
        } else {
            etSearch.setVisibility(View.GONE);
            btnCloseSearchView.setVisibility(View.GONE);
            btnSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTextEditTextSearch(String text) {
        etSearch.setText(text);
    }

    @Override
    public void hideKeyboard() {
        final Activity activity = MainActivity.this;
        final View view = activity.getWindow().getDecorView();
        final InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        assert (imm != null);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void editTextSearchRequestFocus() {
        etSearch.requestFocus();
    }

    @Override
    public void showKeyboardForEditTextSearch() {
        final Activity activity = MainActivity.this;
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void editTextSearchClearFocus() {
        etSearch.clearFocus();
    }

    @Override
    public void updateActualNewsList(List<ApiArticle> apiArticles) {
        adapter.updateActualNewsList(apiArticles);
    }

    @Override
    public void stopRefreshing() {
        swipeRefresherLayout.setRefreshing(false);
    }

    @Override
    public void recyclerViewMovingScrollToStartPosition() {
        rvActualNews.smoothScrollToPosition(0);
    }

    @Override
    public void recyclerViewSavedScrollPosition() {
        assert rvActualNews.getLayoutManager() != null;
        lastFirstVisiblePosition = ((LinearLayoutManager) rvActualNews.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void recyclerViewRestoreScrollPosition() {
        assert rvActualNews.getLayoutManager() != null;
        rvActualNews.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
    }

    @Override
    public void editTextSearchSetText(String query) {
        etSearch.setText(query);
    }

    @Override
    public void openDetailScreen() {
        presenter.openDetailScreen();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String query = etSearch.getText().toString();
        presenter.onPauseView(query);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumeView(API_KEY);
    }
}
