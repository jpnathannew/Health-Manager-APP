package com.homeworkouts.fitnessloseweight.body_frame_size;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
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


public class BodyFrame_Result extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String body_frame;
    Bundle extras;
    GlobalFunction globalFunction;
    ImageView iv_close;
    ImageView iv_imoji;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_ans_bmr;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.popup_body_frame);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.extras = getIntent().getExtras();
        this.body_frame = this.extras.getString("body_frame");
        this.tv_ans_bmr = (TextView) findViewById(R.id.tv_ans_bmr);
        this.iv_close = (ImageView) findViewById(R.id.iv_close);
        this.iv_imoji = (ImageView) findViewById(R.id.iv_imoji);
        this.tv_ans_bmr.setTypeface(this.typefaceManager.getLight());
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            this.adView.setVisibility(8);
        } else {
            this.adView.setVisibility(0);
            this.adView.loadAd(new Builder().build());
            this.adView.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    super.onAdLoaded();
                    BodyFrame_Result.this.adView.setVisibility(0);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    BodyFrame_Result.this.adView.setVisibility(8);
                }
            });
        }
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(this.body_frame);
        Log.d("body_frame->", sb.toString());
        TextView textView = this.tv_ans_bmr;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getString(R.string.Body_frame_text));
        sb2.append(" : ");
        sb2.append(this.body_frame);
        textView.setText(sb2.toString());
        if (this.body_frame.equals(getString(R.string.Body_frame_text_small))) {
            this.iv_imoji.setImageResource(R.drawable.imogi_sad);
        } else if (this.body_frame.equals(getString(R.string.Body_frame_text_medium))) {
            this.iv_imoji.setImageResource(R.drawable.imoji_smile);
        } else if (this.body_frame.equals(getString(R.string.Body_frame_text_large))) {
            this.iv_imoji.setImageResource(R.drawable.imogi_unhappy);
        }
        this.iv_close.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BodyFrame_Result.this.onBackPressed();
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
