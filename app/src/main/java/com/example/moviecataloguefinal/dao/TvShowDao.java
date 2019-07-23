package com.example.moviecataloguefinal.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.example.moviecataloguefinal.model.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert
    long insertTvShow(TvShow tvShow);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListTvShow(List<TvShow> listTvShow);

    @Query("SELECT * FROM tv_shows ")
    LiveData<List<TvShow>> getLiveDataAllTvShow();

    @Query("SELECT * FROM tv_shows")
    List<TvShow> getListAllTvShow();

    @Query("SELECT * FROM tv_shows")
    Cursor getCursorAllTvShow();

    @Query("SELECT * FROM tv_shows WHERE _id=:id")
    Cursor getCursorTvShowById(long id);

    @Update
    int updateTvShow(TvShow tvShow);

    @Delete
    int deleteTvShow(TvShow tvShow);

    @Query("DELETE FROM tv_shows")
    int deleteAllTvShow();

    @Query("SELECT COUNT(*) FROM tv_shows")
    int countAllTvShow();
}
