package com.example.moviecataloguefinal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.repository.TvShowRepository;

import java.util.List;

public class FavoriteTvShowViewModel extends AndroidViewModel {
    private TvShowRepository tvShowRepository;
    private LiveData<List<TvShow>> allTvShow;

    public FavoriteTvShowViewModel(@NonNull Application application) {
        super(application);
        tvShowRepository = new TvShowRepository(application);
        allTvShow = tvShowRepository.getLiveDataAllTvShow();
    }

    public void insertTvShow(TvShow tvShow) {
        tvShowRepository.insertTvShow(tvShow);
    }

    public void updateTvShow(TvShow tvShow) {
        tvShowRepository.updateTvShow(tvShow);
    }

    public void deleteTvShow(TvShow tvShow) {
        tvShowRepository.deleteTvShow(tvShow);
    }

    public void deleteAllTvShow() {
        tvShowRepository.deleteAllTvShow();
    }

    public LiveData<List<TvShow>> getAllTvShow() {
        return allTvShow;
    }

    public TvShow getTvShowById(long id) {
        return tvShowRepository.getTvShowById(id);
    }

    public void insertListTvShow(List<TvShow> listTvShow) {
        tvShowRepository.insertListTvShow(listTvShow);
    }
}
