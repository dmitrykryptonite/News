package com.example.news.navigation;

import android.app.Activity;
import android.content.Intent;

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
}
