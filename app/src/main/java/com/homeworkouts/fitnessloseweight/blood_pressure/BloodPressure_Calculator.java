package com.homeworkouts.fitnessloseweight.blood_pressure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.general.MyApplication;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;


public class BloodPressure_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    String diastolic_val;
    EditText et_diastolic_pressure;
    EditText et_systolic_pressure;
    GlobalFunction globalFunction;
    ImageView iv_back;
    SharedPreferenceManager sharedPreferenceManager;
    String systolic_val;
    TextView tv_bloodpressure;
    TextView tv_calculate_bloodpressure;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.bloodpressure_calculator);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.et_systolic_pressure = (EditText) findViewById(R.id.et_systolic_pressure);
        this.et_diastolic_pressure = (EditText) findViewById(R.id.et_diastolic_pressure);
        this.tv_bloodpressure = (TextView) findViewById(R.id.tv_bloodpressure);
        this.tv_calculate_bloodpressure = (TextView) findViewById(R.id.tv_calculate_bloodpressure);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.adView = (AdView) findViewById(R.id.adView);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.tv_bloodpressure.setTypeface(this.typefaceManager.getBold());
        this.tv_calculate_bloodpressure.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BloodPressure_Calculator.this.onBackPressed();
            }
        });
        this.tv_calculate_bloodpressure.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (BloodPressure_Calculator.this.et_systolic_pressure.getText().toString().trim().equals("") || BloodPressure_Calculator.this.et_systolic_pressure.getText().toString().trim().equals(".")) {
                    Toast.makeText(BloodPressure_Calculator.this.getApplicationContext(), BloodPressure_Calculator.this.getString(R.string.Enter_systolic_value), 0).show();
                } else if (BloodPressure_Calculator.this.et_diastolic_pressure.getText().toString().trim().equals("") || BloodPressure_Calculator.this.et_diastolic_pressure.getText().toString().trim().equals(".")) {
                    Toast.makeText(BloodPressure_Calculator.this.getApplicationContext(), BloodPressure_Calculator.this.getString(R.string.Enter_diastolic_value), 0).show();
                } else {
                    BloodPressure_Calculator.this.systolic_val = BloodPressure_Calculator.this.et_systolic_pressure.getText().toString().trim();
                    BloodPressure_Calculator.this.diastolic_val = BloodPressure_Calculator.this.et_diastolic_pressure.getText().toString().trim();
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("random_number==>");
                    sb.append(random);
                    printStream.println(sb.toString());
                    if (random == 2) {
                        BloodPressure_Calculator.this.showIntertitial();
                        return;
                    }
                    Intent intent = new Intent(BloodPressure_Calculator.this, BloodPressure_Result.class);
                    intent.putExtra("systolic_val", Float.parseFloat(BloodPressure_Calculator.this.systolic_val));
                    intent.putExtra("diastolic_val", Float.parseFloat(BloodPressure_Calculator.this.diastolic_val));
                    BloodPressure_Calculator.this.startActivity(intent);
                }
            }
        });
    }


    public void onResume() {
        super.onResume();
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue() && MyApplication.interstitial != null && !MyApplication.interstitial.isLoaded() && !MyApplication.interstitial.isLoading()) {
            ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                public void onNoResponse() {
                }

                public void onResponseObtained() {
                    MyApplication.interstitial.loadAd(new Builder().build());
                }
            });
        }
        if (!this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            MyApplication.interstitial.setAdListener(new AdListener() {
                public void onAdClosed() {
                    super.onAdClosed();
                    MyApplication.interstitial.loadAd(new Builder().build());
                    Intent intent = new Intent(BloodPressure_Calculator.this, BloodPressure_Result.class);
                    intent.putExtra("systolic_val", BloodPressure_Calculator.this.systolic_val);
                    intent.putExtra("diastolic_val", BloodPressure_Calculator.this.diastolic_val);
                    BloodPressure_Calculator.this.startActivity(intent);
                }

                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    if (MyApplication.interstitial != null && !MyApplication.interstitial.isLoading()) {
                        ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                            public void onNoResponse() {
                            }

                            public void onResponseObtained() {
                                MyApplication.interstitial.loadAd(new Builder().build());
                            }
                        });
                    }
                }
            });
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        this.adView.setVisibility(8);
        ActivityCompat.finishAfterTransition(this);
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            Intent intent = new Intent(this, BloodPressure_Result.class);
            intent.putExtra("systolic_val", this.systolic_val);
            intent.putExtra("diastolic_val", this.diastolic_val);
            startActivity(intent);
        } else if (MyApplication.interstitial == null || !MyApplication.interstitial.isLoaded()) {
            if (!MyApplication.interstitial.isLoading()) {
                ConnectionBuddy.getInstance().hasNetworkConnection(new NetworkRequestCheckListener() {
                    public void onNoResponse() {
                    }

                    public void onResponseObtained() {
                        MyApplication.interstitial.loadAd(new Builder().build());
                    }
                });
            }
            Intent intent2 = new Intent(this, BloodPressure_Result.class);
            intent2.putExtra("systolic_val", this.systolic_val);
            intent2.putExtra("diastolic_val", this.diastolic_val);
            startActivity(intent2);
        } else {
            MyApplication.interstitial.show();
        }
    }
}
