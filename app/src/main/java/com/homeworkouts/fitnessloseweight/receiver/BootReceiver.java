package com.homeworkouts.fitnessloseweight.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homeworkouts.fitnessloseweight.alarm.alarm_pojo_classes.Reminder_custom;
import com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo.AlarmHelper;
import com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo.NotificationPublisher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.core.app.NotificationCompat.CATEGORY_ALARM;

public class BootReceiver extends BroadcastReceiver {
    public static final String PREFERENCE_LAST_REQUEST_CODE = "PREFERENCE_LAST_REQUEST_CODE";
    public static final String TAG = "BootReceiver";
    public AlarmHelper alarmHelper;
    public Context context;
    public SharedPreferences sharedPreferences;
    public SimpleDateFormat startTimeFormat;

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
        this.context.getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, BootReceiver.class), i, 1);
    }

    public void a(int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, 0);
        instance.set(14, 0);
        instance.set(9, i3);
        a(instance.getTimeInMillis(), getPendingIntent());
    }

    public void a(long j, PendingIntent pendingIntent) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("scheduling_PendingIntent: ");
        sb.append(j);
        sb.append("/");
        sb.append(pendingIntent);
        Log.d(str, sb.toString());
        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(CATEGORY_ALARM);
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            alarmManager.setExactAndAllowWhileIdle(0, j, pendingIntent);
        } else if (i >= 19) {
            alarmManager.setExact(0, j, pendingIntent);
        } else {
            alarmManager.set(0, j, pendingIntent);
        }
        setBootReceiverEnabled(1);
    }

    public void a(AlarmHelper alarmHelper2, Calendar calendar) {
        int i;
        int i2;
        int i3;
        if (start_TimeFormat().format(calendar.getTime()).endsWith("AM")) {
            i2 = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            i = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i3 = 0;
        } else {
            i2 = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            i = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i3 = 1;
        }
        a(i2, i, i3);
    }

    public SimpleDateFormat getHourFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh");
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }

    public SimpleDateFormat getMinuteFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }

    public void onReceive(Context context2, Intent intent) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onReceive - Intent Action: ");
        sb.append(intent.getAction());
        Log.d(str, sb.toString());
        this.context = context2;
        setAlarm(context2);
    }

    public void setAlarm(Context context2) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context2);
        List list = (List) new Gson().fromJson(this.sharedPreferences.getString("Reminder_customObjectList", null), new TypeToken<List<Reminder_custom>>() {
        }.getType());
        if (list != null && list.size() > 0) {
            this.alarmHelper = new AlarmHelper(context2);
            Calendar instance = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
            for (int i = 0; i < list.size(); i++) {
                try {
                    instance.setTime(simpleDateFormat.parse(((Reminder_custom) list.get(i)).gettime().substring(0, 5)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                a(this.alarmHelper, instance);
            }
        }
    }

    public SimpleDateFormat start_TimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }
}
