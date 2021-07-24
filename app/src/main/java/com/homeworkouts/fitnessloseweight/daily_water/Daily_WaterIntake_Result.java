package com.homeworkouts.fitnessloseweight.daily_water;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
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


public class Daily_WaterIntake_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_waterintake_result;
    TypefaceManager typefaceManager;
    Double water_intake;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_waterintake);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_waterintake_result = (TextView) findViewById(R.id.tv_waterintake_result);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_waterintake_result.setTypeface(this.typefaceManager.getLight());
        this.extras = getIntent().getExtras();
        this.water_intake = Double.valueOf(this.extras.getDouble("water_intake"));
        StringBuilder sb = new StringBuilder();
        sb.append("water_intake");
        sb.append(this.water_intake);
        Log.d("water_intake", sb.toString());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        TextView textView = this.tv_waterintake_result;
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Daily_Water_Intake));
        sb3.append("  : %.0f");
        sb2.append(String.format(sb3.toString(), new Object[]{this.water_intake}));
        sb2.append("ml");
        textView.setText(sb2.toString());
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Daily_WaterIntake_Result.this.onBackPressed();
            }
        });
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
            return;
        }
        this.adView.setVisibility(0);
        this.adView.loadAd(new Builder().build());
        this.adView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                super.onAdLoaded();
                Daily_WaterIntake_Result.this.adView.setVisibility(0);
            }

            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Daily_WaterIntake_Result.this.adView.setVisibility(8);
            }
        });
    }
}
