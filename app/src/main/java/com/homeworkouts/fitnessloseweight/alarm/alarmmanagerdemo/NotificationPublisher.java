package com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo;

import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.homeworkouts.fitnessloseweight.alarm.alarm_pojo_classes.Reminder_custom;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.fragment.Workout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NotificationPublisher extends BroadcastReceiver {
    public static final String PREFERENCE_LAST_NOTIF_ID = "PREFERENCE_LAST_NOTIF_ID";
    public String TAG = "NotificationPublisher";
    public AlarmHelper alarmHelper;
    public Context context;
    public SharedPreferences sharedPreferences;
    public SimpleDateFormat startTimeFormat;

    private int getNextNotifId() {
        SharedPreferences sharedPreferences2 = this.sharedPreferences;
        String str = PREFERENCE_LAST_NOTIF_ID;
        int i = sharedPreferences2.getInt(str, 0) + 1;
        if (i == Integer.MAX_VALUE) {
            i = 0;
        }
        this.sharedPreferences.edit().putInt(str, i).apply();
        return i;
    }

    private void startNotification(Context context2) {
        PendingIntent existAlarm = this.alarmHelper.existAlarm(this.sharedPreferences.getInt(PREFERENCE_LAST_NOTIF_ID, 0));
        if (existAlarm != null) {
            existAlarm.cancel();
        }
        String str = "Let's do Height boosting Exercise today.";
        String str2 = "Hey! it's Height Increase Workout time";
        String str3 = "notification";
        String str4 = "android.intent.category.LAUNCHER";
        String str5 = "android.intent.action.MAIN";
        if (VERSION.SDK_INT < 26) {
            NotificationManager notificationManager = (NotificationManager) context2.getSystemService(str3);
            Intent intent = new Intent(context2, Workout.class);
            intent.setAction(str5);
            intent.addCategory(str4);
            intent.addFlags(268435456);
            notificationManager.notify(getNextNotifId(), new Builder(context2).setContentIntent(PendingIntent.getActivity(context2, getNextNotifId(), intent, 0)).setAutoCancel(true).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_stat_name).setContentTitle(str2).setContentText(str).setDefaults(1).build());
            return;
        }
        Intent intent2 = new Intent(context2, Workout.class);
        intent2.setAction(str5);
        intent2.addCategory(str4);
        intent2.addFlags(268435456);
        NotificationManager notificationManager2 = (NotificationManager) context2.getSystemService(str3);
        String str6 = "my_channel_id_01";
        NotificationChannel notificationChannel = new NotificationChannel(str6, "My Notifications", 4);
        notificationChannel.setDescription("Channel description");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-65536);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationChannel.enableVibration(true);
        notificationManager2.createNotificationChannel(notificationChannel);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context2, str6);
        builder.setAutoCancel(true).setContentIntent(PendingIntent.getActivity(context2, getNextNotifId(), intent2, 0)).setDefaults(-1).setAutoCancel(true).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_stat_name).setContentTitle(str2).setContentText(str).setDefaults(1);
        notificationManager2.notify(getNextNotifId(), builder.build());
    }

    public void a(AlarmHelper alarmHelper2, Calendar calendar) {
        int parseInt;
        int parseInt2;
        int i;
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("setTimeHrAndMin   ");
        sb.append(startTimeFormat().format(calendar.getTime()));
        Log.d(str, sb.toString());
        if (startTimeFormat().format(calendar.getTime()).equalsIgnoreCase("AM")) {
            parseInt = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            parseInt2 = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i = 0;
        } else if (startTimeFormat().format(calendar.getTime()).equalsIgnoreCase("PM")) {
            parseInt = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            parseInt2 = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i = 1;
        } else {
            return;
        }
        alarmHelper2.schedulePendingIntent(parseInt, parseInt2, i, 86400000);
    }

    public SimpleDateFormat getHourFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh", Locale.ENGLISH);
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }

    public SimpleDateFormat getMinuteFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm", Locale.ENGLISH);
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }

    public void onReceive(Context context2, Intent intent) {
        String str = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("onReceive1 ==========");
        sb.append(intent.getAction());
        Log.d(str, sb.toString());
        this.context = context2;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context2);
        List list = (List) new Gson().fromJson(this.sharedPreferences.getString("Reminder_customObjectList", null), new TypeToken<List<Reminder_custom>>() {
        }.getType());
        Calendar instance = Calendar.getInstance();
        instance.get(11);
        instance.get(12);
        int i = instance.get(7);
        if (list != null && list.size() > 0) {
            this.alarmHelper = new AlarmHelper(context2);
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (((Reminder_custom) list.get(i2)).gettime().equals(startTimeFormat().format(instance.getTime())) && ((Reminder_custom) list.get(i2)).getOnoff()) {
                    if ((((Reminder_custom) list.get(i2)).getSun() && i == 1) || ((((Reminder_custom) list.get(i2)).getMon() && i == 2) || ((((Reminder_custom) list.get(i2)).getTue() && i == 3) || ((((Reminder_custom) list.get(i2)).getWen() && i == 4) || ((((Reminder_custom) list.get(i2)).getThr() && i == 5) || ((((Reminder_custom) list.get(i2)).getFri() && i == 6) || (((Reminder_custom) list.get(i2)).getSat() && i == 7))))))) {
                        startNotification(context2);
                    }
                    a(this.alarmHelper, instance);
                }
            }
            String action = intent.getAction();
            action.getClass();
            if (action.equals("android.intent.action.TIME_SET")) {
                for (int i3 = 0; i3 < list.size(); i3++) {
                    String str2 = this.TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("onReceive: +++++++++++++");
                    sb2.append(((Reminder_custom) list.get(i3)).gettime());
                    Log.d(str2, sb2.toString());
                    if (!((Reminder_custom) list.get(i3)).gettime().toUpperCase().endsWith("AM") && !((Reminder_custom) list.get(i3)).gettime().toLowerCase().endsWith("am")) {
                        String str3 = "a.m.";
                        if (!((Reminder_custom) list.get(i3)).gettime().toUpperCase().endsWith(str3) && !((Reminder_custom) list.get(i3)).gettime().toLowerCase().endsWith(str3)) {
                            if (!((Reminder_custom) list.get(i3)).gettime().toUpperCase().endsWith("PM") && !((Reminder_custom) list.get(i3)).gettime().toUpperCase().endsWith("pm")) {
                                String str4 = "p.m.";
                                if (!((Reminder_custom) list.get(i3)).gettime().toUpperCase().endsWith(str4) && !((Reminder_custom) list.get(i3)).gettime().toLowerCase().endsWith(str4)) {
                                }
                            }
                            this.alarmHelper.schedulePendingIntent(Integer.parseInt(((Reminder_custom) list.get(i3)).gettime().substring(0, 2)), Integer.parseInt(((Reminder_custom) list.get(i3)).gettime().substring(3, 5)), 1);
                        }
                    }
                    this.alarmHelper.schedulePendingIntent(Integer.parseInt(((Reminder_custom) list.get(i3)).gettime().substring(0, 2)), Integer.parseInt(((Reminder_custom) list.get(i3)).gettime().substring(3, 5)), 0);
                }
            }
        }
    }

    public SimpleDateFormat startTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        this.startTimeFormat = simpleDateFormat;
        return simpleDateFormat;
    }
}
