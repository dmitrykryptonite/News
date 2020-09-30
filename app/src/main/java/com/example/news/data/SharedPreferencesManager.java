package com.example.news.data;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.news.app.App;
import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;
import com.google.gson.Gson;

import io.reactivex.Single;

public class SharedPreferencesManager {
    private static SharedPreferencesManager instance;

    public static SharedPreferencesManager getInstance() {
        if (instance == null)
            instance = new SharedPreferencesManager();
        return instance;
    }

    private SharedPreferences preferences;
    private final String SAVED_API_ARTICLE = "saved_api_article";
    private final String SAVED_FAVORITE_ARTICLE = "saved_favorite_article";

    public void saveApiArticle(ApiArticle apiArticle) {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getApp());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String mApiArticle = gson.toJson(apiArticle);
        editor.putString(SAVED_API_ARTICLE, mApiArticle);
        editor.apply();
    }

    public Single<ApiArticle> getApiArticle() {
        return Single.create(emitter -> {
            preferences = PreferenceManager.getDefaultSharedPreferences(App.getApp());
            String savedApiArticle = preferences.getString(SAVED_API_ARTICLE, "");
            Gson gson = new Gson();
            ApiArticle apiArticle = gson.fromJson(savedApiArticle, ApiArticle.class);
            emitter.onSuccess(apiArticle);
        });
    }

    public void saveFavoriteArticle(FavoriteArticle favoriteArticle) {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getApp());
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String mFavoriteArticle = gson.toJson(favoriteArticle);
        editor.putString(SAVED_FAVORITE_ARTICLE, mFavoriteArticle);
        editor.apply();
    }

    public Single<FavoriteArticle> getFavoriteArticle() {
        return Single.create(emitter -> {
            preferences = PreferenceManager.getDefaultSharedPreferences(App.getApp());
            String savedFavoriteArticle = preferences.getString(SAVED_FAVORITE_ARTICLE, "");
            Gson gson = new Gson();
            FavoriteArticle favoriteArticle = gson.fromJson(savedFavoriteArticle, FavoriteArticle.class);
            emitter.onSuccess(favoriteArticle);
        });
    }
}
