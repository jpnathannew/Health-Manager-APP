package com.homeworkouts.fitnessloseweight.blood_pressure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;


public class BloodPressure_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String diastolic_result;
    Float diastolic_val;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    String syastolic_result;
    Float systolic_val;
    TextView tv_bloodpressure_chart;
    TextView tv_diastolic_ans;
    TextView tv_systolic_ans;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bloodpressure);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.tv_systolic_ans = (TextView) findViewById(R.id.tv_systolic_ans);
        this.tv_diastolic_ans = (TextView) findViewById(R.id.tv_diastolic_ans);
        this.tv_bloodpressure_chart = (TextView) findViewById(R.id.tv_bloodpressure_chart);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_bloodpressure_chart.setTypeface(this.typefaceManager.getBold());
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.systolic_val = Float.valueOf(this.extras.getFloat("systolic_val"));
        this.diastolic_val = Float.valueOf(this.extras.getFloat("diastolic_val"));
        this.tv_systolic_ans.setTypeface(this.typefaceManager.getLight());
        this.tv_diastolic_ans.setTypeface(this.typefaceManager.getLight());
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BloodPressure_Result.this.onBackPressed();
            }
        });
        if (this.systolic_val.floatValue() < 120.0f) {
            this.syastolic_result = getString(R.string.Normal_Blood_Pressure);
        } else if (this.systolic_val.floatValue() >= 120.0f && this.systolic_val.floatValue() <= 139.0f) {
            this.syastolic_result = getString(R.string.Prehypertension);
        } else if (this.systolic_val.floatValue() >= 140.0f && this.systolic_val.floatValue() <= 159.0f) {
            this.syastolic_result = getString(R.string.High_Blood_Pressure_Stage_1);
        } else if (this.systolic_val.floatValue() >= 160.0f && this.systolic_val.floatValue() <= 180.0f) {
            this.syastolic_result = getString(R.string.High_Blood_Pressure_Stage_2);
        } else if (this.systolic_val.floatValue() > 180.0f) {
            this.syastolic_result = getString(R.string.Hypertensive_Crisis_Emergency_care_needed);
        }
        if (this.diastolic_val.floatValue() < 80.0f) {
            this.diastolic_result = getString(R.string.Normal_Blood_Pressure);
        } else if (this.diastolic_val.floatValue() >= 80.0f && this.diastolic_val.floatValue() <= 89.0f) {
            this.diastolic_result = getString(R.string.Prehypertension);
        } else if (this.diastolic_val.floatValue() >= 90.0f && this.diastolic_val.floatValue() <= 99.0f) {
            this.diastolic_result = getString(R.string.High_Blood_Pressure_Stage_1);
        } else if (this.diastolic_val.floatValue() >= 100.0f && this.diastolic_val.floatValue() <= 110.0f) {
            this.diastolic_result = getString(R.string.High_Blood_Pressure_Stage_2);
        } else if (this.diastolic_val.floatValue() > 110.0f) {
            this.diastolic_result = getString(R.string.Hypertensive_Crisis_Emergency_care_needed);
        }
        TextView textView = this.tv_systolic_ans;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.upper_result));
        sb.append("\n");
        sb.append(this.syastolic_result);
        textView.setText(sb.toString());
        TextView textView2 = this.tv_diastolic_ans;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.lower_result));
        sb2.append("\n");
        sb2.append(this.diastolic_result);
        textView2.setText(sb2.toString());
        this.tv_bloodpressure_chart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    BloodPressure_Result.this.showIntertitial();
                    return;
                }
                BloodPressure_Result.this.startActivity(new Intent(BloodPressure_Result.this, Bloodpressure_Chart.class));
            }
        });
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
                    BloodPressure_Result.this.startActivity(new Intent(BloodPressure_Result.this, Bloodpressure_Chart.class));
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

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            startActivity(new Intent(this, Bloodpressure_Chart.class));
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
            startActivity(new Intent(this, Bloodpressure_Chart.class));
        } else {
            MyApplication.interstitial.show();
        }
    }
}
