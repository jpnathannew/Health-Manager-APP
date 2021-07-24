package com.homeworkouts.fitnessloseweight.child_growth;

import android.app.Activity;
import android.content.Context;
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
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;


public class Child_growth_result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String age;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    String result;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_age;
    TextView tv_max_heightweight;
    TextView tv_min_heightweight;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_child_growth);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_ans_age = (TextView) findViewById(R.id.tv_ans_age);
        this.tv_min_heightweight = (TextView) findViewById(R.id.tv_min_heightweight);
        this.tv_max_heightweight = (TextView) findViewById(R.id.tv_max_heightweight);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_age.setTypeface(this.typefaceManager.getLight());
        this.tv_min_heightweight.setTypeface(this.typefaceManager.getLight());
        this.tv_max_heightweight.setTypeface(this.typefaceManager.getLight());
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
                    Child_growth_result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Child_growth_result.this.adView.setVisibility(8);
                }
            });
        }
        this.extras = getIntent().getExtras();
        this.age = this.extras.getString("age");
        this.result = this.extras.getString("result");
        TextView textView = this.tv_ans_age;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.Age_of_Child_in_Months));
        sb.append(" : \n");
        sb.append(this.age);
        sb.append(" ");
        sb.append(getString(R.string.Month));
        textView.setText(sb.toString());
        this.tv_min_heightweight.setText(this.result);
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Child_growth_result.this.onBackPressed();
            }
        });
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
