package com.example.news.data.repository;

import android.graphics.Bitmap;

import com.example.news.data.FileManager;
import com.example.news.data.SharedPreferencesManager;
import com.example.news.data.api.ApiClient;
import com.example.news.data.database.DatabaseNewsManager;
import com.example.news.data.utils.Utils;
import com.example.news.domain.MainRepository;
import com.example.news.entities.FavoriteArticle;
import com.example.news.entities.data.ApiArticle;
import com.example.news.entities.data.ApiNews;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MainRepositoryImpl implements MainRepository {
    private ApiClient apiClient = ApiClient.getInstance();
    private SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance();
    private static MainRepositoryImpl instance;
    private FileManager fileManager = new FileManager();
    private DatabaseNewsManager databaseNewsManager = DatabaseNewsManager.getInstance();
    public Observable<List<FavoriteArticle>> favoriteArticlesUpdateListener =
            databaseNewsManager.favoriteArticlesUpdateListener;

    public static MainRepositoryImpl getInstance() {
        if (instance == null)
            instance = new MainRepositoryImpl();
        return instance;
    }

    @Override
    public Single<ApiNews> getNews(String apiKey) {
        return apiClient.getApiInterface().getNews(Utils.getCountry(), Utils.getLanguage(), apiKey);
    }

    @Override
    public Single<ApiNews> getNewsSearch(String keyword, String apiKey) {
        return apiClient.getApiInterface().getNewsSearch(keyword, Utils.getLanguage(), "publishedAt", apiKey);
    }

    @Override
    public void saveApiArticle(ApiArticle apiArticle) {
        sharedPreferencesManager.saveApiArticle(apiArticle);
    }

    @Override
    public Single<ApiArticle> getApiArticle() {
        return sharedPreferencesManager.getApiArticle();
    }

    @Override
    public String saveImage(Bitmap image, String imageName) {
        return fileManager.saveImage(image, imageName);
    }

    @Override
    public Completable saveNewsToFavorites(String title, String appbarTitle, String appbarSubtitle,
                                           String date, String author, String description, String pathToImage) {
        return databaseNewsManager.saveNewsToFavorites(title, appbarTitle, appbarSubtitle, date, author,
                description, pathToImage);
    }

    @Override
    public void getListNews() {
        databaseNewsManager.getListNews();
    }

    @Override
    public Completable deleteNewsFromFavorites(int id) {
        return databaseNewsManager.deleteNewsFromFavorites(id);
    }
}
