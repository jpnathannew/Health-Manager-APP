package com.homeworkouts.fitnessloseweight.body_fat;

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


public class Bodyfat_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    int age;
    double bodyfat;
    Bundle extras;
    String gender;
    GlobalFunction globalFunction;
    ImageView iv_close;
    String result;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bodyfat;
    TextView tv_bodyfat;
    TextView tv_bodyfatchart;
    TextView tv_recomended;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bodyfat);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.extras = getIntent().getExtras();
        this.age = this.extras.getInt("age");
        this.gender = this.extras.getString("gender");
        this.bodyfat = (double) this.extras.getFloat("bodyfat");
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_bodyfat = (TextView) findViewById(R.id.tv_ans_bodyfat);
        this.tv_bodyfat = (TextView) findViewById(R.id.tv_bodyfat);
        this.tv_recomended = (TextView) findViewById(R.id.tv_recomended);
        this.tv_bodyfatchart = (TextView) findViewById(R.id.tv_bodyfatchart);
        this.tv_ans_bodyfat.setTypeface(this.typefaceManager.getLight());
        this.tv_recomended.setTypeface(this.typefaceManager.getLight());
        this.tv_bodyfat.setTypeface(this.typefaceManager.getLight());
        this.tv_bodyfatchart.setTypeface(this.typefaceManager.getLight());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Bodyfat_Result.this.onBackPressed();
            }
        });
        if (this.gender.equalsIgnoreCase(getString(R.string.Male))) {
            if (this.bodyfat < 10.0d) {
                this.result = getString(R.string.Very_Low);
                TextView textView = this.tv_recomended;
                StringBuilder sb = new StringBuilder();
                sb.append(getString(R.string.Recommended_Bodyfat));
                sb.append(" :13-17");
                textView.setText(sb.toString());
            } else if (this.bodyfat >= 10.0d && this.bodyfat < 13.0d) {
                this.result = getString(R.string.Low);
                TextView textView2 = this.tv_recomended;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getString(R.string.Recommended_Bodyfat));
                sb2.append(" :13-17");
                textView2.setText(sb2.toString());
            } else if (this.bodyfat >= 13.0d && this.bodyfat < 17.0d) {
                this.result = getString(R.string.Average);
                TextView textView3 = this.tv_recomended;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getString(R.string.Recommended_Bodyfat));
                sb3.append(" :13-17");
                textView3.setText(sb3.toString());
            } else if (this.bodyfat >= 17.0d && this.bodyfat < 25.0d) {
                this.result = getString(R.string.High);
                TextView textView4 = this.tv_recomended;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getString(R.string.Recommended_Bodyfat));
                sb4.append(" :13-17");
                textView4.setText(sb4.toString());
            } else if (this.bodyfat >= 25.0d) {
                this.result = getString(R.string.Very_High);
                TextView textView5 = this.tv_recomended;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(getString(R.string.Recommended_Bodyfat));
                sb5.append(" :13-17");
                textView5.setText(sb5.toString());
            }
        } else if (this.gender.equals("Female")) {
            if (this.bodyfat < 17.0d) {
                this.result = getString(R.string.Very_Low);
                TextView textView6 = this.tv_recomended;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(getString(R.string.Recommended_Bodyfat));
                sb6.append(" :20-27");
                textView6.setText(sb6.toString());
            } else if (this.bodyfat >= 17.0d && this.bodyfat < 20.0d) {
                this.result = getString(R.string.Low);
                TextView textView7 = this.tv_recomended;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(getString(R.string.Recommended_Bodyfat));
                sb7.append(" :20-27");
                textView7.setText(sb7.toString());
            } else if (this.bodyfat >= 20.0d && this.bodyfat < 27.0d) {
                this.result = getString(R.string.Average);
                TextView textView8 = this.tv_recomended;
                StringBuilder sb8 = new StringBuilder();
                sb8.append(getString(R.string.Recommended_Bodyfat));
                sb8.append(" :20-27");
                textView8.setText(sb8.toString());
            } else if (this.bodyfat >= 27.0d && this.bodyfat < 31.0d) {
                this.result = getString(R.string.High);
                TextView textView9 = this.tv_recomended;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(getString(R.string.Recommended_Bodyfat));
                sb9.append(" :20-27");
                textView9.setText(sb9.toString());
            } else if (this.bodyfat >= 31.0d) {
                this.result = getString(R.string.Very_High);
                TextView textView10 = this.tv_recomended;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(getString(R.string.Recommended_Bodyfat));
                sb10.append(" :20-27");
                textView10.setText(sb10.toString());
            }
        }
        TextView textView11 = this.tv_ans_bodyfat;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(getString(R.string.Your_Body_Fat_is));
        sb11.append(" : ");
        sb11.append(String.format("%.02f", new Object[]{Double.valueOf(this.bodyfat)}));
        textView11.setText(sb11.toString());
        this.tv_bodyfat.setText(this.result);
        this.tv_bodyfatchart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Bodyfat_Result.this.showIntertitial();
                    return;
                }
                Bodyfat_Result.this.startActivity(new Intent(Bodyfat_Result.this, Body_Fat_Chart.class));
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
                    Bodyfat_Result.this.startActivity(new Intent(Bodyfat_Result.this, Body_Fat_Chart.class));
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
            startActivity(new Intent(this, Body_Fat_Chart.class));
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
            startActivity(new Intent(this, Body_Fat_Chart.class));
        } else {
            MyApplication.interstitial.show();
        }
    }
}
