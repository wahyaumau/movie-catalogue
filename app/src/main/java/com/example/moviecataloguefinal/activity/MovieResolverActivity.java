package com.example.moviecataloguefinal.activity;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.adapter.MovieResolverAdapter;
import com.example.moviecataloguefinal.helper.MappingHelper;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.helper.LoadItemsCallback;
import com.example.moviecataloguefinal.provider.MovieProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MovieResolverActivity extends AppCompatActivity implements LoadItemsCallback, View.OnClickListener {
    private MovieResolverAdapter movieResolverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_resolver);
        FloatingActionButton floatingActionButtonRemoveMovieResolver = findViewById(R.id.floatingActionButtonRemoveAllMovieResolver);
        floatingActionButtonRemoveMovieResolver.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_movie_resolver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        movieResolverAdapter = new MovieResolverAdapter(this);
        recyclerView.setAdapter(movieResolverAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(MovieProvider.MOVIE_URI, true, myObserver);
        new GetMovieAsync(this, this).execute();
    }

    @Override
    public void postExecute(Cursor cursor) {
        List<Movie> listMovie = MappingHelper.mapCursorToListMovie(cursor);
        if (listMovie.size() > 0) {
            movieResolverAdapter.setListMovie(listMovie);
        } else {
            Toast.makeText(this, getResources().getString(R.string.message_empty_movie), Toast.LENGTH_SHORT).show();
            movieResolverAdapter.setListMovie(new ArrayList<Movie>());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButtonRemoveAllMovieResolver) {
            getContentResolver().delete(MovieProvider.MOVIE_URI, null, null);
            getContentResolver().notifyChange(MovieProvider.MOVIE_URI, new DataObserver(new Handler(), this));
            Snackbar.make(v, getResources().getString(R.string.message_remove_all_favorite_movie_from_database), Snackbar.LENGTH_SHORT).show();
        }
    }

    private static class GetMovieAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadItemsCallback> weakCallback;


        private GetMovieAsync(Context context, LoadItemsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(MovieProvider.MOVIE_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakCallback.get().postExecute(data);
        }

    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new GetMovieAsync(context, (MovieResolverActivity) context).execute();
        }
    }
}