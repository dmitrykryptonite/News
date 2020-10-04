package com.example.news.presentation.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.news.R;
import com.example.news.app.App;
import com.example.news.navigation.Router;
import com.example.news.presentation.presenter.DetailFavoritePresenter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class DetailFavoriteActivity extends MvpAppCompatActivity implements DetailFavoriteView,
        AppBarLayout.OnOffsetChangedListener {
    @InjectPresenter
    DetailFavoritePresenter presenter;
    private boolean isHideToolbarView = false;
    private FrameLayout dateBehavior;
    private TextView appbarTitle, appbarSubtitle, tvDate, tvBriefInfo, tvTitle, tvPermissionDenied,
            tvNotFound;
    private ImageView imgBackdrop, imgAddToFavorite, imgPermissionDenied, imgNotFound;
    private WebView webView;
    private View lineFromTvTapToChangePermission;
    private Button btnTapToChangePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);
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
        tvBriefInfo = findViewById(R.id.tvBriefInfo);
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
        imgPermissionDenied = findViewById(R.id.imgPermissionDenied);
        tvPermissionDenied = findViewById(R.id.tvPermissionDenied);
        btnTapToChangePermission = findViewById(R.id.btnTapToChangePermission);
        btnTapToChangePermission.setOnClickListener(v ->
                presenter.onBtnTapToChangePermissionClicked());
        lineFromTvTapToChangePermission = findViewById(R.id.lineFromTvTapToChangePermission);
        imgNotFound = findViewById(R.id.imgNotFound);
        tvNotFound = findViewById(R.id.tvNotFound);
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
    public void setTextTvBriefInfo(String briefInfo) {
        tvBriefInfo.setText(briefInfo);
    }

    @Override
    public void setTextTvTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void showErrorPanelFromImageIfPermissionGranted(String error) {
        imgPermissionDenied.setVisibility(View.GONE);
        tvPermissionDenied.setVisibility(View.GONE);
        btnTapToChangePermission.setVisibility(View.GONE);
        lineFromTvTapToChangePermission.setVisibility(View.GONE);
        imgNotFound.setVisibility(View.VISIBLE);
        tvNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorPanelFromImageIfPermissionDenied(String error) {
        imgNotFound.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);
        imgPermissionDenied.setVisibility(View.VISIBLE);
        tvPermissionDenied.setVisibility(View.VISIBLE);
        btnTapToChangePermission.setVisibility(View.VISIBLE);
        lineFromTvTapToChangePermission.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorPanel() {
        imgPermissionDenied.setVisibility(View.GONE);
        tvPermissionDenied.setVisibility(View.GONE);
        btnTapToChangePermission.setVisibility(View.GONE);
        lineFromTvTapToChangePermission.setVisibility(View.GONE);
        imgNotFound.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);
    }

    @Override
    public void setImage(String pathToImage) {
        Glide.with(this)
                .asBitmap()
                .load(pathToImage)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target, boolean isFirstResource) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED)
                            presenter.onLoadImageFailedWithPermissionGranted();
                        else if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED)
                            presenter.onLoadImageFailedWithPermissionDenied();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model,
                                                   Target<Bitmap> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        presenter.onResourceReady();
                        return false;
                    }
                })
                .into(imgBackdrop);
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
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }
}
