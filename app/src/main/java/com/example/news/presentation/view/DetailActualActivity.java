package com.example.news.presentation.view;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.news.R;
import com.example.news.app.App;
import com.example.news.navigation.Router;
import com.example.news.presentation.presenter.DetailActualPresenter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class DetailActualActivity extends MvpAppCompatActivity implements DetailActualView,
        AppBarLayout.OnOffsetChangedListener {
    @InjectPresenter
    DetailActualPresenter presenter;
    private boolean isHideToolbarView = false;
    private FrameLayout dateBehavior;
    private TextView appbarTitle, appbarSubtitle, tvDate, tvTime, tvTitle;
    private ImageView imgBackdrop;
    private ImageView imgAddToFavorite;
    private WebView webView;

    public DetailActualActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dateBehavior = findViewById(R.id.dateBehavior);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        CollapsingToolbarLayout collapsingToolbarLayout =
                findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(this);
        appbarTitle = findViewById(R.id.appbarTitle);
        appbarSubtitle = findViewById(R.id.appbarSubtitle);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        tvTitle = findViewById(R.id.tvTitle);
        imgBackdrop = findViewById(R.id.imgBackdrop);
        webView = findViewById(R.id.webView);
        Router router = new Router(this);
        presenter.setRouter(router);
        ImageView imgOpenInBrowser = findViewById(R.id.imgOpenInBrowser);
        imgOpenInBrowser.setOnClickListener(v -> presenter.onBtnOpenInBrowserClicked());
        ImageView imgShare = findViewById(R.id.imgShare);
        imgShare.setOnClickListener(v -> presenter.onBtnShareClicked());
        imgAddToFavorite = findViewById(R.id.imgAddToFavorite);
        imgAddToFavorite.setOnClickListener(v -> presenter.onBtnAddToFavoriteClicked());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(i) / (float) maxScroll;
        if (percentage == 1f && isHideToolbarView) {
            dateBehavior.setVisibility(View.GONE);
            appbarTitle.setVisibility(View.VISIBLE);
            appbarSubtitle.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
        } else if (percentage < 1f && isHideToolbarView) {
            dateBehavior.setVisibility(View.VISIBLE);
            appbarTitle.setVisibility(View.GONE);
            appbarSubtitle.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void setTextAppbarTitle(String strAppbarTitle) {
        appbarTitle.setText(strAppbarTitle);
    }

    @Override
    public void setTextAppbarSubtitle(String strAppbarSubtitle) {
        appbarSubtitle.setText(strAppbarSubtitle);
    }

    @Override
    public void setTextTvDate(String date) {
        tvDate.setText(date);
    }

    @Override
    public void setTextTvTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void setTextTvTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setImage(String urlToImage, String imageName) {
        Glide.with(this)
                .asBitmap()
                .load(urlToImage)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        imgBackdrop.setImageBitmap(resource);
                        presenter.saveImage(resource, imageName);
                        presenter.setPathToImage(presenter.saveImage(resource, imageName));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void setUrlToWebView(String url) {
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void btnAddToFavoriteSetColorYellow() {
        ImageViewCompat.setImageTintList(imgAddToFavorite,
                ColorStateList.valueOf(Color.parseColor("#FFF100")));
    }

    @Override
    public void btnAddToFavoriteSetColorWhite() {
        ImageViewCompat.setImageTintList(imgAddToFavorite,
                ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
    }

    @Override
    public void showSuccessMassage(String massage) {
        Toast.makeText(App.getApp(), massage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMassage(String massage) {
        Toast.makeText(App.getApp(), massage, Toast.LENGTH_SHORT).show();
    }
}
