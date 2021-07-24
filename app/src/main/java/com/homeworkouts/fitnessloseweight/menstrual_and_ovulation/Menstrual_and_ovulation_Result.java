package com.homeworkouts.fitnessloseweight.menstrual_and_ovulation;

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
import java.text.SimpleDateFormat;
import java.util.Date;


public class Menstrual_and_ovulation_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String curr_date;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    String nextdate1_ovulation;
    String nextdate2_ovulation;
    String nextdate_menstrual;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_next_date;
    TextView tv_ovulation1;
    TextView tv_ovulation2;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_menstrual_and_ovulation);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_next_date = (TextView) findViewById(R.id.tv_next_date);
        this.tv_ovulation1 = (TextView) findViewById(R.id.tv_ovulation1);
        this.tv_ovulation2 = (TextView) findViewById(R.id.tv_ovulation2);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_next_date.setTypeface(this.typefaceManager.getLight());
        this.tv_ovulation1.setTypeface(this.typefaceManager.getLight());
        this.tv_ovulation2.setTypeface(this.typefaceManager.getLight());
        this.extras = getIntent().getExtras();
        this.nextdate_menstrual = this.extras.getString("nextdate_menstrual");
        this.nextdate1_ovulation = this.extras.getString("nextdate1_ovulation");
        this.nextdate2_ovulation = this.extras.getString("nextdate2_ovulation");
        this.curr_date = this.extras.getString("curr_date");
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        TextView textView = this.tv_next_date;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.Date_of_next_Menstrual));
        sb.append(" : \n");
        sb.append(this.nextdate_menstrual);
        textView.setText(sb.toString());
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(this.curr_date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            this.curr_date = simpleDateFormat.format(parse);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("myDate->");
            sb2.append(simpleDateFormat.format(parse));
            Log.d("myDate", sb2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView textView2 = this.tv_ovulation1;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.Date_of_last_Menstrual));
        sb3.append(" : \n");
        sb3.append(this.curr_date);
        textView2.setText(sb3.toString());
        TextView textView3 = this.tv_ovulation2;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(getString(R.string.Your_next_ovulation_period_is));
        sb4.append(" : \n");
        sb4.append(this.nextdate1_ovulation);
        sb4.append("-");
        sb4.append(this.nextdate2_ovulation);
        textView3.setText(sb4.toString());
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Menstrual_and_ovulation_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Menstrual_and_ovulation_Result.this.adView.setVisibility(8);
                }
            });
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Menstrual_and_ovulation_Result.this.onBackPressed();
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
