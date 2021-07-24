package com.homeworkouts.fitnessloseweight.notification;

import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;

import androidx.core.internal.view.SupportMenu;

import com.homeworkouts.fitnessloseweight.R;

public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_FIVE_ID = "loopbots_terminal";
    public static final String CHANNEL_FIVE_NAME = "loopbots_terminal";
    private NotificationManager notifManager;

    public NotificationHelper(Context context) {
        super(context);
        createChannels();
    }

    public void createChannels() {
        NotificationManager notificationManager = this.notifManager;
        NotificationChannel notificationChannel = new NotificationChannel("loopbots_terminal", "loopbots_terminal", 4);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(1);
        getManager().createNotificationChannel(notificationChannel);
    }

    public void notify(int i, Builder builder) {
        getManager().notify(i, builder.build());
    }

    private NotificationManager getManager() {
        if (this.notifManager == null) {
            this.notifManager = (NotificationManager) getSystemService("notification");
        }
        return this.notifManager;
    }

    public Builder getWeeklyNotification(String str, String str2, PendingIntent pendingIntent) {
        return new Builder(getApplicationContext(), "loopbots_terminal").setContentTitle(str).setContentText(str2).setSmallIcon(R.drawable.logo_noti).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo1)).setAutoCancel(true).setOngoing(true).setContentIntent(pendingIntent).setOnlyAlertOnce(true).setSound(RingtoneManager.getDefaultUri(2));
    }
}
