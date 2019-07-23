package com.example.moviecataloguefinal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.api.JsonPlaceholderApi;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.model.TvShowResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();

    public LiveData<ArrayList<TvShow>> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(String query) {
        JsonPlaceholderApi jsonPlaceholderApi = ApiClient.getClient().create(JsonPlaceholderApi.class);
        Call<TvShowResponse> call;
        call = (query.isEmpty()) ? jsonPlaceholderApi.getUpcomingTvShows(ApiClient.API_KEY, ApiClient.getLanguageKey()) :
                jsonPlaceholderApi.searchTvShow(ApiClient.API_KEY, ApiClient.getLanguageKey(), query);

        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                TvShowResponse tvShowResponse = response.body();
                assert tvShowResponse != null;
                listTvShow.postValue(tvShowResponse.getTvShows());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }
}
