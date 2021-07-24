package com.homeworkouts.fitnessloseweight.general;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.core.app.NotificationCompat;
import androidx.multidex.MultiDex;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.notification.NotificationBroadcastReciever;

import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;


public class MyApplication extends Application implements ActivityLifecycleCallbacks {
    public static Boolean app_status = Boolean.valueOf(true);
    public static InterstitialAd interstitial;
    SharedPreferenceManager sharedPreferenceManager;

    public static MyApplication absWomenApplication;
    public TextToSpeech textToSpeech;

    public static MyApplication getInstance() {
        return absWomenApplication;
    }

    public void a() {
        if (this.textToSpeech == null) {
            this.textToSpeech = new TextToSpeech(getInstance(), new com.homeworkouts.fitnessloseweight.b.b(this));
        }
    }

    public void a(int i) {
        if (i == 0) {
            this.textToSpeech.setLanguage(Locale.US);
        }
    }

    public void addEarCorn() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.addEarcon("tick", "com.homeworkouts.fitnessloseweight", R.raw.clocktick_trim);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isSpeaking() {
        return Boolean.valueOf(this.textToSpeech.isSpeaking());
    }

    class ForegroundCheckTask extends AsyncTask<Context, Void, Boolean> {
        ForegroundCheckTask() {
        }


        public Boolean doInBackground(Context... contextArr) {
            return Boolean.valueOf(isAppOnForeground(contextArr[0]));
        }

        private boolean isAppOnForeground(Context context) {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }


    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath(getString(R.string.font_light)).setFontAttrId(R.attr.fontPath).build());
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        registerActivityLifecycleCallbacks(this);
        initInterstitialAd();
        ConnectionBuddy.getInstance().init(new ConnectionBuddyConfiguration.Builder(this).build());
        absWomenApplication = this;
        new Thread(new com.homeworkouts.fitnessloseweight.b.a(this)).start();
    }
    public void playEarCorn() {
        try {
            if (this.textToSpeech != null) {
                String str = "tick";
                if (VERSION.SDK_INT >= 21) {
                    this.textToSpeech.playEarcon(str, 0, null, "com.outthinking.abs");
                } else {
                    this.textToSpeech.playEarcon(str, 0, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void speak(String str) {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.setSpeechRate(1.0f);
                this.textToSpeech.speak(str, 1, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (this.textToSpeech != null) {
                this.textToSpeech.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActivityStarted(Activity activity) {
        try {
            if (((Boolean) new ForegroundCheckTask().execute(new Context[]{getApplicationContext()}).get()).booleanValue()) {
                stopService(new Intent(this, NotificationBroadcastReciever.class));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }

    public void onActivityStopped(Activity activity) {
        try {
            boolean booleanValue = ((Boolean) new ForegroundCheckTask().execute(new Context[]{getApplicationContext()}).get()).booleanValue();
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("forground===");
            sb.append(booleanValue);
            printStream.println(sb.toString());
            if (!booleanValue) {
                PendingIntent broadcast = PendingIntent.getBroadcast(this, 1, new Intent(this, NotificationBroadcastReciever.class), 268435456);
                AlarmManager alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
                Calendar instance = Calendar.getInstance();
                if (VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(0, instance.getTimeInMillis() + 604800000, broadcast);
                } else if (VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(0, instance.getTimeInMillis() + 604800000, broadcast);
                } else if (VERSION.SDK_INT >= 16) {
                    alarmManager.setRepeating(0, instance.getTimeInMillis() + 604800000, instance.getTimeInMillis() + 604800000, broadcast);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }

    public void initInterstitialAd() {
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(getResources().getString(R.string.interstitial_key));
            interstitial.loadAd(new AdRequest.Builder().build());
        }
    }
}
