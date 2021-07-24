package com.homeworkouts.fitnessloseweight.heart_rate;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;


public class Heart_Rate_Chart extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    GlobalFunction globalFunction;
    ImageView iv_back;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_50;
    TextView tv_55;
    TextView tv_60;
    TextView tv_65;
    TextView tv_70;
    TextView tv_75;
    TextView tv_80;
    TextView tv_85;
    TextView tv_90;
    TextView tv_95;
    TextView tv_heart_rate;
    TextView tv_heartrate_cal;
    TextView tv_hr;
    TextView tv_hr1;
    TextView tv_hr10;
    TextView tv_hr2;
    TextView tv_hr3;
    TextView tv_hr4;
    TextView tv_hr5;
    TextView tv_hr50;
    TextView tv_hr55;
    TextView tv_hr6;
    TextView tv_hr60;
    TextView tv_hr65;
    TextView tv_hr7;
    TextView tv_hr70;
    TextView tv_hr75;
    TextView tv_hr8;
    TextView tv_hr80;
    TextView tv_hr85;
    TextView tv_hr9;
    TextView tv_hr90;
    TextView tv_hr95;
    TextView tv_intensity;
    TextView tv_intensity1;
    TextView tv_targethr;
    TextView tv_targethr1;
    TextView tv_targethr10;
    TextView tv_targethr2;
    TextView tv_targethr3;
    TextView tv_targethr4;
    TextView tv_targethr5;
    TextView tv_targethr6;
    TextView tv_targethr7;
    TextView tv_targethr8;
    TextView tv_targethr9;
    TextView tv_title;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.heart_rate_chart);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_intensity1 = (TextView) findViewById(R.id.tv_intensity1);
        this.tv_heart_rate = (TextView) findViewById(R.id.tv_heart_rate);
        this.tv_hr1 = (TextView) findViewById(R.id.tv_hr1);
        this.tv_hr2 = (TextView) findViewById(R.id.tv_hr2);
        this.tv_hr3 = (TextView) findViewById(R.id.tv_hr3);
        this.tv_hr4 = (TextView) findViewById(R.id.tv_hr4);
        this.tv_hr5 = (TextView) findViewById(R.id.tv_hr5);
        this.tv_hr6 = (TextView) findViewById(R.id.tv_hr6);
        this.tv_hr7 = (TextView) findViewById(R.id.tv_hr7);
        this.tv_hr8 = (TextView) findViewById(R.id.tv_hr8);
        this.tv_hr9 = (TextView) findViewById(R.id.tv_hr9);
        this.tv_hr10 = (TextView) findViewById(R.id.tv_hr10);
        this.tv_50 = (TextView) findViewById(R.id.tv_50);
        this.tv_55 = (TextView) findViewById(R.id.tv_55);
        this.tv_60 = (TextView) findViewById(R.id.tv_60);
        this.tv_65 = (TextView) findViewById(R.id.tv_65);
        this.tv_70 = (TextView) findViewById(R.id.tv_70);
        this.tv_75 = (TextView) findViewById(R.id.tv_75);
        this.tv_80 = (TextView) findViewById(R.id.tv_80);
        this.tv_85 = (TextView) findViewById(R.id.tv_85);
        this.tv_90 = (TextView) findViewById(R.id.tv_90);
        this.tv_95 = (TextView) findViewById(R.id.tv_95);
        this.tv_heartrate_cal = (TextView) findViewById(R.id.tv_heartrate_cal);
        this.tv_targethr = (TextView) findViewById(R.id.tv_targethr);
        this.tv_intensity = (TextView) findViewById(R.id.tv_intensity);
        this.tv_hr = (TextView) findViewById(R.id.tv_hr);
        this.tv_targethr1 = (TextView) findViewById(R.id.tv_targethr1);
        this.tv_targethr2 = (TextView) findViewById(R.id.tv_targethr2);
        this.tv_targethr3 = (TextView) findViewById(R.id.tv_targethr3);
        this.tv_targethr4 = (TextView) findViewById(R.id.tv_targethr4);
        this.tv_targethr5 = (TextView) findViewById(R.id.tv_targethr5);
        this.tv_targethr6 = (TextView) findViewById(R.id.tv_targethr6);
        this.tv_targethr7 = (TextView) findViewById(R.id.tv_targethr7);
        this.tv_targethr8 = (TextView) findViewById(R.id.tv_targethr8);
        this.tv_targethr9 = (TextView) findViewById(R.id.tv_targethr9);
        this.tv_targethr10 = (TextView) findViewById(R.id.tv_targethr10);
        this.tv_hr50 = (TextView) findViewById(R.id.tv_hr50);
        this.tv_hr55 = (TextView) findViewById(R.id.tv_hr55);
        this.tv_hr60 = (TextView) findViewById(R.id.tv_hr60);
        this.tv_hr65 = (TextView) findViewById(R.id.tv_hr65);
        this.tv_hr70 = (TextView) findViewById(R.id.tv_hr70);
        this.tv_hr75 = (TextView) findViewById(R.id.tv_hr75);
        this.tv_hr80 = (TextView) findViewById(R.id.tv_hr80);
        this.tv_hr85 = (TextView) findViewById(R.id.tv_hr85);
        this.tv_hr90 = (TextView) findViewById(R.id.tv_hr90);
        this.tv_hr95 = (TextView) findViewById(R.id.tv_hr95);
        this.tv_title.setTypeface(this.typefaceManager.getBold());
        this.tv_intensity1.setTypeface(this.typefaceManager.getBold());
        this.tv_heart_rate.setTypeface(this.typefaceManager.getBold());
        this.tv_hr1.setTypeface(this.typefaceManager.getLight());
        this.tv_hr2.setTypeface(this.typefaceManager.getLight());
        this.tv_hr3.setTypeface(this.typefaceManager.getLight());
        this.tv_hr4.setTypeface(this.typefaceManager.getLight());
        this.tv_hr5.setTypeface(this.typefaceManager.getLight());
        this.tv_hr6.setTypeface(this.typefaceManager.getLight());
        this.tv_hr7.setTypeface(this.typefaceManager.getLight());
        this.tv_hr8.setTypeface(this.typefaceManager.getLight());
        this.tv_hr9.setTypeface(this.typefaceManager.getLight());
        this.tv_hr10.setTypeface(this.typefaceManager.getLight());
        this.tv_50.setTypeface(this.typefaceManager.getLight());
        this.tv_55.setTypeface(this.typefaceManager.getLight());
        this.tv_60.setTypeface(this.typefaceManager.getLight());
        this.tv_65.setTypeface(this.typefaceManager.getLight());
        this.tv_70.setTypeface(this.typefaceManager.getLight());
        this.tv_75.setTypeface(this.typefaceManager.getLight());
        this.tv_80.setTypeface(this.typefaceManager.getLight());
        this.tv_85.setTypeface(this.typefaceManager.getLight());
        this.tv_90.setTypeface(this.typefaceManager.getLight());
        this.tv_heartrate_cal.setTypeface(this.typefaceManager.getBold());
        this.tv_targethr.setTypeface(this.typefaceManager.getBold());
        this.tv_intensity.setTypeface(this.typefaceManager.getBold());
        this.tv_hr.setTypeface(this.typefaceManager.getBold());
        this.tv_targethr1.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr2.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr3.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr4.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr5.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr6.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr7.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr8.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr9.setTypeface(this.typefaceManager.getLight());
        this.tv_targethr10.setTypeface(this.typefaceManager.getLight());
        this.tv_hr50.setTypeface(this.typefaceManager.getLight());
        this.tv_hr55.setTypeface(this.typefaceManager.getLight());
        this.tv_hr60.setTypeface(this.typefaceManager.getLight());
        this.tv_hr65.setTypeface(this.typefaceManager.getLight());
        this.tv_hr70.setTypeface(this.typefaceManager.getLight());
        this.tv_hr75.setTypeface(this.typefaceManager.getLight());
        this.tv_hr80.setTypeface(this.typefaceManager.getLight());
        this.tv_hr85.setTypeface(this.typefaceManager.getLight());
        this.tv_hr90.setTypeface(this.typefaceManager.getLight());
        this.tv_hr95.setTypeface(this.typefaceManager.getLight());
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Heart_Rate_Chart.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Heart_Rate_Chart.this.adView.setVisibility(8);
                }
            });
        }
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Heart_Rate_Chart.this.onBackPressed();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.tv_hr1.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate1));
        this.tv_hr2.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate2));
        this.tv_hr3.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate3));
        this.tv_hr4.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate4));
        this.tv_hr5.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate5));
        this.tv_hr6.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate6));
        this.tv_hr7.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate7));
        this.tv_hr8.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate8));
        this.tv_hr9.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate9));
        this.tv_hr10.setText(String.valueOf((int) Heart_Rate_Calculator.lower_rate10));
        this.tv_targethr1.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate50));
        this.tv_targethr2.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate55));
        this.tv_targethr3.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate60));
        this.tv_targethr4.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate65));
        this.tv_targethr5.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate70));
        this.tv_targethr6.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate75));
        this.tv_targethr7.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate80));
        this.tv_targethr8.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate85));
        this.tv_targethr9.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate90));
        this.tv_targethr10.setText(String.valueOf((int) Heart_Rate_Calculator.target_heart_rate95));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }


    public void onResume() {
        super.onResume();
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(0);
        } else {
            this.adView.setVisibility(8);
        }
    }
}
