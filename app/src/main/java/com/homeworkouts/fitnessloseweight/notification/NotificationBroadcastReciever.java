package com.homeworkouts.fitnessloseweight.notification;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.Log;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.general.Splash;
import java.util.Random;

import static android.os.Build.VERSION_CODES.O;

public class NotificationBroadcastReciever extends BroadcastReceiver {
    private static final int notification_five = 105;
    private NotificationHelper notificationHelper;

    public void onReceive(Context context, Intent intent) {
        if (VERSION.SDK_INT >= O) {
            this.notificationHelper = new NotificationHelper(context);
        }
        ComponentName componentName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0)).topActivity;
        StringBuilder sb = new StringBuilder();
        sb.append("package_name");
        sb.append(context.getPackageName());
        Log.d("package_name", sb.toString());
        String[] stringArray = context.getResources().getStringArray(R.array.notification_text);
        String str = stringArray[new Random().nextInt(stringArray.length)];
        if (componentName.getPackageName().equals(context.getPackageName())) {
            ((NotificationManager) context.getSystemService("notification")).cancel(1);
            return;
        }
        int currentTimeMillis = (int) System.currentTimeMillis();
        Intent intent2 = new Intent(context, Splash.class);
        intent2.setFlags(603979776);
        PendingIntent activity = PendingIntent.getActivity(context, currentTimeMillis, intent2, 0);
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        if (VERSION.SDK_INT >= 26) {
            postNotification(105, context.getString(R.string.app_name), str, activity);
            return;
        }
        ((NotificationManager) context.getSystemService("notification")).notify(1, new Builder(context).setContentTitle(context.getString(R.string.app_name)).setContentText(str).setSmallIcon(R.drawable.logo_noti).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo1)).setSound(defaultUri).setContentIntent(activity).setAutoCancel(true).build());
    }

    public void postNotification(int i, String str, String str2, PendingIntent pendingIntent) {
        Builder weeklyNotification = i != 105 ? null : this.notificationHelper.getWeeklyNotification(str, str2, pendingIntent);
        if (weeklyNotification != null) {
            this.notificationHelper.notify(i, weeklyNotification);
        }
    }
}
