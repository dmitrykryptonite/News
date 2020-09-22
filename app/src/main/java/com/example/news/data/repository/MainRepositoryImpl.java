package com.example.news.data.repository;

import com.example.news.data.api.ApiClient;
import com.example.news.data.utils.Utils;
import com.example.news.domain.MainRepository;
import com.example.news.entities.data.ApiNews;

import io.reactivex.Single;

public class MainRepositoryImpl implements MainRepository {
    private ApiClient apiClient;
    private static MainRepositoryImpl instance;

    public static MainRepositoryImpl getInstance() {
        if (instance == null)
            instance = new MainRepositoryImpl();
        return instance;
    }

    public MainRepositoryImpl() {
        apiClient = ApiClient.getInstance();
    }

    @Override
    public Single<ApiNews> getNews(String apiKey) {
        return apiClient.getApiInterface().getNews(Utils.getCountry(), Utils.getLanguage(), apiKey);
    }

    @Override
    public Single<ApiNews> getNewsSearch(String keyword, String apiKey) {
        return apiClient.getApiInterface().getNewsSearch(keyword, Utils.getLanguage(), "publishedAt", apiKey);
    }
}
