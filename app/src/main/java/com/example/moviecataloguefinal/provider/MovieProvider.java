package com.example.moviecataloguefinal.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.repository.MovieRepository;
import com.example.moviecataloguefinal.repository.TvShowRepository;

import java.util.Objects;

public class MovieProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.moviecataloguefinal.provider";
    private static final String SCHEME = "content";
    private MovieRepository movieRepository;
    private TvShowRepository tvShowRepository;

    public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(Movie.TABLE_NAME)
            .build();

    public static final Uri TV_SHOW_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TvShow.TABLE_NAME)
            .build();

    private static final int CODE_MOVIE_DIR = 1;
    private static final int CODE_MOVIE_ITEM = 2;
    private static final int CODE_TV_SHOW_DIR = 3;
    private static final int CODE_TV_SHOW_ITEM = 4;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME, CODE_MOVIE_DIR);
        MATCHER.addURI(AUTHORITY, Movie.TABLE_NAME + "/#", CODE_MOVIE_ITEM);
        MATCHER.addURI(AUTHORITY, TvShow.TABLE_NAME, CODE_TV_SHOW_DIR);
        MATCHER.addURI(AUTHORITY, TvShow.TABLE_NAME + "/#", CODE_TV_SHOW_ITEM);
    }


    @Override
    public boolean onCreate() {
        movieRepository = new MovieRepository(getContext());
        tvShowRepository = new TvShowRepository(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                cursor = movieRepository.getCursorAllMovie();
                break;
            case CODE_MOVIE_ITEM:
                cursor = movieRepository.getCursorMovieById(ContentUris.parseId(uri));
                break;
            case CODE_TV_SHOW_DIR:
                cursor = tvShowRepository.getCursorAllTvShow();
                break;
            case CODE_TV_SHOW_ITEM:
                cursor = tvShowRepository.getCursorTvShowById(ContentUris.parseId(uri));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(Objects.requireNonNull(getContext()).getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return String.valueOf(MOVIE_URI);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_DIR:
                assert values != null;
                added = movieRepository.insertMovie(Movie.fromContentValues(values));
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, added);
            case CODE_TV_SHOW_DIR:
                assert values != null;
                added = tvShowRepository.insertTvShow(TvShow.fromContentValues(values));
                return ContentUris.withAppendedId(uri, added);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_ITEM:
                Movie movie = movieRepository.getMovieById(ContentUris.parseId(uri));
                deleted = movieRepository.deleteMovie(movie);
                break;
            case CODE_MOVIE_DIR:
                deleted = movieRepository.deleteAllMovie();
                break;
            case CODE_TV_SHOW_ITEM:
                TvShow tvShow = tvShowRepository.getTvShowById(ContentUris.parseId(uri));
                deleted = tvShowRepository.deleteTvShow(tvShow);
                break;
            case CODE_TV_SHOW_DIR:
                deleted = tvShowRepository.deleteAllTvShow();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (MATCHER.match(uri)) {
            case CODE_MOVIE_ITEM:
                assert values != null;
                Movie movie = Movie.fromContentValues(values);
                movie.setId(ContentUris.parseId(uri));
                updated = movieRepository.updateMovie(movie);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                return updated;
            case CODE_TV_SHOW_ITEM:
                assert values != null;
                TvShow tvShow = TvShow.fromContentValues(values);
                tvShow.setId(ContentUris.parseId(uri));
                updated = tvShowRepository.updateTvShow(tvShow);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
                return updated;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
