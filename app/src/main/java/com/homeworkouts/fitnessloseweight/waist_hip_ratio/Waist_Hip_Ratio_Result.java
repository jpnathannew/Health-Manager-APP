package com.homeworkouts.fitnessloseweight.waist_hip_ratio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
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


public class Waist_Hip_Ratio_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    String health_risk;
    ImageView iv_close;
    ImageView iv_imoji;
    RelativeLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TextView tv_ans_healthrisk;
    TextView tv_whr_chart;
    TypefaceManager typefaceManager;
    Double whr;


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
        this.rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.iv_imoji = (ImageView) findViewById(R.id.iv_imoji);
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient1);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.whr = Double.valueOf(this.extras.getString("whr"));
        this.health_risk = this.extras.getString("health_risk");
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.tv_ans_healthrisk = (TextView) findViewById(R.id.tv_ans_healthrisk);
        this.tv_whr_chart = (TextView) findViewById(R.id.tv_whr_chart);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        this.tv_ans_healthrisk.setTypeface(this.typefaceManager.getLight());
        this.tv_whr_chart.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.whr);
        Log.d("whr->", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(this.health_risk);
        Log.d("tv_ans_healthrisk->", sb2.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.whr_is));
        sb3.append(" ");
        sb3.append(this.whr);
        textView.setText(sb3.toString());
        TextView textView2 = this.tv_ans_healthrisk;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.health_risk));
        sb4.append(" ");
        sb4.append(this.health_risk);
        textView2.setText(sb4.toString());
        if (this.whr.doubleValue() <= 0.95d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_sad);
        } else if (this.whr.doubleValue() <= 0.95d || this.whr.doubleValue() >= 1.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_unhappy);
        } else {
            this.iv_imoji.setImageResource(R.drawable.imoji_smile);
        }
        this.tv_whr_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Waist_Hip_Ratio_Result.this.showIntertitial();
                    return;
                }
                Waist_Hip_Ratio_Result.this.startActivity(new Intent(Waist_Hip_Ratio_Result.this, Waist_Hip_Ratio_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Waist_Hip_Ratio_Result.this.onBackPressed();
            }
        });
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            startActivity(new Intent(this, Waist_Hip_Ratio_Chart.class));
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
            startActivity(new Intent(this, Waist_Hip_Ratio_Chart.class));
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
                    Waist_Hip_Ratio_Result.this.startActivity(new Intent(Waist_Hip_Ratio_Result.this, Waist_Hip_Ratio_Chart.class));
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
