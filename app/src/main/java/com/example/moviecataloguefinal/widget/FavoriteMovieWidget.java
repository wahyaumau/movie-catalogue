package com.example.moviecataloguefinal.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.DetailMovieActivity;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.repository.MovieRepository;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteMovieWidget extends AppWidgetProvider {

    private static final String ACTION_DETAIL = "com.example.moviecataloguefinal.ACTION_DETAIL";
    public static final String EXTRA_MOVIE_ID = "com.example.moviecataloguefinal.EXTRA_MOVIE_ID";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent serviceIntent = new Intent(context, StackWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            Intent detailMovieIntent = new Intent(context, FavoriteMovieWidget.class);
            detailMovieIntent.setAction(ACTION_DETAIL);
            detailMovieIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent detailMoviePendingIntent = PendingIntent.getBroadcast(context, 0, detailMovieIntent, 0);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_favorite_movie);
            remoteViews.setRemoteAdapter(R.id.stack_view, serviceIntent);
            remoteViews.setEmptyView(R.id.stack_view, R.id.empty_view);
            remoteViews.setPendingIntentTemplate(R.id.stack_view, detailMoviePendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.item_widget_movie);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        MovieRepository movieRepository = new MovieRepository(context);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_DETAIL)) {
                long movieId = intent.getLongExtra(EXTRA_MOVIE_ID, 0);

                Movie movie = movieRepository.getMovieById(movieId);
                Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intentActivity = new Intent(context, DetailMovieActivity.class);
                intentActivity.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) movieId, intentActivity, 0);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), FavoriteMovieWidget.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);
    }

}