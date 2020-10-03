package com.example.news.data.repository;

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
        return apiClient.getApiInterface().getNewsSearch(keyword, Utils.getLanguage(),
                "publishedAt", apiKey);
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
    public Single<String> saveImageByUrl(String url, String imageName) {
        return fileManager.saveImageByUrl(url, imageName);
    }

    @Override
    public Completable deleteImageByPath(String pathToImage) {
        return fileManager.deleteImageByPath(pathToImage);
    }

    @Override
    public FavoriteArticle getNewsByTitleApiArticle(String title) {
        return databaseNewsManager.getNewsByTitleApiArticle(title);
    }

    @Override
    public Completable saveNewsToFavorites(String title, String appbarTitle,
                                           String appbarSubtitle, String date, String author,
                                           String description, String pathToImage, String url) {
        return databaseNewsManager.saveNewsToFavorites(title, appbarTitle,
                appbarSubtitle, date, author, description, pathToImage, url);
    }

    @Override
    public Completable deleteNewsFromFavorites(String title) {
        return databaseNewsManager.deleteNewsFromFavorites(title);
    }

    @Override
    public void getListNews() {
        databaseNewsManager.getListNews();
    }

    @Override
    public void saveFavoriteArticle(FavoriteArticle favoriteArticle) {
        sharedPreferencesManager.saveFavoriteArticle(favoriteArticle);
    }

    @Override
    public Single<FavoriteArticle> getFavoriteArticle() {
        return sharedPreferencesManager.getFavoriteArticle();
    }
}
