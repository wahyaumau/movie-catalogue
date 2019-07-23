package com.example.moviecataloguefinal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.notification.NotificationItem;
import com.example.moviecataloguefinal.notification.ReminderReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderSettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private ReminderReceiver reminderReceiver;
    public static int currentNotificationCount = 0;
    public static List<NotificationItem> listNotificationItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);
        Switch switchDailyReminder = findViewById(R.id.switchDailyReminder);
        Switch switchReleaseReminder = findViewById(R.id.switchReleaseReminder);
        switchDailyReminder.setOnCheckedChangeListener(this);
        switchReleaseReminder.setOnCheckedChangeListener(this);
        reminderReceiver = new ReminderReceiver();

        if (ReminderReceiver.isAlarmSet(this, ReminderReceiver.TYPE_DAILY)) {
            switchDailyReminder.setChecked(true);
        }
        if (ReminderReceiver.isAlarmSet(this, ReminderReceiver.TYPE_RELEASE)) {
            switchReleaseReminder.setChecked(true);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchDailyReminder:
                if (isChecked) {
                    String title = getResources().getString(R.string.notification_title);
                    String message = getResources().getString(R.string.notification_message);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 7);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
//                    calendar.add(Calendar.SECOND, 2);
                    reminderReceiver.setDailyReminder(this, calendar, title, message);
                } else {
                    reminderReceiver.cancelAlarm(this, ReminderReceiver.TYPE_DAILY);
                }
                break;
            case R.id.switchReleaseReminder:
                if (isChecked) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 8);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
//                    calendar.add(Calendar.SECOND, 2);
                    reminderReceiver.setReleaseReminder(this, calendar);
                } else {
                    reminderReceiver.cancelAlarm(this, ReminderReceiver.TYPE_RELEASE);
                }
                break;
        }
    }
}