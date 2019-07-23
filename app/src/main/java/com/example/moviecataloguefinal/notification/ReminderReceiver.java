package com.example.moviecataloguefinal.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.MainActivity;
import com.example.moviecataloguefinal.activity.ReminderSettingActivity;
import com.example.moviecataloguefinal.api.ApiClient;
import com.example.moviecataloguefinal.api.JsonPlaceholderApi;
import com.example.moviecataloguefinal.model.Movie;
import com.example.moviecataloguefinal.model.MovieResponse;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderReceiver extends BroadcastReceiver {
    public static final String TYPE_DAILY = "com.example.moviecataloguefinal.notification.TYPE_DAILY";
    public static final String TYPE_RELEASE = "com.example.moviecataloguefinal.notification.TYPE_RELEASE";
    public static final String EXTRA_MESSAGE = "com.example.moviecataloguefinal.notification.EXTRA_MESSAGE";
    public static final String EXTRA_TITLE = "com.example.moviecataloguefinal.notification.EXTRA_TITLE";
    public static final String EXTRA_TYPE = "com.example.moviecataloguefinal.notification.EXTRA_TYPE";

//    private int currentNotificationCount = 0;
//    private List<NotificationItem> notificationItems = new ArrayList<>();

    private final static int REQUEST_CODE_DAILY_REMINDER = 100;
    private final static int REQUEST_CODE_RELEASE_REMINDER = 101;

    public ReminderReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        int notifId = intent.getStringExtra(EXTRA_TYPE).equalsIgnoreCase(TYPE_DAILY) ? REQUEST_CODE_DAILY_REMINDER : REQUEST_CODE_RELEASE_REMINDER;
        if (notifId == REQUEST_CODE_DAILY_REMINDER) {
            String title = intent.getStringExtra(EXTRA_TITLE);
            String message = intent.getStringExtra(EXTRA_MESSAGE);
            NotificationItem notificationItem = new NotificationItem(ReminderSettingActivity.currentNotificationCount, title, message);
            ReminderSettingActivity.listNotificationItem.add(notificationItem);
            showNotification(context, notificationItem.getTitle(), notificationItem.getMessage());
            ReminderSettingActivity.currentNotificationCount++;
        } else {
            JsonPlaceholderApi jsonPlaceholderApi = ApiClient.getClient().create(JsonPlaceholderApi.class);
            Call<MovieResponse> call = jsonPlaceholderApi.getUpcomingMovies(ApiClient.API_KEY, ApiClient.getLanguageKey());

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    MovieResponse movieResponse = response.body();
                    assert movieResponse != null;
                    ArrayList<Movie> listMovie = movieResponse.getMovies();
                    for (Movie movie : listMovie) {
                        Calendar today = Calendar.getInstance();
//                        today.set(2019, 6, 12);
                        Calendar movieReleaseCalendar = Calendar.getInstance();
                        movieReleaseCalendar.setTime(movie.getReleaseDate());
                        if (today.get(Calendar.DAY_OF_YEAR) == movieReleaseCalendar.get(Calendar.DAY_OF_YEAR) &&
                                today.get(Calendar.YEAR) == movieReleaseCalendar.get(Calendar.YEAR)) {
                        String message = String.format(context.getResources().getString(R.string.notification_release_message), movie.getTitle());
                        NotificationItem notificationItem = new NotificationItem(ReminderSettingActivity.currentNotificationCount, movie.getTitle(), message);
                        ReminderSettingActivity.listNotificationItem.add(notificationItem);
                        showNotification(context, notificationItem.getTitle(), notificationItem.getMessage());
                        ReminderSettingActivity.currentNotificationCount++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    Log.d("Failure", t.getMessage());
                }
            });
        }
    }

    private void showNotification(Context context, String title, String message) {
        String CHANNEL_ID = "com.example.moviecataloguefinal.notification.CHANNEL_ID";
        String CHANNEL_NAME = "com.example.moviecataloguefinal.notification.CHANNEL_NAME";
        String NOTIFICATION_GROUP = "com.example.moviecataloguefinal.notification.NOTIFICATION_GROUP";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 10, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder;

        int maxNotif = 2;
        if (ReminderSettingActivity.currentNotificationCount < maxNotif) {
            notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.film)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setGroup(NOTIFICATION_GROUP)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(String.format(context.getResources().getString(R.string.notification_release_message),
                            ReminderSettingActivity.listNotificationItem.get(ReminderSettingActivity.currentNotificationCount).getTitle()))
                    .addLine(String.format(context.getResources().getString(R.string.notification_release_message),
                            ReminderSettingActivity.listNotificationItem.get(ReminderSettingActivity.currentNotificationCount - 1).getTitle()))
                    .setBigContentTitle(ReminderSettingActivity.currentNotificationCount +
                            context.getResources().getString(R.string.notification_message_content_title))
                    .setSummaryText(context.getResources().getString(R.string.notification_message_content_title));
            notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(ReminderSettingActivity.currentNotificationCount +
                            context.getResources().getString(R.string.notification_message_content_title))
                    .setContentText(context.getResources().getString(R.string.notification_message))
                    .setSmallIcon(R.drawable.film)
                    .setGroup(NOTIFICATION_GROUP)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationBuilder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = notificationBuilder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(ReminderSettingActivity.currentNotificationCount, notification);
            SystemClock.sleep(2000);
        }

    }

    public void setDailyReminder(Context context, Calendar calendar, String title, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, TYPE_DAILY);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_MESSAGE, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_DAILY_REMINDER, intent, 0);
        if (alarmManager != null) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getResources().getString(R.string.message_daily_reminder_setup), Toast.LENGTH_SHORT).show();
    }

    public void setReleaseReminder(Context context, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, TYPE_RELEASE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE_RELEASE_REMINDER, intent, 0);
        if (alarmManager != null) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, context.getResources().getString(R.string.message_release_reminder_setup), Toast.LENGTH_SHORT).show();
    }


    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? REQUEST_CODE_DAILY_REMINDER : REQUEST_CODE_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        String message = type.equalsIgnoreCase(TYPE_DAILY) ?
                context.getResources().getString(R.string.message_cancel_daily_reminder) :
                context.getResources().getString(R.string.message_cancel_release_reminder);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }


    public static boolean isAlarmSet(Context context, String type) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? REQUEST_CODE_DAILY_REMINDER : REQUEST_CODE_RELEASE_REMINDER;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }
}
