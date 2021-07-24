package com.homeworkouts.fitnessloseweight.sugar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import java.util.ArrayList;


public class Sugar_calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_sugar;
    ArrayList<String> arraylist_sugar = new ArrayList<>();
    Double blood_sugarval;
    EditText et_sugar_value;
    Double final_bloodsugar_val;
    GlobalFunction globalFunction;
    ImageView iv_back;
    ListView listViewsugar;
    private PopupWindow popupWindowsugar;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_caluculate_blood_sugar;
    TextView tv_sugar;
    TextView tv_sugar_unit;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sugar_calculator);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_sugar = (TextView) findViewById(R.id.tv_sugar);
        this.tv_sugar_unit = (TextView) findViewById(R.id.tv_sugar_unit);
        this.et_sugar_value = (EditText) findViewById(R.id.et_sugar_value);
        this.tv_caluculate_blood_sugar = (TextView) findViewById(R.id.tv_caluculate_blood_sugar);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_sugar_unit.setOnClickListener(showPopupWindowTime());
        this.arraylist_sugar.clear();
        this.arraylist_sugar.add(getString(R.string.mmol));
        this.arraylist_sugar.add(getString(R.string.mg));
        this.adapter_sugar = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_sugar);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_sugar.setTypeface(this.typefaceManager.getBold());
        this.tv_caluculate_blood_sugar.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Sugar_calculator.this.onBackPressed();
            }
        });
        this.tv_caluculate_blood_sugar.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Sugar_calculator.this.et_sugar_value.getText().toString().trim().equals("") || Sugar_calculator.this.et_sugar_value.getText().toString().trim().equals(".")) {
                    Toast.makeText(Sugar_calculator.this.getApplicationContext(), Sugar_calculator.this.getString(R.string.Enter_Blood_sugar_value), 0).show();
                    return;
                }
                Sugar_calculator.this.blood_sugarval = Double.valueOf(Double.parseDouble(Sugar_calculator.this.et_sugar_value.getText().toString().toString()));
                if (Sugar_calculator.this.tv_sugar_unit.getText().toString().trim().equals(Sugar_calculator.this.getString(R.string.mmol))) {
                    Sugar_calculator.this.calculate_m1();
                } else {
                    Sugar_calculator.this.calculate_m2();
                }
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Sugar_calculator.this.showIntertitial();
                    return;
                }
                Intent intent = new Intent(Sugar_calculator.this, Sugar_Result.class);
                intent.putExtra("final_bloodsugar_val", Sugar_calculator.this.final_bloodsugar_val);
                Sugar_calculator.this.startActivity(intent);
            }
        });
    }

    public void calculate_m1() {
        this.final_bloodsugar_val = Double.valueOf(this.blood_sugarval.doubleValue() * 18.0d);
    }

    public void calculate_m2() {
        this.final_bloodsugar_val = Double.valueOf(this.blood_sugarval.doubleValue() * 0.05556d);
    }

    private OnClickListener showPopupWindowTime() {
        return new OnClickListener() {
            public void onClick(View view) {
                Sugar_calculator.this.popupWindowTime().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowTime() {
        this.popupWindowsugar = new PopupWindow(this);
        this.listViewsugar = new ListView(this);
        this.listViewsugar.setDividerHeight(0);
        this.listViewsugar.setAdapter(this.adapter_sugar);
        this.listViewsugar.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Sugar_calculator.this.tv_sugar_unit.setText((CharSequence) Sugar_calculator.this.arraylist_sugar.get(i));
                Sugar_calculator.this.dismissPopupTime();
            }
        });
        this.popupWindowsugar.setFocusable(true);
        this.popupWindowsugar.setWidth(this.tv_sugar_unit.getMeasuredWidth());
        this.popupWindowsugar.setHeight(-2);
        this.popupWindowsugar.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowsugar.setContentView(this.listViewsugar);
        return this.popupWindowsugar;
    }


    public void dismissPopupTime() {
        if (this.popupWindowsugar != null) {
            this.popupWindowsugar.dismiss();
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
            Intent intent = new Intent(this, Sugar_Result.class);
            intent.putExtra("final_bloodsugar_val", this.final_bloodsugar_val);
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
            Intent intent2 = new Intent(this, Sugar_Result.class);
            intent2.putExtra("final_bloodsugar_val", this.final_bloodsugar_val);
            startActivity(intent2);
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
                    Intent intent = new Intent(Sugar_calculator.this, Sugar_Result.class);
                    intent.putExtra("final_bloodsugar_val", Sugar_calculator.this.final_bloodsugar_val);
                    Sugar_calculator.this.startActivity(intent);
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
