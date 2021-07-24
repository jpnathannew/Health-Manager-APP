package com.homeworkouts.fitnessloseweight.sugar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.general.MyApplication;
import com.homeworkouts.fitnessloseweight.utils.ConnectionDetector;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;


public class Sugar_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdRequest adRequest;
    AdView adView;
    ConnectionDetector connectionDetector;
    Bundle extras;
    Double final_bloodsugar_val;
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bloodsugar;
    TextView tv_bloodsugar_chart;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_sugar);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.connectionDetector = new ConnectionDetector(this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_ans_bloodsugar = (TextView) findViewById(R.id.tv_ans_bloodsugar);
        this.tv_bloodsugar_chart = (TextView) findViewById(R.id.tv_bloodsugar_chart);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_bloodsugar.setTypeface(this.typefaceManager.getLight());
        this.tv_bloodsugar_chart.setTypeface(this.typefaceManager.getBold());
        this.extras = getIntent().getExtras();
        this.final_bloodsugar_val = Double.valueOf(this.extras.getDouble("final_bloodsugar_val"));
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        if (this.final_bloodsugar_val.doubleValue() < 0.0d) {
            TextView textView = this.tv_ans_bloodsugar;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Blood_Sugar));
            sb.append(" : 0 ]");
            sb.append(getString(R.string.mmol_a));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.tv_ans_bloodsugar;
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getString(R.string.Blood_Sugar));
            sb3.append(" : %.2f");
            sb2.append(String.format(sb3.toString(), new Object[]{this.final_bloodsugar_val}));
            sb2.append(" ");
            sb2.append(getString(R.string.mmol_a));
            textView2.setText(sb2.toString());
        }
        this.tv_bloodsugar_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Sugar_Result.this.showIntertitial();
                    return;
                }
                Sugar_Result.this.startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
            }
        });
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Sugar_Result.this.onBackPressed();
            }
        });
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            startActivity(new Intent(this, Sugar_Chart.class));
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
            startActivity(new Intent(this, Sugar_Chart.class));
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
                    Sugar_Result.this.startActivity(new Intent(Sugar_Result.this, Sugar_Chart.class));
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
