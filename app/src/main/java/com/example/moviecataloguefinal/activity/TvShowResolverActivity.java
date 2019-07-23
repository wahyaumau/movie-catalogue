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
import com.example.moviecataloguefinal.adapter.TvShowResolverAdapter;
import com.example.moviecataloguefinal.helper.MappingHelper;
import com.example.moviecataloguefinal.model.TvShow;
import com.example.moviecataloguefinal.helper.LoadItemsCallback;
import com.example.moviecataloguefinal.provider.MovieProvider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class TvShowResolverActivity extends AppCompatActivity implements LoadItemsCallback, View.OnClickListener {
    private TvShowResolverAdapter tvShowResolverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_resolver);
        FloatingActionButton floatingActionButtonRemoveTvShowResolver = findViewById(R.id.floatingActionButtonRemoveAllTvShowResolver);
        floatingActionButtonRemoveTvShowResolver.setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_tv_show_resolver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        tvShowResolverAdapter = new TvShowResolverAdapter(this);
        recyclerView.setAdapter(tvShowResolverAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(MovieProvider.TV_SHOW_URI, true, myObserver);
        new GetTvShowAsync(this, this).execute();
    }

    @Override
    public void postExecute(Cursor cursor) {
        List<TvShow> listTvShow = MappingHelper.mapCursorToListTvShow(cursor);
        if (listTvShow.size() > 0) {
            tvShowResolverAdapter.setListTvShow(listTvShow);
        } else {
            Toast.makeText(this, getResources().getString(R.string.message_empty_tv_show), Toast.LENGTH_SHORT).show();
            tvShowResolverAdapter.setListTvShow(new ArrayList<TvShow>());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floatingActionButtonRemoveAllTvShowResolver) {
            getContentResolver().delete(MovieProvider.TV_SHOW_URI, null, null);
            getContentResolver().notifyChange(MovieProvider.TV_SHOW_URI, new MovieResolverActivity.DataObserver(new Handler(), this));
            Snackbar.make(v, getResources().getString(R.string.message_remove_all_tv_show_from_database), Snackbar.LENGTH_SHORT).show();
        }
    }

    private static class GetTvShowAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadItemsCallback> weakCallback;


        private GetTvShowAsync(Context context, LoadItemsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(MovieProvider.TV_SHOW_URI, null, null, null, null);
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
            new GetTvShowAsync(context, (TvShowResolverActivity) context).execute();
        }
    }
}
