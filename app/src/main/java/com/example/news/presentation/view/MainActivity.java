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
import com.example.news.presentation.presenter.MainPresenter;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter presenter;
    private RecyclerView rvActualNews;
    private SwipeRefreshLayout swipeRefresherLayout;
    private ImageButton btnSearch, btnCloseSearchView, btnFavoriteNews;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvActualNews = findViewById(R.id.rvActualNews);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvActualNews.setLayoutManager(llm);
        rvActualNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.onScrollStateChanged();
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
                presenter.onImeActionSearchClicked();
            }
            return false;
        });
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
