package com.homeworkouts.fitnessloseweight.children_height_growth;

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


public class Children_Predicted_Height_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    Float predicted_height;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_child_predicted_Height;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_children_height_prediction);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_ans_child_predicted_Height = (TextView) findViewById(R.id.tv_ans_child_predicted_Height);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.tv_ans_child_predicted_Height.setTypeface(this.typefaceManager.getLight());
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
                    Children_Predicted_Height_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Children_Predicted_Height_Result.this.adView.setVisibility(8);
                }
            });
        }
        this.extras = getIntent().getExtras();
        this.predicted_height = Float.valueOf(this.extras.getFloat("child_predicted_height"));
        TextView textView = this.tv_ans_child_predicted_Height;
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.Your_Childs_Predicted_Height));
        sb.append(" ");
        sb.append(String.format("%.02f", new Object[]{this.predicted_height}));
        sb.append(" ");
        sb.append(getString(R.string.Feet_At_21_Years));
        textView.setText(sb.toString());
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Children_Predicted_Height_Result.this.onBackPressed();
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
