package com.example.moviecataloguefinal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.api.JsonPlaceholderApi;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public LiveData<ArrayList<Movie>> getListMovie() {
        return listMovie;
    }

    public void setListMovie(String query) {
        JsonPlaceholderApi jsonPlaceholderApi = ApiClient.getClient().create(JsonPlaceholderApi.class);
        Call<MovieResponse> call;
        call = (query.isEmpty()) ? jsonPlaceholderApi.getUpcomingMovies(ApiClient.API_KEY, ApiClient.getLanguageKey()) :
                jsonPlaceholderApi.searchMovie(ApiClient.API_KEY, ApiClient.getLanguageKey(), query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                assert movieResponse != null;
                listMovie.postValue(movieResponse.getMovies());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }
}
