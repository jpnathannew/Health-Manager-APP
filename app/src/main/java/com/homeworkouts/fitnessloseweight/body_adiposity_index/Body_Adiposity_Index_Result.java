package com.homeworkouts.fitnessloseweight.body_adiposity_index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.general.MyApplication;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;


public class Body_Adiposity_Index_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String bai;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    RelativeLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TextView tv_ans_healthrisk;
    TextView tv_whr_chart;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_whr);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.bai = this.extras.getString("bai");
        this.rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.tv_ans_healthrisk = (TextView) findViewById(R.id.tv_ans_healthrisk);
        this.tv_whr_chart = (TextView) findViewById(R.id.tv_whr_chart);
        this.tv_ans_healthrisk.setVisibility(8);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        this.tv_ans_healthrisk.setTypeface(this.typefaceManager.getLight());
        this.tv_whr_chart.setTypeface(this.typefaceManager.getBold());
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient7);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.bai_is));
        sb.append(" ");
        sb.append(this.bai);
        sb.append("%");
        textView.setText(sb.toString());
        this.tv_whr_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Body_Adiposity_Index_Result.this.showIntertitial();
                    return;
                }
                Body_Adiposity_Index_Result.this.startActivity(new Intent(Body_Adiposity_Index_Result.this, Body_Adiposity_Index_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Body_Adiposity_Index_Result.this.onBackPressed();
            }
        });
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            startActivity(new Intent(this, Body_Adiposity_Index_Chart.class));
        } else if (MyApplication.interstitial == null || !MyApplication.interstitial.isLoaded()) {
            if (!MyApplication.interstitial.isLoading()) {
                ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                    public void onNoResponse() {
                    }

                    public void onResponseObtained() {
                        MyApplication.interstitial.loadAd(new Builder().build());
                    }
                });
            }
            startActivity(new Intent(this, Body_Adiposity_Index_Chart.class));
        } else {
            MyApplication.interstitial.show();
        }
    }


    public void onResume() {
        super.onResume();
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue() && MyApplication.interstitial != null && !MyApplication.interstitial.isLoaded() && !MyApplication.interstitial.isLoading()) {
            ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                public void onNoResponse() {
                }

                public void onResponseObtained() {
                    MyApplication.interstitial.loadAd(new Builder().build());
                }
            });
        }
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            MyApplication.interstitial.setAdListener(new AdListener() {
                public void onAdClosed() {
                    super.onAdClosed();
                    MyApplication.interstitial.loadAd(new Builder().build());
                    Body_Adiposity_Index_Result.this.startActivity(new Intent(Body_Adiposity_Index_Result.this, Body_Adiposity_Index_Chart.class));
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    if (MyApplication.interstitial != null && !MyApplication.interstitial.isLoading()) {
                        ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                            public void onNoResponse() {
                            }

                            public void onResponseObtained() {
                                MyApplication.interstitial.loadAd(new Builder().build());
                            }
                        });
                    }
                }
            });
        }
    }
}
