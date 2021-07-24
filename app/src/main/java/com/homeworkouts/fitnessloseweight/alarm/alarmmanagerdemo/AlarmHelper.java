package com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.util.Log;


import com.homeworkouts.fitnessloseweight.receiver.BootReceiver;

import java.util.Calendar;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class AlarmHelper {
    public static final String PREFERENCE_LAST_REQUEST_CODE = "PREFERENCE_LAST_REQUEST_CODE";
    public static final String TAG = "AlarmMainActivity";
    public AlarmManager alarmManager;
    public Context context;
    public SharedPreferences sharedPreferences;

    public AlarmHelper(Context context2) {
        this.context = context2;
        this.alarmManager = (AlarmManager) context2.getSystemService(CATEGORY_ALARM);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context2);
    }

    private int getNextRequestCode() {
        String str = "PREFERENCE_LAST_REQUEST_CODE";
        int i = this.sharedPreferences.getInt(str, 0) + 1;
        if (i == Integer.MAX_VALUE) {
            i = 0;
        }
        this.sharedPreferences.edit().putInt(str, i).apply();
        return i;
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent("com.homeworkouts.fitnessloseweight.alarmmanagerdemo.NOTIFY_ACTION");
        intent.setClass(this.context, NotificationPublisher.class);
        intent.setFlags(268435456);
        return PendingIntent.getBroadcast(this.context, getNextRequestCode(), intent, 134217728);
    }

    private void setBootReceiverEnabled(int i) {
        Log.d(TAG, "setBootReceiverEnabled: ");
        this.context.getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, BootReceiver.class), i, 1);
    }

    public PendingIntent existAlarm(int i) {
        Intent intent = new Intent("com.homeworkouts.fitnessloseweight.alarmmanagerdemo.NOTIFY_ACTION");
        intent.setClass(this.context, NotificationPublisher.class);
        return PendingIntent.getBroadcast(this.context, i, intent, 536870912);
    }

    public boolean isAlarmScheduled(int i) {
        Intent intent = new Intent("com.homeworkouts.fitnessloseweight.alarmmanagerdemo.NOTIFY_ACTION");
        intent.setClass(this.context, NotificationPublisher.class);
        return PendingIntent.getBroadcast(this.context, i, intent, 536870912) != null;
    }

    public void schedulePendingIntent(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, 0);
        instance.set(14, 0);
        instance.set(9, i3);
        schedulePendingIntent(instance.getTimeInMillis(), getPendingIntent());
    }

    public void schedulePendingIntent(int i, int i2, int i3, long j) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, 0);
        instance.set(14, 0);
        instance.set(9, i3);
        schedulePendingIntent(instance.getTimeInMillis(), getPendingIntent(), j);
    }

    public void schedulePendingIntent(long j, PendingIntent pendingIntent) {
        StringBuilder sb = new StringBuilder();
        sb.append("schedulePendingIntent: ");
        sb.append(j);
        sb.append("/");
        sb.append(pendingIntent);
        Log.d(TAG, sb.toString());
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            this.alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        } else if (i >= 19) {
            this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        } else {
            this.alarmManager.set(AlarmManager.RTC_WAKEUP, j, pendingIntent);
        }
        setBootReceiverEnabled(1);
    }

    public void schedulePendingIntent(long j, PendingIntent pendingIntent, long j2) {
        StringBuilder sb = new StringBuilder();
        sb.append("schedulePendingIntent: ");
        sb.append(j);
        sb.append("/");
        sb.append(pendingIntent);
        Log.d(TAG, sb.toString());
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            this.alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        } else if (i >= 19) {
            this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        } else {
            this.alarmManager.set(AlarmManager.RTC_WAKEUP, j + j2, pendingIntent);
        }
        setBootReceiverEnabled(1);
    }

    public void unschedulePendingIntent() {
        PendingIntent pendingIntent = getPendingIntent();
        pendingIntent.cancel();
        this.alarmManager.cancel(pendingIntent);
    }
}
