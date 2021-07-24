package com.homeworkouts.fitnessloseweight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.homeworkouts.fitnessloseweight.adapters.WorkoutData;
import com.homeworkouts.fitnessloseweight.database.DatabaseOperations;
import com.homeworkouts.fitnessloseweight.fragment.Workout;
import com.homeworkouts.fitnessloseweight.utils.Constants;

import java.util.List;

public class CompletionExcActivity extends Activity {


    public LinearLayout adView;
    public boolean adloaded = false;
    public AdmobAds admobAdsObject = null;
    public LinearLayoutManager b;

    public TextView congrts_complete;
    public int d;
    public int daysCompletionConter = 0;
    public int e;
    public TextView f;
    public TextView g;
    public TextView h;
    public ImageView i;
    public Context j;
    public LayoutInflater k;
    public int l;
    public SharedPreferences launchDataPreferences;
    public int m;
    public int n = 0;

    public LinearLayout nativeAdContainer;

    public int shareConter = 0;
    public List<WorkoutData> workoutDataList;


    public void actionView(String str) {
        if (isConnectedToInternet()) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Please Check Internet Connection..", Toast.LENGTH_SHORT).show();
        }
    }




    private void getScreenHeightWidth() {
        this.j = this;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.l = displayMetrics.heightPixels;
        this.m = displayMetrics.widthPixels;
    }


    public boolean isConnectedToInternet() {
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


    public void shareAllApp() {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
//        StringBuilder sb = new StringBuilder();
//        sb.append("https://play.google.com/store/apps/details?id=" + getPackageName());
//        String sb2 = sb.toString();
//        StringBuilder sb3 = new StringBuilder();
//        sb3.append("Hi, i am using this amazing app ");
//        sb3.append(getResources().getString(R.string.app_name));
//        sb3.append(" and its awesome!\n\n This app keeps you fit and helps to get best results at home.\n\n Download the app here: \n");
//        sb3.append(sb2);
//        String sb4 = sb3.toString();
//        intent.putExtra("android.intent.extra.TEXT", sb4);
//        intent.putExtra("android.intent.extra.STREAM", sb4);
//        intent.setType("text/plain");
//        startActivity(intent);


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"I suggest this app for you : https://play.google.com/store/apps/details?id=" +  getPackageName());
        intent.setType("text/plain");
        startActivity(intent);

    }


    public void shareApp() {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.SEND");
//        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
//        StringBuilder sb = new StringBuilder();
//        sb.append("https://play.google.com/store/apps/details?id=");
//        sb.append(getApplicationContext().getPackageName());
//        String sb2 = sb.toString();
//        StringBuilder sb3 = new StringBuilder();
//        sb3.append("Hi, i have finished");
//        Bundle extras = getIntent().getExtras();
//        extras.getClass();
//        sb3.append(extras.getString("day"));
//        sb3.append("Workout of ");
//        sb3.append(getResources().getString(R.string.app_name));
//        sb3.append("\nPlease Download this app:\n ");
//        sb3.append(sb2);
//        sb3.append(" \n And start working out in your Home.");
//        String sb4 = sb3.toString();
//        intent.putExtra("android.intent.extra.TEXT", sb4);
//        intent.putExtra("android.intent.extra.STREAM", sb4);
//        intent.setType("text/plain");
//        startActivity(intent);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"start working out in your Home. : https://play.google.com/store/apps/details?id=" +  getPackageName());
        intent.setType("text/plain");
        startActivity(intent);


    }


    public void shareConfirmDialog() {
        final Dialog dialog = new Dialog(this.j, R.style.Theme_Dialog);
        try {
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.share_app_dailog);
            LayoutParams layoutParams = new LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.gravity = 17;
            dialog.getWindow().setAttributes(layoutParams);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        dialog.getWindow().setLayout(-1, -1);
        dialog.setCancelable(true);
        ((Button) dialog.findViewById(R.id.shareit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                CompletionExcActivity.this.shareAllApp();
            }
        });
        ((ImageView) dialog.findViewById(R.id.close_share)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        this.shareConter++;
    }




    public void a() {
        this.nativeAdContainer = (LinearLayout) findViewById(R.id.nativeAdContainer);
        this.k = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        this.admobAdsObject = new AdmobAds(this.j, this.nativeAdContainer, getString(R.string.g_native));
        this.admobAdsObject.refreshAd();
    }

    public void allCompletionDialogCreate() {
        String str = "OK";
        String str2 = "Cancel";
        new Builder(this).setTitle((CharSequence) "Congratulations !").setMessage((CharSequence) "You have completed all 30 days workouts. To achieve consistency, please repeat the exercise from day one.").setPositiveButton((CharSequence) str, (OnClickListener) null).setNegativeButton((CharSequence) str2, (OnClickListener) null).setPositiveButton((CharSequence) str, (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Editor edit = PreferenceManager.getDefaultSharedPreferences(CompletionExcActivity.this.getApplicationContext()).edit();
                edit.putBoolean("thirtyday", true);
                edit.apply();
                CompletionExcActivity.this.finish();
                Constants.TOTAL_DAYS = 30;
                Intent intent = new Intent(CompletionExcActivity.this, Workout.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
            }
        }).setNegativeButton((CharSequence) str2, (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(CompletionExcActivity.this, Workout.class);
                intent.addFlags(603979776);
                CompletionExcActivity.this.startActivity(intent);
                dialogInterface.dismiss();
            }
        }).show();
    }

    public void onBackPressed() {
        String str = "TAG";
        if (Constants.TOTAL_DAYS - this.daysCompletionConter == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("daysCompletionConter on backpress if");
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
            Log.d(str, sb.toString());
            allCompletionDialogCreate();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("daysCompletionConter on backpress else");
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
        Log.d(str, sb2.toString());
        if (this.shareConter == 0) {
            shareConfirmDialog();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(603979776);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onCreate(@Nullable Bundle bundle) {
        StringBuilder sb;
        String str;
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.exc_completion_layout);
        this.e = getIntent().getIntExtra("totalExc", 0);
        this.d = getIntent().getIntExtra("totalTime", 0);

        this.h = (TextView) findViewById(R.id.shareimage_Congrats);
        this.f = (TextView) findViewById(R.id.congrts_duration);
        this.g = (TextView) findViewById(R.id.congrts_ExNo);
        this.congrts_complete = (TextView) findViewById(R.id.congrts_complete);
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        Gson gson = new Gson();
        String str2 = "";
        String string = sharedPreferences.getString("json_string", str2);


        int i2 = this.d;
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        String str3 = "0";
        if (i3 < 10) {
            sb = new StringBuilder();
            sb.append(str3);
        } else {
            sb = new StringBuilder();
            sb.append(str2);
        }
        sb.append(i3);
        String sb2 = sb.toString();
        if (i4 < 10) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str3);
            sb3.append(i4);
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(i4);
            str = sb4.toString();
        }
        TextView textView = this.f;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb2);
        sb5.append(":");
        sb5.append(str);
        textView.setText(sb5.toString());
        TextView textView2 = this.g;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str2);
        sb6.append(this.e);
        textView2.setText(sb6.toString());
        TextView textView3 = this.congrts_complete;
        StringBuilder sb7 = new StringBuilder();
        Bundle extras = getIntent().getExtras();
        extras.getClass();
        sb7.append(extras.getString("day"));
        sb7.append(" Completed");
        textView3.setText(sb7.toString());
        this.h.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                CompletionExcActivity.this.shareApp();
            }
        });
        findViewById(R.id.closeimage_Congrats).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (CompletionExcActivity.this.shareConter == 0) {
                    CompletionExcActivity.this.shareConfirmDialog();
                } else {
                    CompletionExcActivity.this.onBackPressed();
                }
            }
        });


        getScreenHeightWidth();
        if (isConnectedToInternet()) {
            a();
        } else {


        }
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = new DatabaseOperations(this).getAllDaysProgress();
        for (int i5 = 0; i5 < Constants.TOTAL_DAYS; i5++) {
            if (((WorkoutData) this.workoutDataList.get(i5)).getProgress() >= 99.0f) {
                this.daysCompletionConter++;
            }
        }
        int i6 = this.daysCompletionConter;
        this.daysCompletionConter = i6 + (i6 / 3);
        if (this.daysCompletionConter % 5 == 0) {
            StringBuilder sb8 = new StringBuilder();
            sb8.append("day completion");
            sb8.append(this.daysCompletionConter);
            Log.i("debashish", sb8.toString());
            Bundle bundle2 = new Bundle();
            bundle2.putInt("open_time", this.daysCompletionConter);
            FirebaseAnalytics instance = FirebaseAnalytics.getInstance(this);
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Days_completed_beginner_");
            sb9.append(this.daysCompletionConter);
            instance.logEvent(sb9.toString(), bundle2);
        }
    }
}
