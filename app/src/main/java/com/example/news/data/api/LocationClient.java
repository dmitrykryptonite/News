package com.example.news.data.api;

import com.example.news.app.App;
import com.example.news.data.api.services.LocationInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationClient {
    private static final String BASE_URL = "http://ip-api.com/";
    private static LocationClient instance;
    private static Retrofit retrofit;

    public static LocationClient getInstance() {
        if (instance == null)
            instance = new LocationClient();
        return instance;
    }

    private static Retrofit getLocationClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new NetworkConnectionInterceptor(App.getApp()));
            builder.addInterceptor(interceptor);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public LocationInterface getLocationInterface() {
        return getLocationClient().create(LocationInterface.class);
    }
}
