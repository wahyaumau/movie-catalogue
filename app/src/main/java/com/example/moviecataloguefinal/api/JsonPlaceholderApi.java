package com.example.moviecataloguefinal.api;

import com.example.moviecataloguefinal.model.MovieResponse;
import com.example.moviecataloguefinal.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceholderApi {
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);

    @GET("tv/popular")
    Call<TvShowResponse> getUpcomingTvShows(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("search/tv")
    Call<TvShowResponse> searchTvShow(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query);
}
