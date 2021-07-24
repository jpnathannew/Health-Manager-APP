package com.homeworkouts.fitnessloseweight.calories_intake;

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


public class Daily_Calories_Intake_Chart extends Activity {
    String TAG = getClass().getSimpleName();
    TextView active1;
    AdView adView;
    TextView age1;
    Bundle extras;
    int gain;
    GlobalFunction globalFunction;
    ImageView iv_back;
    int lose;
    int maintain;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_Active;
    TextView tv_age;
    TextView tv_cal_require;
    TextView tv_curr_weight;
    TextView tv_gain;
    TextView tv_gain_weight;
    TextView tv_lose;
    TextView tv_lose_weight;
    TextView tv_lowactivity;
    TextView tv_lowactivity1;
    TextView tv_maintain;
    TextView tv_sedentary;
    TextView tv_sedentary1;
    TextView tv_title;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.calories_intake_chart);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_maintain = (TextView) findViewById(R.id.tv_maintain);
        this.tv_gain = (TextView) findViewById(R.id.tv_gain);
        this.tv_lose = (TextView) findViewById(R.id.tv_lose);
        this.tv_curr_weight = (TextView) findViewById(R.id.tv_curr_weight);
        this.tv_lose_weight = (TextView) findViewById(R.id.tv_lose_weight);
        this.tv_gain_weight = (TextView) findViewById(R.id.tv_gain_weight);
        this.tv_cal_require = (TextView) findViewById(R.id.tv_cal_require);
        this.tv_age = (TextView) findViewById(R.id.tv_age);
        this.tv_sedentary = (TextView) findViewById(R.id.tv_sedentary);
        this.tv_lowactivity = (TextView) findViewById(R.id.tv_lowactivity);
        this.tv_Active = (TextView) findViewById(R.id.tv_Active);
        this.age1 = (TextView) findViewById(R.id.age1);
        this.tv_sedentary1 = (TextView) findViewById(R.id.tv_sedentary1);
        this.tv_lowactivity1 = (TextView) findViewById(R.id.tv_lowactivity1);
        this.active1 = (TextView) findViewById(R.id.active1);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_title.setTypeface(this.typefaceManager.getBold());
        this.tv_maintain.setTypeface(this.typefaceManager.getLight());
        this.tv_gain.setTypeface(this.typefaceManager.getLight());
        this.tv_lose.setTypeface(this.typefaceManager.getLight());
        this.tv_curr_weight.setTypeface(this.typefaceManager.getBold());
        this.tv_lose_weight.setTypeface(this.typefaceManager.getBold());
        this.tv_gain_weight.setTypeface(this.typefaceManager.getBold());
        this.tv_cal_require.setTypeface(this.typefaceManager.getBold());
        this.tv_age.setTypeface(this.typefaceManager.getBold());
        this.tv_sedentary.setTypeface(this.typefaceManager.getBold());
        this.tv_lowactivity.setTypeface(this.typefaceManager.getBold());
        this.tv_Active.setTypeface(this.typefaceManager.getBold());
        this.age1.setTypeface(this.typefaceManager.getBold());
        this.tv_sedentary1.setTypeface(this.typefaceManager.getBold());
        this.tv_lowactivity1.setTypeface(this.typefaceManager.getBold());
        this.active1.setTypeface(this.typefaceManager.getBold());
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
                    Daily_Calories_Intake_Chart.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Daily_Calories_Intake_Chart.this.adView.setVisibility(8);
                }
            });
        }
        this.extras = getIntent().getExtras();
        this.maintain = this.extras.getInt("maintain");
        this.gain = this.extras.getInt("gain");
        this.lose = this.extras.getInt("lose");
        TextView textView = this.tv_maintain;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.You_need));
        sb.append(" ");
        sb.append(String.valueOf(this.maintain));
        sb.append(" ");
        sb.append(getString(R.string.Calories_per_day));
        textView.setText(sb.toString());
        TextView textView2 = this.tv_gain;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.You_need));
        sb2.append(" ");
        sb2.append(String.valueOf(this.gain));
        sb2.append(" ");
        sb2.append(getString(R.string.Calories_per_day_will_cause_you_to_gain_one_pound_a_week));
        textView2.setText(sb2.toString());
        TextView textView3 = this.tv_lose;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getString(R.string.You_need));
        sb3.append(" ");
        sb3.append(String.valueOf(this.lose));
        sb3.append(" ");
        sb3.append(getString(R.string.Calories_per_day_will_cause_you_to_Lose_one_pound_a_week));
        textView3.setText(sb3.toString());
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Daily_Calories_Intake_Chart.this.onBackPressed();
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
