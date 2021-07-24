package com.homeworkouts.fitnessloseweight.waist_height_ratio;

import android.app.Activity;
import android.content.Context;
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
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;


public class Waist_Height_Ratio_Result extends Activity {
    String Body;
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    ImageView iv_imoji;
    RelativeLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TextView tv_ans_healthrisk;
    TextView tv_whr_chart;
    TypefaceManager typefaceManager;
    Double waist_height_ratio;


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
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient9);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.waist_height_ratio = Double.valueOf(this.extras.getString("waist_height_ratio"));
        this.Body = this.extras.getString("Body");
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.tv_ans_healthrisk = (TextView) findViewById(R.id.tv_ans_healthrisk);
        this.tv_whr_chart = (TextView) findViewById(R.id.tv_whr_chart);
        this.tv_whr_chart.setVisibility(8);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        this.tv_ans_healthrisk.setTypeface(this.typefaceManager.getLight());
        this.tv_whr_chart.setTypeface(this.typefaceManager.getLight());
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
                    Waist_Height_Ratio_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Waist_Height_Ratio_Result.this.adView.setVisibility(8);
                }
            });
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.waist_height_ratio);
        Log.d("waist_height_ratio->", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(this.Body);
        Log.d("Body->", sb2.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.your_Waist_Height_Ratio));
        sb3.append(" ");
        sb3.append(this.waist_height_ratio);
        textView.setText(sb3.toString());
        TextView textView2 = this.tv_ans_healthrisk;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.your_Body));
        sb4.append(" ");
        sb4.append(this.Body);
        textView2.setText(sb4.toString());
        if (this.waist_height_ratio.doubleValue() < 35.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_sad);
        } else if (this.waist_height_ratio.doubleValue() >= 35.0d && this.waist_height_ratio.doubleValue() < 43.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_sad);
        } else if (this.waist_height_ratio.doubleValue() >= 43.0d && this.waist_height_ratio.doubleValue() < 46.0d) {
            this.iv_imoji.setImageResource(R.drawable.imoji_smile);
        } else if (this.waist_height_ratio.doubleValue() >= 46.0d && this.waist_height_ratio.doubleValue() < 53.0d) {
            this.iv_imoji.setImageResource(R.drawable.imoji_smile);
        } else if (this.waist_height_ratio.doubleValue() >= 53.0d && this.waist_height_ratio.doubleValue() < 58.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_sad);
        } else if (this.waist_height_ratio.doubleValue() >= 58.0d && this.waist_height_ratio.doubleValue() < 63.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_unhappy);
        } else if (this.waist_height_ratio.doubleValue() >= 63.0d) {
            this.iv_imoji.setImageResource(R.drawable.imogi_unhappy);
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Waist_Height_Ratio_Result.this.onBackPressed();
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
