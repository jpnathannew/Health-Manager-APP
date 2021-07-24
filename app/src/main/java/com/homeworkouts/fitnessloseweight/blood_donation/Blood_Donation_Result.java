package com.homeworkouts.fitnessloseweight.blood_donation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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


public class Blood_Donation_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    String flag;
    GlobalFunction globalFunction;
    ImageView iv_close;
    String next_date;
    String prev_date;
    RelativeLayout rl_main;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_next_date;
    TextView tv_prev_date;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_blood_donation);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_prev_date = (TextView) findViewById(R.id.tv_prev_date);
        this.tv_next_date = (TextView) findViewById(R.id.tv_next_date);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        this.tv_prev_date.setTypeface(this.typefaceManager.getLight());
        this.tv_next_date.setTypeface(this.typefaceManager.getLight());
        this.extras = getIntent().getExtras();
        this.prev_date = this.extras.getString("prevdate");
        this.next_date = this.extras.getString("nextdate");
        this.flag = this.extras.getString("flag");
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Blood_Donation_Result.this.onBackPressed();
            }
        });
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Blood_Donation_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Blood_Donation_Result.this.adView.setVisibility(8);
                }
            });
        }
        if (this.flag.equals("0")) {
            TextView textView = this.tv_prev_date;
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.Date_of_last_donation));
            sb.append(" : ");
            sb.append(this.prev_date);
            textView.setText(sb.toString());
            TextView textView2 = this.tv_next_date;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getString(R.string.Date_of_Next_donation));
            sb2.append(" : \n");
            sb2.append(this.next_date);
            textView2.setText(sb2.toString());
//            this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient3);
            return;
        }
        TextView textView3 = this.tv_prev_date;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Date_of_the_first_day_of_your_last_period_is));
        sb3.append(" : ");
        sb3.append(this.prev_date);
        textView3.setText(sb3.toString());
        TextView textView4 = this.tv_next_date;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.Estimated_Expected_Date_of_Delivery));
        sb4.append(" : \n");
        sb4.append(this.next_date);
        textView4.setText(sb4.toString());
//        this.rl_main.setBackgroundResource(R.drawable.popup_background_gradient6);
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
