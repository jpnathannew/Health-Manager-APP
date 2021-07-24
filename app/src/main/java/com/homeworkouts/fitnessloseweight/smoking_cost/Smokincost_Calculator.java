package com.homeworkouts.fitnessloseweight.smoking_cost;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
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


public class Smokincost_Calculator extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    EditText et_cig_inpack;
    EditText et_cig_price;
    EditText et_cig_smoked;
    GlobalFunction globalFunction;
    Double monthly_expense;
    Double no_of_cig_inpack;
    Double no_of_cig_perday;
    Double price_inrs;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_count_smoking_cost;
    TypefaceManager typefaceManager;
    Double weekely_expense;
    Double yearly_expense;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.smoking_cost_calculator);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.et_cig_smoked = (EditText) findViewById(R.id.et_cig_smoked);
        this.et_cig_inpack = (EditText) findViewById(R.id.et_cig_inpack);
        this.et_cig_price = (EditText) findViewById(R.id.et_cig_price);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_count_smoking_cost = (TextView) findViewById(R.id.tv_count_smoking_cost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle((CharSequence) getString(R.string.Smoking_Cost));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.smoking_cost)));
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.smoking_cost1));
        }
        this.tv_count_smoking_cost.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Smokincost_Calculator.this.et_cig_smoked.getText().toString().trim().equals("")) {
                    Toast.makeText(Smokincost_Calculator.this.getApplicationContext(), Smokincost_Calculator.this.getString(R.string.Enter_no_of_cigarettes_smoked_per_day), 0).show();
                } else if (Smokincost_Calculator.this.et_cig_inpack.getText().toString().trim().equals("")) {
                    Toast.makeText(Smokincost_Calculator.this.getApplicationContext(), Smokincost_Calculator.this.getString(R.string.Enter_no_of_cigarettes_in_pack), 0).show();
                } else if (Smokincost_Calculator.this.et_cig_price.getText().toString().trim().equals("")) {
                    Toast.makeText(Smokincost_Calculator.this.getApplicationContext(), Smokincost_Calculator.this.getString(R.string.Enter_price_per_packes_in_Rs), 0).show();
                } else {
                    Smokincost_Calculator.this.no_of_cig_perday = Double.valueOf(Double.parseDouble(Smokincost_Calculator.this.et_cig_smoked.getText().toString().trim()));
                    Smokincost_Calculator.this.no_of_cig_inpack = Double.valueOf(Double.parseDouble(Smokincost_Calculator.this.et_cig_inpack.getText().toString().trim()));
                    Smokincost_Calculator.this.price_inrs = Double.valueOf(Double.parseDouble(Smokincost_Calculator.this.et_cig_price.getText().toString().trim()));
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("random_number==>");
                    sb.append(random);
                    printStream.println(sb.toString());
                    if (random == 2) {
                        Smokincost_Calculator.this.showIntertitial();
                    } else {
                        Smokincost_Calculator.this.calculate_smoking_cost();
                    }
                }
            }
        });
    }

    public void calculate_smoking_cost() {
        this.weekely_expense = Double.valueOf((this.no_of_cig_perday.doubleValue() / this.no_of_cig_inpack.doubleValue()) * 7.0d * this.price_inrs.doubleValue());
        this.monthly_expense = Double.valueOf((this.no_of_cig_perday.doubleValue() / this.no_of_cig_inpack.doubleValue()) * 30.0d * this.price_inrs.doubleValue());
        this.yearly_expense = Double.valueOf((this.no_of_cig_perday.doubleValue() / this.no_of_cig_inpack.doubleValue()) * 365.0d * this.price_inrs.doubleValue());
        StringBuilder sb = new StringBuilder();
        sb.append("weekely_expense");
        sb.append(this.weekely_expense);
        Log.d("weekely_expense", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("monthly_expense");
        sb2.append(this.monthly_expense);
        Log.d("monthly_expense", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("yearly_expense");
        sb3.append(this.yearly_expense);
        Log.d("yearly_expense", sb3.toString());
        Intent intent = new Intent(this, SmokingCost_Result.class);
        intent.putExtra("weekely_expense", this.weekely_expense);
        intent.putExtra("monthly_expense", this.monthly_expense);
        intent.putExtra("yearly_expense", this.yearly_expense);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            calculate_smoking_cost();
        } else if (MyApplication.interstitial == null || !MyApplication.interstitial.isLoaded()) {
            if (!MyApplication.interstitial.isLoading()) {
                MyApplication.interstitial.loadAd(new Builder().build());
            }
            calculate_smoking_cost();
        } else {
            MyApplication.interstitial.show();
        }
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
                    Smokincost_Calculator.this.calculate_smoking_cost();
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
}
