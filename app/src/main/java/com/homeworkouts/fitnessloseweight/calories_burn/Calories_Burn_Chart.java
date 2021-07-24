package com.homeworkouts.fitnessloseweight.calories_burn;

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


public class Calories_Burn_Chart extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    GlobalFunction globalFunction;
    ImageView iv_back;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_aerobic;
    TextView tv_aerobic_cal;
    TextView tv_approx_cal;
    TextView tv_bicycling;
    TextView tv_bicycling_cal;
    TextView tv_burncalories;
    TextView tv_exercise;
    TextView tv_gardening;
    TextView tv_gardening_cal;
    TextView tv_jogging;
    TextView tv_jogging_cal;
    TextView tv_swimming;
    TextView tv_swimming_cal;
    TextView tv_title;
    TextView tv_walking;
    TextView tv_walking_cal;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.calories_burn_chart);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_burncalories = (TextView) findViewById(R.id.tv_burncalories);
        this.tv_exercise = (TextView) findViewById(R.id.tv_exercise);
        this.tv_approx_cal = (TextView) findViewById(R.id.tv_approx_cal);
        this.tv_walking = (TextView) findViewById(R.id.tv_walking);
        this.tv_walking_cal = (TextView) findViewById(R.id.tv_walking_cal);
        this.tv_jogging = (TextView) findViewById(R.id.tv_jogging);
        this.tv_jogging_cal = (TextView) findViewById(R.id.tv_jogging_cal);
        this.tv_swimming = (TextView) findViewById(R.id.tv_swimming);
        this.tv_swimming_cal = (TextView) findViewById(R.id.tv_swimming_cal);
        this.tv_bicycling = (TextView) findViewById(R.id.tv_bicycling);
        this.tv_bicycling_cal = (TextView) findViewById(R.id.tv_bicycling_cal);
        this.tv_aerobic = (TextView) findViewById(R.id.tv_aerobic);
        this.tv_aerobic_cal = (TextView) findViewById(R.id.tv_aerobic_cal);
        this.tv_gardening = (TextView) findViewById(R.id.tv_gardening);
        this.tv_gardening_cal = (TextView) findViewById(R.id.tv_gardening_cal);
        this.tv_burncalories.setTypeface(this.typefaceManager.getBold());
        this.tv_exercise.setTypeface(this.typefaceManager.getBold());
        this.tv_approx_cal.setTypeface(this.typefaceManager.getBold());
        this.tv_walking.setTypeface(this.typefaceManager.getLight());
        this.tv_walking_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_jogging.setTypeface(this.typefaceManager.getLight());
        this.tv_jogging_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_swimming.setTypeface(this.typefaceManager.getLight());
        this.tv_swimming_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_bicycling.setTypeface(this.typefaceManager.getLight());
        this.tv_bicycling_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_aerobic.setTypeface(this.typefaceManager.getLight());
        this.tv_aerobic_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_gardening.setTypeface(this.typefaceManager.getLight());
        this.tv_gardening_cal.setTypeface(this.typefaceManager.getLight());
        this.tv_title.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
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
                    Calories_Burn_Chart.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Calories_Burn_Chart.this.adView.setVisibility(8);
                }
            });
        }
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Calories_Burn_Chart.this.onBackPressed();
            }
        });
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
