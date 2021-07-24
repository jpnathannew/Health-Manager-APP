package com.homeworkouts.fitnessloseweight.ideal_body_weight;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;


public class Ideal_Body_Weight_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    Float ideal_body_weight;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ideal_weight;
    TextView tv_ideal_weight_range;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_ideal_body_weight_result);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_ideal_weight = (TextView) findViewById(R.id.tv_ideal_weight);
        this.tv_ideal_weight_range = (TextView) findViewById(R.id.tv_ideal_weight_range);
        this.tv_ideal_weight.setTypeface(this.typefaceManager.getLight());
        this.tv_ideal_weight_range.setTypeface(this.typefaceManager.getLight());
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
                    Ideal_Body_Weight_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Ideal_Body_Weight_Result.this.adView.setVisibility(8);
                }
            });
        }
        this.extras = getIntent().getExtras();
        this.ideal_body_weight = Float.valueOf(this.extras.getFloat("ideal_body_weight"));
        if (this.ideal_body_weight.floatValue() <= 0.0f) {
            TextView textView = this.tv_ideal_weight;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Your_ideal_body_weight_is));
            sb.append("0");
            sb.append(getString(R.string.kg));
            textView.setText(sb.toString());
            TextView textView2 = this.tv_ideal_weight_range;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.Your_ideal_body_weight_range_is));
            sb2.append("0-0");
            sb2.append(getString(R.string.kg));
            textView2.setText(sb2.toString());
            return;
        }
        TextView textView3 = this.tv_ideal_weight;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Your_ideal_body_weight_is));
        sb3.append(" ");
        sb3.append(Math.round(this.ideal_body_weight.floatValue()));
        sb3.append(getString(R.string.kg));
        textView3.setText(sb3.toString());
        TextView textView4 = this.tv_ideal_weight_range;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.Your_ideal_body_weight_range_is));
        sb4.append(" ");
        sb4.append(Math.round(this.ideal_body_weight.floatValue()) - 5);
        sb4.append("-");
        sb4.append(Math.round(this.ideal_body_weight.floatValue()) + 5);
        sb4.append(getString(R.string.kg));
        textView4.setText(sb4.toString());
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
