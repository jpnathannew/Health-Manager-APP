package com.homeworkouts.fitnessloseweight.BMR;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;


public class BMR_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    double bmr;
    Bundle extras;
    String from = "";
    GlobalFunction globalFunction;
    ImageView iv_close;
    LinearLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_bmr);

        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.rl_main = (LinearLayout) findViewById(R.id.rl_main);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.bmr = this.extras.getDouble("bmr");
        this.from = this.extras.getString("from", "");
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    BMR_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    BMR_Result.this.adView.setVisibility(8);
                }
            });
        }
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        if (this.from.equals("bmr")) {
//            this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient1);
        } else {
//            this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient7);
        }
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.bmr);
        Log.d("bmr_val->", sb.toString());
        if (this.from.equals("bmr")) {
            TextView textView = this.tv_ans_bmr;
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getString(R.string.Your_BMR_is));
            sb3.append(" :  \n");
            sb3.append(String.format("%.0f", new Object[]{Double.valueOf(this.bmr)}));
            sb2.append(String.valueOf(sb3.toString()));
            sb2.append(" ");
            sb2.append(getString(R.string.cal_per_day));
            textView.setText(sb2.toString());
        } else {
            TextView textView2 = this.tv_ans_bmr;
            StringBuilder sb4 = new StringBuilder();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(getString(R.string.Your_RMR_is));
            sb5.append(" :  \n");
            sb5.append(String.format("%.0f", new Object[]{Double.valueOf(this.bmr)}));
            sb4.append(String.valueOf(sb5.toString()));
            sb4.append(" ");
            sb4.append(getString(R.string.cal_per_day));
            textView2.setText(sb4.toString());
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BMR_Result.this.onBackPressed();
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
