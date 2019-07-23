package com.example.moviecataloguefinal.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.moviecataloguefinal.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListMovie(List<Movie> listMovie);

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getLiveDataAllMovie();

    @Query("SELECT * FROM movies")
    List<Movie> getListAllMovie();

    @Query("SELECT * FROM movies")
    Cursor getCursorAllMovie();

    @Query("SELECT * FROM movies WHERE _id=:id")
    Cursor getCursorMovieById(long id);

    @Update
    int updateMovie(Movie movie);

    @Delete
    int deleteMovie(Movie movie);

    @Query("DELETE FROM movies")
    int deleteAllMovie();

    @Query("SELECT COUNT(*) FROM movies")
    int countAllMovie();
}