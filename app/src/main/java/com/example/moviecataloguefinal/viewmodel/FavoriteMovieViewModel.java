package com.example.moviecataloguefinal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.repository.MovieRepository;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> allMovie;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allMovie = movieRepository.getLiveDataAllMovie();
    }

    public void insertMovie(Movie movie) {
        movieRepository.insertMovie(movie);
    }

    public void updateMovie(Movie movie) {
        movieRepository.updateMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.deleteMovie(movie);
    }

    public void deleteAllMovie() {
        movieRepository.deleteAllMovie();
    }

    public Movie getMovieById(long id) {
        return movieRepository.getMovieById(id);
    }

    public LiveData<List<Movie>> getAllMovie() {
        return allMovie;
    }

    public List<Movie> getListMovie() {
        return movieRepository.getListAllMovie();
    }

    public void insertListMovie(List<Movie> listMovie) {
        movieRepository.insertListMovie(listMovie);
    }
}
