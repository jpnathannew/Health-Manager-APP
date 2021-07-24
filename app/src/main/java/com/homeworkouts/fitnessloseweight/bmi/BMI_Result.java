package com.homeworkouts.fitnessloseweight.bmi;

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
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;


public class BMI_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Integer age;
    Float bmi;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    String result;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmi;
    TextView tv_bmichart;
    TextView tv_current_state;
    TextView tv_recomended;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bmi);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.age = Integer.valueOf(this.extras.getInt("age"));
        this.bmi = Float.valueOf(this.extras.getFloat("bmi"));
        this.tv_ans_bmi = (TextView) findViewById(R.id.tv_ans_bmi);
        this.tv_current_state = (TextView) findViewById(R.id.tv_current_state);
        this.tv_recomended = (TextView) findViewById(R.id.tv_recomended);
        this.tv_bmichart = (TextView) findViewById(R.id.tv_bmichart);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_bmi.setTypeface(this.typefaceManager.getLight());
        this.tv_current_state.setTypeface(this.typefaceManager.getLight());
        this.tv_recomended.setTypeface(this.typefaceManager.getLight());
        this.tv_bmichart.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BMI_Result.this.onBackPressed();
            }
        });
        if (this.age.intValue() < 17) {
            if (this.bmi.floatValue() > 21.0f && this.bmi.floatValue() <= 26.0f) {
                this.result = getString(R.string.Overweight);
                TextView textView = this.tv_recomended;
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.Recommended_BMI_Value));
                sb.append(" : 15-20");
                textView.setText(sb.toString());
            } else if (this.bmi.floatValue() > 26.0f && this.bmi.floatValue() <= 34.0f) {
                this.result = getString(R.string.Obese);
                TextView textView2 = this.tv_recomended;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getString(R.string.Recommended_BMI_Value));
                sb2.append(" : 15-20");
                textView2.setText(sb2.toString());
            } else if (this.bmi.floatValue() > 34.0f) {
                this.result = getString(R.string.Extremely_Obese);
                TextView textView3 = this.tv_recomended;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getString(R.string.Recommended_BMI_Value));
                sb3.append(" : 15-20");
                textView3.setText(sb3.toString());
            } else if (this.bmi.floatValue() < 15.0f || this.bmi.floatValue() > 20.0f) {
                this.result = getString(R.string.Under_Weight);
                TextView textView4 = this.tv_recomended;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getString(R.string.Recommended_BMI_Value));
                sb4.append(" : 15-20");
                textView4.setText(sb4.toString());
            } else {
                this.result = getString(R.string.ok);
                TextView textView5 = this.tv_recomended;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(getString(R.string.Recommended_BMI_Value));
                sb5.append(" : 15-20");
                textView5.setText(sb5.toString());
            }
        } else if (this.age.intValue() < 17 || this.age.intValue() >= 35) {
            if (this.age.intValue() >= 35) {
                if (this.bmi.floatValue() > 27.0f && this.bmi.floatValue() <= 30.0f) {
                    this.result = getString(R.string.Overweight);
                    TextView textView6 = this.tv_recomended;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(getString(R.string.Recommended_BMI_Value));
                    sb6.append(" : 19-26");
                    textView6.setText(sb6.toString());
                } else if (this.bmi.floatValue() > 30.0f && this.bmi.floatValue() <= 40.0f) {
                    this.result = getString(R.string.Obese);
                    TextView textView7 = this.tv_recomended;
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(getString(R.string.Recommended_BMI_Value));
                    sb7.append(" : 19-26");
                    textView7.setText(sb7.toString());
                } else if (this.bmi.floatValue() > 40.0f) {
                    this.result = getString(R.string.Extremely_Obese);
                    TextView textView8 = this.tv_recomended;
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(getString(R.string.Recommended_BMI_Value));
                    sb8.append(" : 19-26");
                    textView8.setText(sb8.toString());
                } else if (this.bmi.floatValue() < 19.0f || this.bmi.floatValue() > 26.0f) {
                    this.result = getString(R.string.Under_Weight);
                    TextView textView9 = this.tv_recomended;
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(getString(R.string.Recommended_BMI_Value));
                    sb9.append(" : 19-26");
                    textView9.setText(sb9.toString());
                } else {
                    this.result = getString(R.string.ok);
                    TextView textView10 = this.tv_recomended;
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append(getString(R.string.Recommended_BMI_Value));
                    sb10.append(" : 19-26");
                    textView10.setText(sb10.toString());
                }
            }
        } else if (this.bmi.floatValue() > 25.0f && this.bmi.floatValue() <= 30.0f) {
            this.result = getString(R.string.Overweight);
            TextView textView11 = this.tv_recomended;
            StringBuilder sb11 = new StringBuilder();
            sb11.append(getString(R.string.Recommended_BMI_Value));
            sb11.append(" : 18-24");
            textView11.setText(sb11.toString());
        } else if (this.bmi.floatValue() > 30.0f && this.bmi.floatValue() <= 40.0f) {
            this.result = getString(R.string.Obese);
            TextView textView12 = this.tv_recomended;
            StringBuilder sb12 = new StringBuilder();
            sb12.append(getString(R.string.Recommended_BMI_Value));
            sb12.append(": 18-24");
            textView12.setText(sb12.toString());
        } else if (this.bmi.floatValue() > 40.0f) {
            this.result = getString(R.string.Extremely_Obese);
            TextView textView13 = this.tv_recomended;
            StringBuilder sb13 = new StringBuilder();
            sb13.append(getString(R.string.Recommended_BMI_Value));
            sb13.append(" : 18-24");
            textView13.setText(sb13.toString());
        } else if (this.bmi.floatValue() < 18.0f || this.bmi.floatValue() > 24.0f) {
            this.result = getString(R.string.Under_Weight);
            TextView textView14 = this.tv_recomended;
            StringBuilder sb14 = new StringBuilder();
            sb14.append(getString(R.string.Recommended_BMI_Value));
            sb14.append(" : 18-24");
            textView14.setText(sb14.toString());
        } else {
            this.result = getString(R.string.ok);
            TextView textView15 = this.tv_recomended;
            StringBuilder sb15 = new StringBuilder();
            sb15.append(getString(R.string.Recommended_BMI_Value));
            sb15.append(" : 18-24");
            textView15.setText(sb15.toString());
        }
        TextView textView16 = this.tv_ans_bmi;
        StringBuilder sb16 = new StringBuilder();
        sb16.append(getString(R.string.Your_BMI_is));
        sb16.append(" : ");
        sb16.append(String.format("%.02f", new Object[]{this.bmi}));
        textView16.setText(String.valueOf(sb16.toString()));
        this.tv_current_state.setText(this.result);
        this.tv_bmichart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    BMI_Result.this.showIntertitial();
                    return;
                }
                BMI_Result.this.startActivity(new Intent(BMI_Result.this, BMI_Chart.class));
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
                    BMI_Result.this.startActivity(new Intent(BMI_Result.this, BMI_Chart.class));
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
            startActivity(new Intent(this, BMI_Chart.class));
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
            startActivity(new Intent(this, BMI_Chart.class));
        } else {
            MyApplication.interstitial.show();
        }
    }
}
