package com.homeworkouts.fitnessloseweight.bmi;

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


public class BMI_Chart extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    GlobalFunction globalFunction;
    ImageView iv_back;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_age_17;
    TextView tv_age_17ext_obese;
    TextView tv_age_17ext_obese_val;
    TextView tv_age_17obese;
    TextView tv_age_17obese_val;
    TextView tv_age_17overweight;
    TextView tv_age_17overweight_val;
    TextView tv_age_17recomonded;
    TextView tv_age_17recomonded_val;
    TextView tv_agegroup35;
    TextView tv_agegroup35_ext_obese;
    TextView tv_agegroup35_ext_obese_val;
    TextView tv_agegroup35_obese;
    TextView tv_agegroup35_obese_val;
    TextView tv_agegroup35_overweight;
    TextView tv_agegroup35_overweight_val;
    TextView tv_agegroup35_recommended;
    TextView tv_agegroup35_recommended_val;
    TextView tv_agegroup45;
    TextView tv_agegroup45_ext_obese;
    TextView tv_agegroup45_ext_obese_val;
    TextView tv_agegroup45_obese;
    TextView tv_agegroup45_obese_val;
    TextView tv_agegroup45_overweight;
    TextView tv_agegroup45_overweight_val;
    TextView tv_agegroup45_recommended;
    TextView tv_agegroup45_recommended_val;
    TextView tv_title;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.bmi_chart);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_age_17 = (TextView) findViewById(R.id.tv_age_17);
        this.tv_age_17recomonded = (TextView) findViewById(R.id.tv_age_17recomonded);
        this.tv_age_17recomonded_val = (TextView) findViewById(R.id.tv_age_17recomonded_val);
        this.tv_age_17overweight = (TextView) findViewById(R.id.tv_age_17overweight);
        this.tv_age_17overweight_val = (TextView) findViewById(R.id.tv_age_17overweight_val);
        this.tv_age_17obese = (TextView) findViewById(R.id.tv_age_17obese);
        this.tv_age_17obese_val = (TextView) findViewById(R.id.tv_age_17obese_val);
        this.tv_age_17ext_obese = (TextView) findViewById(R.id.tv_age_17ext_obese);
        this.tv_age_17ext_obese_val = (TextView) findViewById(R.id.tv_age_17ext_obese_val);
        this.tv_agegroup35 = (TextView) findViewById(R.id.tv_agegroup35);
        this.tv_agegroup35_recommended = (TextView) findViewById(R.id.tv_agegroup35_recommended);
        this.tv_agegroup35_recommended_val = (TextView) findViewById(R.id.tv_agegroup35_recommended_val);
        this.tv_agegroup35_overweight = (TextView) findViewById(R.id.tv_agegroup35_overweight);
        this.tv_agegroup35_overweight_val = (TextView) findViewById(R.id.tv_agegroup35_overweight_val);
        this.tv_agegroup35_obese = (TextView) findViewById(R.id.tv_agegroup35_obese);
        this.tv_agegroup35_obese_val = (TextView) findViewById(R.id.tv_agegroup35_obese_val);
        this.tv_agegroup35_ext_obese = (TextView) findViewById(R.id.tv_agegroup35_ext_obese);
        this.tv_agegroup35_ext_obese_val = (TextView) findViewById(R.id.tv_agegroup35_ext_obese_val);
        this.tv_agegroup45 = (TextView) findViewById(R.id.tv_agegroup45);
        this.tv_agegroup45_recommended = (TextView) findViewById(R.id.tv_agegroup45_recommended);
        this.tv_agegroup45_recommended_val = (TextView) findViewById(R.id.tv_agegroup45_recommended_val);
        this.tv_agegroup45_overweight = (TextView) findViewById(R.id.tv_agegroup45_overweight);
        this.tv_agegroup45_overweight_val = (TextView) findViewById(R.id.tv_agegroup45_overweight_val);
        this.tv_agegroup45_obese = (TextView) findViewById(R.id.tv_agegroup45_obese);
        this.tv_agegroup45_obese_val = (TextView) findViewById(R.id.tv_agegroup45_obese_val);
        this.tv_agegroup45_ext_obese = (TextView) findViewById(R.id.tv_agegroup45_ext_obese);
        this.tv_agegroup45_ext_obese_val = (TextView) findViewById(R.id.tv_agegroup45_ext_obese_val);
        this.tv_age_17.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17recomonded.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17recomonded_val.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17overweight.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17overweight_val.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17obese.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17obese_val.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17ext_obese.setTypeface(this.typefaceManager.getLight());
        this.tv_age_17ext_obese_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_recommended.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_recommended_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_overweight.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_overweight_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_obese.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup35_ext_obese_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_recommended.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_recommended_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_overweight.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_overweight_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_obese.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_obese_val.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_ext_obese.setTypeface(this.typefaceManager.getLight());
        this.tv_agegroup45_ext_obese_val.setTypeface(this.typefaceManager.getLight());
        this.tv_title.setTypeface(this.typefaceManager.getBold());
        this.tv_age_17.setTypeface(this.typefaceManager.getBold());
        this.tv_agegroup35.setTypeface(this.typefaceManager.getBold());
        this.tv_agegroup45.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    BMI_Chart.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    BMI_Chart.this.adView.setVisibility(8);
                }
            });
        }
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BMI_Chart.this.onBackPressed();
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
