package com.example.android.popularmovies.data.remote;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.data.model.TMDBResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mika on 2018-04-16.
 */

public interface TMDBService {
    String API_KEY = BuildConfig.THEMOVIEDB_API_KEY;

    @GET("movie/popular?api_key=" + API_KEY)
    Call<TMDBResponse> getPopular();

    @GET("/movie/top_rated?api_key=" + API_KEY)
    Call<TMDBResponse> getTopRated();
}