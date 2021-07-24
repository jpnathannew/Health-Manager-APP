package com.homeworkouts.fitnessloseweight.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class DailyBrodcast extends BroadcastReceiver {
    public Context context;

    public void onReceive(Context context2, Intent intent) {
        this.context = context2;
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED") || intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")) {
            setAlarm();
        }
    }

    public void setAlarm() {
        Calendar instance = Calendar.getInstance();
        instance.set(11, 7);
        instance.set(12, 0);
        instance.set(13, 0);
        ((AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(this.context, 100, new Intent(this.context, NotificationReceiver.class), 134217728));
    }
}
