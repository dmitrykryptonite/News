package com.example.news.navigation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.news.presentation.view.DetailActualActivity;
import com.example.news.presentation.view.DetailFavoriteActivity;
import com.example.news.presentation.view.FavoriteNewsActivity;

public class Router {
    private Activity activity;

    public Router(Activity activity) {
        this.activity = activity;
    }

    public void openDetailActualScreen() {
        Intent intent = new Intent(activity, DetailActualActivity.class);
        activity.startActivity(intent);
    }

    public void openNewsInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }

    public void shareNews(String source, String title, String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plan");
        intent.putExtra(Intent.EXTRA_SUBJECT, source);
        String body = title + "\n" + url + "\n" + "Shared from the News App" + "\n";
        intent.putExtra(Intent.EXTRA_TEXT, body);
        activity.startActivity(Intent.createChooser(intent, "Share in: "));
    }

    public void openFavoriteScreen() {
        Intent intent = new Intent(activity, FavoriteNewsActivity.class);
        activity.startActivity(intent);
    }

    public void openDetailFavoriteScreen() {
        Intent intent = new Intent(activity, DetailFavoriteActivity.class);
        activity.startActivity(intent);
    }
}
