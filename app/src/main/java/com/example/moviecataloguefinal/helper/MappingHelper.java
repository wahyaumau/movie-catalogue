package com.example.moviecataloguefinal.helper;

import android.database.Cursor;

import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.TvShow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MappingHelper {
    public static Movie mapCursorToMovie(Cursor cursor) {
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(Movie.COLUMN_ID));
        long voteCount = cursor.getLong(cursor.getColumnIndexOrThrow(Movie.COLUMN_VOTE_COUNT));
        boolean video = false;
        int videoInt = cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_VIDEO));
        if (videoInt == 1) {
            video = true;
        }
        double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.COLUMN_VOTE_AVERAGE));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TITLE));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.COLUMN_POPULARITY));
        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_POSTER_PATH));
        String originalLanguage = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_ORIGINAL_LANGUAGE));
        String originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_ORIGINAL_TITLE));
        String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_BACKDROP_PATH));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_OVERVIEW));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RELEASE_DATE));
        Date date = new Date(Long.valueOf(releaseDate));
        return new Movie(id, voteCount, video, voteAverage, title, popularity, posterPath, originalLanguage,
                originalTitle, new ArrayList<String>(), backdropPath, true, overview, date);
    }

    public static List<Movie> mapCursorToListMovie(Cursor cursor) {
        List<Movie> listMovie = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(Movie.COLUMN_ID));
            long voteCount = cursor.getLong(cursor.getColumnIndexOrThrow(Movie.COLUMN_VOTE_COUNT));
            boolean video = false;
            int videoInt = cursor.getInt(cursor.getColumnIndexOrThrow(Movie.COLUMN_VIDEO));
            if (videoInt == 1) {
                video = true;
            }
            double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.COLUMN_VOTE_AVERAGE));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_TITLE));
            double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(Movie.COLUMN_POPULARITY));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_POSTER_PATH));
            String originalLanguage = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_ORIGINAL_LANGUAGE));
            String originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_ORIGINAL_TITLE));
            String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_BACKDROP_PATH));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_OVERVIEW));
            String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(Movie.COLUMN_RELEASE_DATE));
            Date date = new Date(Long.valueOf(releaseDate));
            Movie movie = new Movie(id, voteCount, video, voteAverage, title, popularity, posterPath, originalLanguage,
                    originalTitle, new ArrayList<String>(), backdropPath, true, overview, date);
            listMovie.add(movie);
        }
        return listMovie;
    }

    public static TvShow mapCursorToTvShow(Cursor cursor) {
        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ID));
        String originalName = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ORIGINAL_NAME));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_NAME));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(TvShow.COLUMN_POPULARITY));
        long voteCount = cursor.getLong(cursor.getColumnIndexOrThrow(TvShow.COLUMN_VOTE_COUNT));
        String firstAirDateString = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_FIRST_AIR_DATE));
        Date firstAirDate = new Date(Long.valueOf(firstAirDateString));
        String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_BACKDROP_PATH));
        String originalLanguage = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ORIGINAL_LANGUAGE));
        double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(TvShow.COLUMN_VOTE_AVERAGE));
        String overview = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_OVERVIEW));
        String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_POSTER_PATH));

        return new TvShow(id, originalName, new ArrayList<String>(), name, popularity, new ArrayList<String>()
                , voteCount, firstAirDate, backdropPath, originalLanguage, voteAverage, overview, posterPath);
    }

    public static List<TvShow> mapCursorToListTvShow(Cursor cursor) {
        List<TvShow> listTvShow = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ID));
            String originalName = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ORIGINAL_NAME));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_NAME));
            double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow(TvShow.COLUMN_POPULARITY));
            long voteCount = cursor.getLong(cursor.getColumnIndexOrThrow(TvShow.COLUMN_VOTE_COUNT));
            String firstAirDateString = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_FIRST_AIR_DATE));
            Date firstAirDate = new Date(Long.valueOf(firstAirDateString));
            String backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_BACKDROP_PATH));
            String originalLanguage = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_ORIGINAL_LANGUAGE));
            double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(TvShow.COLUMN_VOTE_AVERAGE));
            String overview = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_OVERVIEW));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow(TvShow.COLUMN_POSTER_PATH));

            TvShow tvShow = new TvShow(id, originalName, new ArrayList<String>(), name, popularity, new ArrayList<String>()
                    , voteCount, firstAirDate, backdropPath, originalLanguage, voteAverage, overview, posterPath);
            listTvShow.add(tvShow);

        }
        return listTvShow;
    }

}
