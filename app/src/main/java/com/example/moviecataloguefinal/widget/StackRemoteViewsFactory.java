package com.example.moviecataloguefinal.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.repository.MovieRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Movie> listMovie = new ArrayList<>();
    private final Context context;
    private MovieRepository movieRepository;

    StackRemoteViewsFactory(Context context) {
        this.context = context;

    }

    @Override
    public void onCreate() {
        movieRepository = new MovieRepository(context);
    }


    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        listMovie = movieRepository.getListAllMovie();
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget_movie);
        if (getCount() > 0) {
            Movie movie = listMovie.get(position);
            rv.setImageViewBitmap(R.id.imageViewWidgetItem, getBitmapByUrl(ApiClient.IMAGE_URL + movie.getBackdropPath()));

            Bundle extras = new Bundle();
            extras.putLong(FavoriteMovieWidget.EXTRA_MOVIE_ID, listMovie.get(position).getId());
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            rv.setOnClickFillInIntent(R.id.imageViewWidgetItem, fillInIntent);
        }


        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Bitmap getBitmapByUrl(String url) {
        try {
            return new GetBitmapFromUrlAsyncTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class GetBitmapFromUrlAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}