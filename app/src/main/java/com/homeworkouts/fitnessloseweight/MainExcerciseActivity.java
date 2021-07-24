package com.homeworkouts.fitnessloseweight;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.AdChoicesView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.gson.Gson;
import com.homeworkouts.fitnessloseweight.adapters.WorkoutData;
import com.homeworkouts.fitnessloseweight.database.DatabaseOperations;
import com.homeworkouts.fitnessloseweight.general.MyApplication;
import com.homeworkouts.fitnessloseweight.utils.AppUtilss;
import com.homeworkouts.fitnessloseweight.utils.Constants;

import java.util.ArrayList;

import kr.pe.burt.android.lib.faimageview.FAImageView;

public class MainExcerciseActivity extends AppCompatActivity {
    public AdRequest A;
    public AdRequest B;
    public boolean C;
    public Toolbar D;

    public Boolean F;
    public Boolean G;
    public AdChoicesView adChoicesView;
    public LinearLayout adView;
    public boolean adloaded;
    public AdmobAds admobAdsObject;
    public FAImageView animImageFull;

    public TextView count;
    public TextView countRestTimer;
    public DatabaseOperations databaseOperations;
    public String day;
    public TextView eachSideTextView;
    public int excCouner;
    public TextView excDescInReadyToGo;
    public TextView excName;
    public TextView excNameInReadyToGo;
    public CountDownTimer excersiseTimer;
    public int i;
    public LayoutInflater inflater;
    public float k = 1.0f;
    public long l;

    public long m = 25000;
    public int mainExcCounter = 1;
    public ArrayList<WorkoutData> n;
    public NativeAd nativeAd;
    public LinearLayout nativeAdContainer;
    public TextView o;
    public FAImageView p;
    public ImageView pauseMainExcersise;
    public ImageView pauseRestTime;
    public ImageView playPause;
    public ImageView previous_exc;
    public double prog;
    public double progress;
    public RoundCornerProgressBar progressbar;
    public TextView q;
    public LinearLayout r;
    public CountDownTimer readyToGoTimer;
    public CoordinatorLayout readytogo_layout;

    public CoordinatorLayout restScreen;
    public CountDownTimer restTimer;
    public ProgressBar restTimerprogress;
    public ImageView resumRestTime;
    public ImageView resumeMainExcersise;
    public ProgressBar s;
    public long s1;
    public TextView skip;
    public TextView skipRestTime;
    public ImageView skip_exc;
    public Context t;
    public int textInitStatus;
    public TextView timerTop;
    public ProgressBar timerprogress;
    public TextView timingExersice;
    public TextToSpeech tts;
    public TextView tvProgress;
    public TextView tvProgressMax;
    public int u;
    public int v;
    public ImageView volume;
    public int w;
    public MyApplication x;
    public InterstitialAd y;
    public InterstitialAd z;

    public MainExcerciseActivity() {
        Boolean valueOf = Boolean.valueOf(false);
        this.i = 0;
        this.adloaded = false;
        this.w = 0;
        this.admobAdsObject = null;
        this.C = false;
        this.F = valueOf;
        this.G = valueOf;
    }


    public void actionView(String str) {
        if (isConnectedToInternet()) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please Check Internet Connection..", Toast.LENGTH_SHORT).show();
        }
    }

    private void getScreenHeightWidth() {
        this.t = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.u = displayMetrics.heightPixels;
        this.v = displayMetrics.widthPixels;
    }

    private boolean isConnectedToInternet() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (allNetworkInfo != null) {
                    for (NetworkInfo state : allNetworkInfo) {
                        if (state.getState() == State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }


    public void mainExcTimer(long j, int i2, float f) {
        WorkoutData workoutData = (WorkoutData) this.n.get(this.excCouner);
        this.animImageFull.reset();
        for (int addImageFrame : workoutData.getImageIdList()) {
            this.animImageFull.addImageFrame(addImageFrame);
        }
        this.restScreen.setVisibility(View.GONE);
        this.animImageFull.startAnimation();
        StringBuilder sb = new StringBuilder();
        sb.append("progressbar: ");
        sb.append(this.l / 1000);
        Log.i("setMax", sb.toString());
        this.progressbar.setMax((float) ((this.l / 1000) - 1));
        this.s = (ProgressBar) this.r.findViewById(this.excCouner);
        this.s.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        this.s.setMax((((int) this.l) / 1000) - 1);
        this.x.addEarCorn();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("setMax: ");
        sb2.append(j / 1000);
        Log.i("mainExcTimer", sb2.toString());
        final float f2 = f;
        final int i3 = i2;
        CountDownTimer r2 = new CountDownTimer(j, 1000) {


            public float f1448a = f2;
            public int b = i3;
            public int c;

            public void onFinish() {
                RoundCornerProgressBar h = MainExcerciseActivity.this.progressbar;
                float f2 = this.f1448a;
                this.f1448a = f2 + 1.0f;
                h.setProgress(f2);
                StringBuilder sb = new StringBuilder();
                sb.append("count: ");
                sb.append(this.b);
                sb.append("f ");
                sb.append(this.f1448a);
                Log.i("onTick onFinish", sb.toString());
                MainExcerciseActivity.this.excCouner = MainExcerciseActivity.this.excCouner + 1;
                MainExcerciseActivity.this.animImageFull.stopAnimation();
                String str = "MainExcerciseActivity";
                if (MainExcerciseActivity.this.excCouner < MainExcerciseActivity.this.n.size()) {
                    MainExcerciseActivity.this.restScreen.setVisibility(View.VISIBLE);
                    RoundCornerProgressBar h2 = MainExcerciseActivity.this.progressbar;
                    MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                    h2.setMax((float) ((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCouner)).getExcCycles());
                    TextView g = MainExcerciseActivity.this.tvProgress;
                    StringBuilder sb2 = new StringBuilder();
                    int i = this.b;
                    this.b = i + 1;
                    sb2.append(i);
                    String str2 = "";
                    sb2.append(str2);
                    g.setText(sb2.toString());
                    TextView m = MainExcerciseActivity.this.tvProgressMax;
                    StringBuilder sb3 = new StringBuilder();
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    sb3.append(((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCouner)).getExcCycles());
                    sb3.append(str2);
                    m.setText(sb3.toString());
                    this.f1448a = 1.0f;
                    this.b = 1;
                    MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                    double size = (double) ((float) mainExcerciseActivity3.n.size());
                    Double.isNaN(size);
                    mainExcerciseActivity3.progress = 100.0d / size;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("progress: ");
                    sb4.append(MainExcerciseActivity.this.progress);
                    Log.i(str, sb4.toString());
                    float excDayProgress = MainExcerciseActivity.this.databaseOperations.getExcDayProgress(MainExcerciseActivity.this.day);
                    MainExcerciseActivity mainExcerciseActivity4 = MainExcerciseActivity.this;
                    double d2 = (double) excDayProgress;
                    double o = mainExcerciseActivity4.progress;
                    Double.isNaN(d2);
                    mainExcerciseActivity4.progress = d2 + o;
                    if (MainExcerciseActivity.this.progress <= 100.0d) {
                        MainExcerciseActivity.this.databaseOperations.insertExcDayData(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                    }
                    try {
                        Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent.putExtra(AppUtilss.KEY_PROGRESS, MainExcerciseActivity.this.progress);
                        MainExcerciseActivity.this.sendBroadcast(intent);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        Log.e("Failed update progress", e2.getMessage());
                    }
                    MainExcerciseActivity.this.pauseRestTime.setVisibility(View.VISIBLE);
                    MainExcerciseActivity.this.resumRestTime.setVisibility(View.GONE);
                    MainExcerciseActivity mainExcerciseActivity5 = MainExcerciseActivity.this;
                    mainExcerciseActivity5.a(mainExcerciseActivity5.m);
                } else {
                    MainExcerciseActivity mainExcerciseActivity6 = MainExcerciseActivity.this;
                    double size2 = (double) ((float) mainExcerciseActivity6.n.size());
                    Double.isNaN(size2);
                    mainExcerciseActivity6.progress = 100.0d / size2;
                    float excDayProgress2 = MainExcerciseActivity.this.databaseOperations.getExcDayProgress(MainExcerciseActivity.this.day);
                    MainExcerciseActivity mainExcerciseActivity7 = MainExcerciseActivity.this;
                    double d3 = (double) excDayProgress2;
                    double o2 = mainExcerciseActivity7.progress;
                    Double.isNaN(d3);
                    mainExcerciseActivity7.progress = d3 + o2;
                    if (((int) MainExcerciseActivity.this.progress) <= 100) {
                        MainExcerciseActivity.this.databaseOperations.insertExcDayData(MainExcerciseActivity.this.day, (float) MainExcerciseActivity.this.progress);
                    }
                    try {
                        Intent intent2 = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
                        intent2.putExtra(AppUtilss.KEY_PROGRESS, MainExcerciseActivity.this.progress);
                        MainExcerciseActivity.this.sendBroadcast(intent2);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    MainExcerciseActivity.this.restScreen.setVisibility(View.GONE);
                    Intent intent3 = new Intent(MainExcerciseActivity.this, CompletionExcActivity.class);
                    intent3.putExtra("day", MainExcerciseActivity.this.day);
                    int i2 = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < MainExcerciseActivity.this.n.size(); i4++) {
                        i2 += ((WorkoutData) MainExcerciseActivity.this.n.get(i4)).getExcCycles();
                        i3 = i3 + ((WorkoutData) MainExcerciseActivity.this.n.get(i4)).getImageIdList().length + Constants.REST_TIME;
                    }
                    intent3.putExtra("totalExc", i2);
                    intent3.putExtra("totalTime", i3);
                    MainExcerciseActivity.this.startActivity(intent3);
                    if (MainExcerciseActivity.this.y.isLoaded()) {
                        MainExcerciseActivity.this.y.show();
                    }
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append("excCouner: ");
                sb5.append(MainExcerciseActivity.this.excCouner);
                Log.i(str, sb5.toString());
                MainExcerciseActivity mainExcerciseActivity8 = MainExcerciseActivity.this;
                mainExcerciseActivity8.k = 1.0f;
                mainExcerciseActivity8.mainExcCounter = 1;
            }


            public void onTick(long j) {
                int i;
                int i2;
                String str;
                MyApplication absWomenApplication;
                String upperCase;
                TextView m = null;
                String sb = null;
                TextView g = null;
                String valueOf = null;
                String str2 = " ";
                String str3 = "";
                StringBuilder sb2 = new StringBuilder();
                String str4 = "millisUntilFinished: ";
                sb2.append(str4);
                sb2.append(j);
                Log.i("millisUntilFinished", sb2.toString());
                try {
                    if (((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length <= 2) {
                        if (this.b <= ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) {
                            g = MainExcerciseActivity.this.tvProgress;
                            StringBuilder sb3 = new StringBuilder();
                            int i3 = this.b;
                            this.b = i3 + 1;
                            sb3.append(i3);
                            sb3.append(str3);
                            valueOf = String.valueOf(sb3.toString());
                        }
                        RoundCornerProgressBar h = MainExcerciseActivity.this.progressbar;
                        float f2 = this.f1448a;
                        this.f1448a = 1.0f + f2;
                        h.setProgress(f2);
                        MainExcerciseActivity.this.s.setProgress((int) this.f1448a);
                        TextView i4 = MainExcerciseActivity.this.timerTop;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(this.b);
                        sb4.append(str3);
                        i4.setText(sb4.toString());
                        upperCase = ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", str2).toUpperCase();
                        MainExcerciseActivity.this.excName.setText(upperCase);
                        if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                        }
                    } else if (this.f1448a == 1.0f) {
                        g = MainExcerciseActivity.this.tvProgress;
                        valueOf = "1";
                    } else {
                        if (this.f1448a % ((float) ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length) == 0.0f) {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(str4);
                            sb5.append(this.f1448a % ((float) ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length));
                            Log.i("mainExce", sb5.toString());
                            g = MainExcerciseActivity.this.tvProgress;
                            StringBuilder sb6 = new StringBuilder();
                            int i5 = this.b;
                            this.b = i5 + 1;
                            sb6.append(i5);
                            sb6.append(str3);
                            valueOf = String.valueOf(sb6.toString());
                        }
                        RoundCornerProgressBar h2 = MainExcerciseActivity.this.progressbar;
                        float f22 = this.f1448a;
                        this.f1448a = 1.0f + f22;
                        h2.setProgress(f22);
                        MainExcerciseActivity.this.s.setProgress((int) this.f1448a);
                        TextView i42 = MainExcerciseActivity.this.timerTop;
                        StringBuilder sb42 = new StringBuilder();
                        sb42.append(this.b);
                        sb42.append(str3);
                        i42.setText(sb42.toString());
                        upperCase = ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", str2).toUpperCase();
                        MainExcerciseActivity.this.excName.setText(upperCase);
                        if (!upperCase.equalsIgnoreCase("standing bicycle crunches") && !upperCase.equalsIgnoreCase("clapping crunches") && !upperCase.equalsIgnoreCase("heel touch") && !upperCase.equalsIgnoreCase("bicycle crunches") && !upperCase.equalsIgnoreCase("flutter kicks") && !upperCase.equalsIgnoreCase("adductor strect in standing") && !upperCase.equalsIgnoreCase("lunges") && !upperCase.equalsIgnoreCase("side lunges")) {
                            if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                                MainExcerciseActivity.this.eachSideTextView.setText(str2);
                                String str5 = "/";
                                if (!upperCase.equalsIgnoreCase("plank")) {
                                    m = MainExcerciseActivity.this.tvProgressMax;
                                    StringBuilder sb7 = new StringBuilder();
                                    sb7.append(str5);
                                    sb7.append(((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles());
                                    sb7.append("s");
                                    sb = sb7.toString();
                                } else {
                                    m = MainExcerciseActivity.this.tvProgressMax;
                                    StringBuilder sb8 = new StringBuilder();
                                    sb8.append(str5);
                                    sb8.append(((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles());
                                    sb = sb8.toString();
                                }
                                m.setText(sb);
                                MainExcerciseActivity.this.s1 = j;
                                MainExcerciseActivity.this.mainExcCounter = this.b;
                                MainExcerciseActivity.this.k = this.f1448a;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append("onTick: ");
                                sb9.append(this.b);
                                sb9.append("      ");
                                sb9.append(this.c);
                                sb9.append("           ");
                                sb9.append(i3);
                                Log.d("mycount", sb9.toString());
                                i = this.c;
                                i2 = this.b;
                                if (i != i2) {
                                    MainExcerciseActivity.this.x.playEarCorn();
                                    return;
                                }
                                this.c = i2;
                                int i6 = this.c;
                                if (i6 != 1) {
                                    MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                                    if (i6 <= ((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCouner)).getExcCycles()) {
                                        absWomenApplication = MainExcerciseActivity.this.x;
                                        StringBuilder sb10 = new StringBuilder();
                                        sb10.append(str3);
                                        sb10.append(this.c - 1);
                                        str = sb10.toString();
                                    } else {
                                        absWomenApplication = MainExcerciseActivity.this.x;
                                        StringBuilder sb11 = new StringBuilder();
                                        sb11.append(str3);
                                        MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                                        sb11.append(((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCouner)).getExcCycles());
                                        str = sb11.toString();
                                    }
                                    absWomenApplication.speak(str);
                                    return;
                                }
                                return;
                            }
                        }
                        TextView l = MainExcerciseActivity.this.eachSideTextView;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("Each Side x ");
                        sb12.append(((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles() / 2);
                        l.setText(sb12.toString());
                        String str52 = "/";
                        if (!upperCase.equalsIgnoreCase("plank")) {
                        }
                        m.setText(sb);
                        MainExcerciseActivity.this.s1 = j;
                        MainExcerciseActivity.this.mainExcCounter = this.b;
                        MainExcerciseActivity.this.k = this.f1448a;
                        StringBuilder sb92 = new StringBuilder();
                        sb92.append("onTick: ");
                        sb92.append(this.b);
                        sb92.append("      ");
                        sb92.append(this.c);
                        sb92.append("           ");
                        sb92.append(i3);
                        Log.d("mycount", sb92.toString());
                        i = this.c;
                        i2 = this.b;
                        if (i != i2) {
                        }
                    }
                    g.setText(valueOf);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RoundCornerProgressBar h22 = MainExcerciseActivity.this.progressbar;
                float f222 = this.f1448a;
                this.f1448a = 1.0f + f222;
                h22.setProgress(f222);
                MainExcerciseActivity.this.s.setProgress((int) this.f1448a);
                TextView i422 = MainExcerciseActivity.this.timerTop;
                StringBuilder sb422 = new StringBuilder();
                sb422.append(this.b);
                sb422.append(str3);
                i422.setText(sb422.toString());
                try {
                    upperCase = ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcName().replace("_", str2).toUpperCase();
                    MainExcerciseActivity.this.excName.setText(upperCase);
                    if (!upperCase.equalsIgnoreCase("swimmer and superman")) {
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        };
        this.excersiseTimer = r2.start();
    }


    public void requestNewInterstitial() {
        this.y.loadAd(this.A);
    }


    public void requestNewInterstitial1() {
        this.z.loadAd(this.B);
    }

    private void setAdmodAds() {
        this.y = new InterstitialAd(this);
        this.y.setAdUnitId(getString(R.string.interstitial_key));
        this.z = new InterstitialAd(this);
        this.z.setAdUnitId(getString(R.string.interstitial_key));
        this.A = new Builder().build();
        this.y.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MainExcerciseActivity.this.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        this.A = new Builder().build();
        this.B = new Builder().build();
        this.z.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                MainExcerciseActivity.this.requestNewInterstitial1();
            }
        });
        requestNewInterstitial1();
    }

    public void a(long j) {
        String upperCase = ((WorkoutData) this.n.get(this.excCouner)).getExcName().replace("_", " ").toUpperCase();
        this.o.setText(upperCase);
        TextView textView = this.q;
        StringBuilder sb = new StringBuilder();
        sb.append("x");
        sb.append(((WorkoutData) this.n.get(this.excCouner)).getExcCycles());
        textView.setText(sb.toString());
        this.p.reset();
        for (int addImageFrame : ((WorkoutData) this.n.get(this.excCouner)).getImageIdList()) {
            this.p.addImageFrame(addImageFrame);
        }
        this.p.startAnimation();
        this.restTimerprogress.setMax((int) (this.m / 1000));
        if (j == this.m) {
            this.x.speak("Take rest");
            MyApplication absWomenApplication = this.x;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("the next exercise is ");
            sb2.append(upperCase);
            absWomenApplication.speak(sb2.toString());
        }
        CountDownTimer r2 = new CountDownTimer(j, 1000) {
            public void onFinish() {
                MainExcerciseActivity.this.restScreen.setVisibility(8);
                MainExcerciseActivity.this.F = Boolean.valueOf(false);
                try {
                    long length = (long) (((((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length > 2 ? ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getImageIdList().length * ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles() : ((WorkoutData) MainExcerciseActivity.this.n.get(MainExcerciseActivity.this.excCouner)).getExcCycles()) + 1) * 1000);
                    MainExcerciseActivity.this.l = length;
                    MainExcerciseActivity.this.pauseMainExcersise.setVisibility(0);
                    MainExcerciseActivity.this.resumeMainExcersise.setVisibility(8);
                    MainExcerciseActivity.this.mainExcTimer(length, MainExcerciseActivity.this.mainExcCounter, MainExcerciseActivity.this.k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @SuppressLint({"SetTextI18n"})
            public void onTick(long j) {
                long j2 = (j - 1000) / 1000;
                MainExcerciseActivity.this.restTimerprogress.setProgress((int) j2);
                TextView s = MainExcerciseActivity.this.countRestTimer;
                StringBuilder sb = new StringBuilder();
                sb.append(j2);
                sb.append("");
                s.setText(sb.toString());
                MainExcerciseActivity.this.s1 = j;
                if (j2 < 4) {
                    if (j2 == 3) {
                        MainExcerciseActivity.this.x.speak("three ");
                    }
                    if (j2 == 2) {
                        MainExcerciseActivity.this.x.speak("two ");
                    }
                    if (j2 == 1) {
                        MainExcerciseActivity.this.x.speak("one ");
                    }
                    if (j2 == 0 && !MainExcerciseActivity.this.F.booleanValue()) {
                        MainExcerciseActivity.this.x.speak("start");
                        MainExcerciseActivity.this.F = Boolean.valueOf(true);
                    }
                } else if (!MainExcerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExcerciseActivity.this.x.playEarCorn();
                }
            }
        };
        this.restTimer = r2.start();
    }

    public  void a(View view) {
        if (this.x.isSpeaking().booleanValue()) {
            this.x.stop();
        }
        if (this.excCouner > 0) {
            this.s.setProgress(0);
            this.excersiseTimer.cancel();
            this.excCouner--;
            this.progressbar.setMax((float) ((WorkoutData) this.n.get(this.excCouner)).getExcCycles());
            this.tvProgressMax.setText(String.valueOf(((WorkoutData) this.n.get(this.excCouner)).getExcCycles()));
            long calculateExTime = calculateExTime(this.excCouner);
            this.l = calculateExTime;
            this.pauseMainExcersise.setVisibility(0);
            this.resumeMainExcersise.setVisibility(8);
            double excDayProgress = (double) this.databaseOperations.getExcDayProgress(this.day);
            double size = (double) ((float) this.n.size());
            Double.isNaN(size);
            double d = 100.0d / size;
            Double.isNaN(excDayProgress);
            this.progress = excDayProgress - d;
            dataBaseProgressUpdate(this.progress);
            mainExcTimer(calculateExTime, 1, 1.0f);
            return;
        }
        Toast.makeText(this.x, "This is first exercise", 0).show();
    }

    public  void a(MaterialDialog materialDialog, DialogAction dialogAction) {
        try {
            materialDialog.dismiss();
            if (this.readyToGoTimer != null) {
                this.readyToGoTimer.cancel();
            }
            if (this.excersiseTimer != null) {
                this.excersiseTimer.cancel();
            }
            if (this.restTimer != null) {
                this.restTimer.cancel();
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(603979776);
            startActivity(intent);
            onSuperBackPressed();
            if (this.z.isLoaded()) {
                this.z.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    public void b() {
        new MaterialDialog.Builder(this).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(this, R.color.black)).content((CharSequence) "Would you like to quit the workout?").contentColor(ContextCompat.getColor(this, R.color.textColor)).positiveText((CharSequence) "Yes").positiveColor(ContextCompat.getColor(this, R.color.colorAccent)).onPositive(new g(this)).negativeText((CharSequence) "No").negativeColor(ContextCompat.getColor(this, R.color.textColor)).onNegative(h.f7a).show();
    }

    public long calculateExTime(int i2) {
        return ((WorkoutData) this.n.get(i2)).getImageIdList().length > 2 ? (long) (((((WorkoutData) this.n.get(i2)).getImageIdList().length * ((WorkoutData) this.n.get(i2)).getExcCycles()) + 1) * 1000) : (long) ((((WorkoutData) this.n.get(i2)).getExcCycles() + 1) * 1000);
    }

    public void dataBaseProgressUpdate(double d) {
        this.databaseOperations.insertExcDayData(this.day, (float) d);
        this.databaseOperations.insertExcCounter(this.day, this.excCouner);
        StringBuilder sb = new StringBuilder();
        sb.append(this.excCouner);
        sb.append("");
        Log.d("CounterValue", sb.toString());
        try {
            Intent intent = new Intent(AppUtilss.WORKOUT_BROADCAST_FILTER);
            intent.putExtra(AppUtilss.KEY_PROGRESS, d);
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        b();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        setContentView((int) R.layout.mainexcercise_layout);
        this.databaseOperations = new DatabaseOperations(this);
        ((Toolbar) findViewById(R.id.mtoolbar)).setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainExcerciseActivity.this.finish();
            }
        });
        this.n = (ArrayList) getIntent().getExtras().getSerializable("workoutDataList");
        setAdmodAds();
        Bundle extras = getIntent().getExtras();
        extras.getClass();
        this.day = extras.getString("day");
        this.prog = (double) getIntent().getExtras().getFloat("progress");
        this.x = MyApplication.getInstance();
        this.excCouner = ((int) this.prog) / (100 / this.n.size());
        this.progressbar = (RoundCornerProgressBar) findViewById(R.id.progress_one);
        this.animImageFull = (FAImageView) findViewById(R.id.animImageFull);
        this.tvProgress = (TextView) findViewById(R.id.tv_progress);
        this.tvProgressMax = (TextView) findViewById(R.id.tv_progress_max);
        this.timerTop = (TextView) findViewById(R.id.timerTop);
        this.restScreen = (CoordinatorLayout) findViewById(R.id.restScreen);
        this.excName = (TextView) findViewById(R.id.excName);
        this.pauseMainExcersise = (ImageView) findViewById(R.id.pauseMainExcersise);
        this.resumeMainExcersise = (ImageView) findViewById(R.id.resumeMainExcersise);
        this.skip = (TextView) findViewById(R.id.skip);
        this.skipRestTime = (TextView) findViewById(R.id.skipRestTime);
        this.timerprogress = (ProgressBar) findViewById(R.id.timer);
        this.volume = (ImageView) findViewById(R.id.volumeOption);
        this.count = (TextView) findViewById(R.id.counting);
        this.playPause = (ImageView) findViewById(R.id.floatingPlay);
        this.eachSideTextView = (TextView) findViewById(R.id.eachSideTextView);
        this.excDescInReadyToGo = (TextView) findViewById(R.id.excDescInReadyToGo);
        this.excNameInReadyToGo = (TextView) findViewById(R.id.excNameInReadyToGo);
        this.skip_exc = (ImageView) findViewById(R.id.skip_exc);
        this.previous_exc = (ImageView) findViewById(R.id.previous_exc);
        this.pauseRestTime = (ImageView) findViewById(R.id.pauseRestTime);
        this.resumRestTime = (ImageView) findViewById(R.id.resumeRestTime);
        this.restTimerprogress = (ProgressBar) findViewById(R.id.rest_timer);
        this.countRestTimer = (TextView) findViewById(R.id.rest_counting);
        this.o = (TextView) findViewById(R.id.nextExerciseName);
        this.q = (TextView) findViewById(R.id.nextExerciseCycles);
        this.p = (FAImageView) findViewById(R.id.nextExercisAnimation);
        this.D = (Toolbar) findViewById(R.id.toolbarrest);
        this.r = (LinearLayout) findViewById(R.id.hLayoutprogress);
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        Gson gson = new Gson();
        String string = sharedPreferences.getString("json_string", "");
        if (string.isEmpty() || !isConnectedToInternet()) {
            this.D.setVisibility(View.GONE);
        } else {


        }


            findViewById(R.id.toolbarrest).setVisibility(View.GONE);
//            LayoutParams layoutParams = (LayoutParams) findViewById(R.id.collapsingLayout).getLayoutParams();
//            layoutParams.setScrollFlags(0);
//            findViewById(R.id.collapsingLayout).setLayoutParams(layoutParams);


        this.readytogo_layout = (CoordinatorLayout) findViewById(R.id.readytogo_layout);
        this.progressbar.setMax(10.0f);
        this.animImageFull.setInterval(1000);
        this.animImageFull.setLoop(true);
        this.animImageFull.reset();
        try {
            for (int addImageFrame : ((WorkoutData) this.n.get(this.excCouner)).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame);
            }
        } catch (IndexOutOfBoundsException unused) {
            this.excCouner = this.n.size() - 1;
            for (int addImageFrame2 : ((WorkoutData) this.n.get(this.excCouner)).getImageIdList()) {
                this.animImageFull.addImageFrame(addImageFrame2);
            }
        }
        this.animImageFull.startAnimation();
        this.skipRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExcerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExcerciseActivity.this.x.stop();
                }
                MainExcerciseActivity.this.restTimer.cancel();
                MainExcerciseActivity.this.restTimer.onFinish();
                MainExcerciseActivity.this.pauseRestTime.setVisibility(0);
                MainExcerciseActivity.this.resumRestTime.setVisibility(8);
            }
        });
        this.skip.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExcerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExcerciseActivity.this.x.stop();
                }
                MainExcerciseActivity.this.readyToGoTimer.cancel();
                MainExcerciseActivity.this.readyToGoTimer.onFinish();
            }
        });
        this.skip_exc.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExcerciseActivity.this.mainExcCounter);
                Log.i("mainexc", sb.toString());
                if (MainExcerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExcerciseActivity.this.x.stop();
                }
                MainExcerciseActivity.this.excersiseTimer.cancel();
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                ProgressBar progressBar = mainExcerciseActivity.s;
                int length = ((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCouner)).getImageIdList().length;
                MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                progressBar.setProgress(length * ((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCouner)).getExcCycles());
                MainExcerciseActivity.this.excersiseTimer.onFinish();
            }
        });
        this.previous_exc.setOnClickListener(new f(this));
        this.playPause.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MainExcerciseActivity.this.i % 2 == 0) {
                    MainExcerciseActivity.this.playPause.setBackgroundResource(R.drawable.play);
                    MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                    mainExcerciseActivity.C = true;
                    mainExcerciseActivity.readyToGoTimer.cancel();
                } else {
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    mainExcerciseActivity2.C = false;
                    mainExcerciseActivity2.playPause.setBackgroundResource(R.drawable.pause);
                    MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                    mainExcerciseActivity3.readyToGoFun(mainExcerciseActivity3.s1);
                }
                MainExcerciseActivity.this.i = MainExcerciseActivity.this.i + 1;
            }
        });
        this.pauseRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainExcerciseActivity.this.pauseRestTime.setVisibility(8);
                MainExcerciseActivity.this.resumRestTime.setVisibility(0);
                MainExcerciseActivity.this.restTimer.cancel();
            }
        });
        this.resumRestTime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MainExcerciseActivity.this.pauseRestTime.setVisibility(0);
                MainExcerciseActivity.this.resumRestTime.setVisibility(8);
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.a(mainExcerciseActivity.s1);
            }
        });
        this.pauseMainExcersise.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExcerciseActivity.this.mainExcCounter);
                sb.append("mainExcProgress ");
                sb.append(MainExcerciseActivity.this.k);
                Log.i("pauseMainExcersise", sb.toString());
                MainExcerciseActivity.this.pauseMainExcersise.setVisibility(8);
                MainExcerciseActivity.this.resumeMainExcersise.setVisibility(0);
                MainExcerciseActivity.this.excersiseTimer.cancel();
                MainExcerciseActivity.this.animImageFull.stopAnimation();
            }
        });
        this.resumeMainExcersise.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("mainExcCounter");
                sb.append(MainExcerciseActivity.this.mainExcCounter);
                sb.append("mainExcProgress ");
                sb.append(MainExcerciseActivity.this.k);
                Log.i("resumeMainExcersise", sb.toString());
                MainExcerciseActivity.this.pauseMainExcersise.setVisibility(0);
                MainExcerciseActivity.this.resumeMainExcersise.setVisibility(8);
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                mainExcerciseActivity.mainExcTimer(mainExcerciseActivity.s1 - 1000, MainExcerciseActivity.this.mainExcCounter, MainExcerciseActivity.this.k);
            }
        });
        readyToGoFun(10000);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        for (int i2 = 0; i2 < this.n.size(); i2++) {
            this.s = new ProgressBar(this, null, 16842872);
            layoutParams2.rightMargin = 2;
            layoutParams2.leftMargin = 2;
            this.s.setPadding(0, 0, 0, -8);
            this.s.setLayoutParams(layoutParams2);
            this.s.setId(i2);
            this.s.setScaleY(2.5f);
            this.r.addView(this.s);
        }
        for (int i3 = 0; i3 < this.excCouner; i3++) {
            this.s = (ProgressBar) this.r.findViewById(i3);
            this.s.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
            this.s.setMax(((WorkoutData) this.n.get(this.excCouner)).getImageIdList().length * ((WorkoutData) this.n.get(this.excCouner)).getExcCycles());
            this.s.setProgress(((WorkoutData) this.n.get(this.excCouner)).getImageIdList().length * ((WorkoutData) this.n.get(this.excCouner)).getExcCycles());
        }
        getScreenHeightWidth();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    public void onPause() {
        super.onPause();
        CountDownTimer countDownTimer = this.readyToGoTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = this.excersiseTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        CountDownTimer countDownTimer3 = this.restTimer;
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
        }
        if (!this.C) {
            this.i--;
        }
        this.playPause.setBackgroundResource(R.drawable.play);
        this.resumeMainExcersise.setVisibility(0);
        this.pauseMainExcersise.setVisibility(8);
        this.resumRestTime.setVisibility(0);
        this.pauseRestTime.setVisibility(8);
        this.animImageFull.stopAnimation();
    }

    public void onResume() {
        super.onResume();
        this.pauseMainExcersise.setVisibility(8);
        this.resumeMainExcersise.setVisibility(0);
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }

    public void readyToGoFun(long j) {
        this.excDescInReadyToGo.setText(((WorkoutData) this.n.get(this.excCouner)).getExcDescResId());
        String upperCase = ((WorkoutData) this.n.get(this.excCouner)).getExcName().replace("_", " ").toUpperCase();
        this.excNameInReadyToGo.setText(upperCase);
        String lowerCase = upperCase.toLowerCase();
        if (j == 10000) {
            this.x.speak("ready to go ");
            MyApplication absWomenApplication = this.x;
            StringBuilder sb = new StringBuilder();
            sb.append("the exercise is ");
            sb.append(lowerCase);
            absWomenApplication.speak(sb.toString());
        }
        this.timerprogress.setMax(10);
        CountDownTimer r2 = new CountDownTimer(j, 1000) {
            public void onFinish() {
                int i;
                Log.i("readyToGoTimer", "onFinish: ");
                MainExcerciseActivity.this.G = Boolean.valueOf(false);
                MainExcerciseActivity.this.timerprogress.setProgress(0);
                MainExcerciseActivity.this.readytogo_layout.setVisibility(8);
                MainExcerciseActivity mainExcerciseActivity = MainExcerciseActivity.this;
                if (((WorkoutData) mainExcerciseActivity.n.get(mainExcerciseActivity.excCouner)).getImageIdList().length > 2) {
                    MainExcerciseActivity mainExcerciseActivity2 = MainExcerciseActivity.this;
                    int length = ((WorkoutData) mainExcerciseActivity2.n.get(mainExcerciseActivity2.excCouner)).getImageIdList().length;
                    MainExcerciseActivity mainExcerciseActivity3 = MainExcerciseActivity.this;
                    i = length * ((WorkoutData) mainExcerciseActivity3.n.get(mainExcerciseActivity3.excCouner)).getExcCycles();
                } else {
                    MainExcerciseActivity mainExcerciseActivity4 = MainExcerciseActivity.this;
                    i = ((WorkoutData) mainExcerciseActivity4.n.get(mainExcerciseActivity4.excCouner)).getExcCycles();
                }
                long j = (long) ((i + 1) * 1000);
                MainExcerciseActivity mainExcerciseActivity5 = MainExcerciseActivity.this;
                mainExcerciseActivity5.l = j;
                mainExcerciseActivity5.pauseMainExcersise.setVisibility(0);
                MainExcerciseActivity.this.resumeMainExcersise.setVisibility(8);
                MainExcerciseActivity mainExcerciseActivity6 = MainExcerciseActivity.this;
                mainExcerciseActivity6.mainExcTimer(j, mainExcerciseActivity6.mainExcCounter, MainExcerciseActivity.this.k);
            }

            public void onTick(long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("progressbar: ");
                sb.append(((int) j) / 1000);
                Log.i("readyToGoTimer", sb.toString());
                long j2 = j - 1000;
                MainExcerciseActivity.this.timerprogress.setProgress(((int) j2) / 1000);
                TextView u = MainExcerciseActivity.this.count;
                StringBuilder sb2 = new StringBuilder();
                long j3 = j2 / 1000;
                sb2.append(j3);
                sb2.append("");
                u.setText(sb2.toString());
                MainExcerciseActivity.this.s1 = j;
                if (j3 < 4) {
                    if (j3 == 3) {
                        MainExcerciseActivity.this.x.speak("three ");
                    }
                    if (j3 == 2) {
                        MainExcerciseActivity.this.x.speak("two ");
                    }
                    if (j3 == 1) {
                        MainExcerciseActivity.this.x.speak("one ");
                    }
                    if (j3 == 0 && !MainExcerciseActivity.this.G.booleanValue()) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("onTick: ");
                        sb3.append(j);
                        Log.d("TAG", sb3.toString());
                        MainExcerciseActivity.this.x.speak("let's start ");
                        MainExcerciseActivity.this.G = Boolean.valueOf(true);
                    }
                } else if (!MainExcerciseActivity.this.x.isSpeaking().booleanValue()) {
                    MainExcerciseActivity.this.x.playEarCorn();
                }
            }
        };
        this.readyToGoTimer = r2.start();
    }
}
