package com.example.news.data;

import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.example.news.app.App;
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
            String saved_apiArticle = preferences.getString(SAVED_API_ARTICLE, "");
            Gson gson = new Gson();
            ApiArticle apiArticle = gson.fromJson(saved_apiArticle, ApiArticle.class);
            emitter.onSuccess(apiArticle);
        });
    }
}
