package com.example.news.data.api.services;

import com.example.news.entities.data.Location;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface LocationInterface {

    @GET("json")
    Single<Location> getLocation();
}
