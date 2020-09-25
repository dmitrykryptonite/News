package com.example.news.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.news.presentation.view.DetailActivity;

public class Router {
    private Activity activity;

    public Router(Activity activity) {
        this.activity = activity;
    }

    public void openDetailScreen() {
        Intent intent = new Intent(activity, DetailActivity.class);
        activity.startActivity(intent);
    }

    public void openNewsInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }
}
