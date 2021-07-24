package com.homeworkouts.fitnessloseweight.body_adiposity_index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
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


public class Body_Adiposity_Index_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_height;
    ArrayAdapter<String> adapter_wrist;
    ArrayList<String> arraylist_gender = new ArrayList<>();
    ArrayList<String> arraylist_height = new ArrayList<>();
    ArrayList<String> arraylist_wrist = new ArrayList<>();
    double bai;
    EditText et_height;
    EditText et_weight;
    GlobalFunction globalFunction;
    float height;
    String height_unit;
    float inserted_height;
    float inserted_waist;
    ImageView iv_back;
    ListView listViewHeight;

    public PopupWindow popupWindowHeight;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_body_adopisity_index;
    TextView tv_heightunit;
    TextView tv_search_bai;
    TextView tv_weightunit;
    TypefaceManager typefaceManager;
    float waist;
    String weight_unit;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.body_adopisity_index);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.et_height = (EditText) findViewById(R.id.et_height);
        this.et_weight = (EditText) findViewById(R.id.et_weight);
        this.tv_heightunit = (TextView) findViewById(R.id.tv_heightunit);
        this.tv_weightunit = (TextView) findViewById(R.id.tv_weightunit);
        this.tv_search_bai = (TextView) findViewById(R.id.tv_search_bai);
        this.tv_body_adopisity_index = (TextView) findViewById(R.id.tv_body_adopisity_index);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_body_adopisity_index.setTypeface(this.typefaceManager.getBold());
        this.et_height.setTypeface(this.typefaceManager.getLight());
        this.et_weight.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_weightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_search_bai.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.height_unit = getString(R.string.feet);
        this.weight_unit = getString(R.string.lbs);
        this.tv_heightunit.setOnClickListener(showPopupWindowHeight(true));
        this.tv_weightunit.setOnClickListener(showPopupWindowHeight(false));
        this.arraylist_wrist.clear();
        this.arraylist_wrist.add(getString(R.string.cm));
        this.arraylist_wrist.add(getString(R.string.inch));
        this.arraylist_height.clear();
        this.arraylist_height.add(getString(R.string.cm));
        this.arraylist_height.add(getString(R.string.feet));
        this.arraylist_gender.clear();
        this.arraylist_gender.add(getString(R.string.Male));
        this.arraylist_gender.add(getString(R.string.Female));
        this.adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_gender);
        this.adapter_wrist = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_wrist);
        this.adapter_height = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_height);
        this.tv_search_bai.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Body_Adiposity_Index_Calculator.this.et_height.getText().toString().trim().equals("") || Body_Adiposity_Index_Calculator.this.et_height.getText().toString().trim().equals(".")) {
                    Body_Adiposity_Index_Calculator.this.et_height.setError(Body_Adiposity_Index_Calculator.this.getString(R.string.Enter_Height));
                } else if (Body_Adiposity_Index_Calculator.this.et_weight.getText().toString().trim().equals("") || Body_Adiposity_Index_Calculator.this.et_weight.getText().toString().trim().equals(".")) {
                    Body_Adiposity_Index_Calculator.this.et_weight.setError(Body_Adiposity_Index_Calculator.this.getString(R.string.Enter_Weight));
                } else {
                    Body_Adiposity_Index_Calculator.this.height_unit = Body_Adiposity_Index_Calculator.this.tv_heightunit.getText().toString();
                    Body_Adiposity_Index_Calculator.this.weight_unit = Body_Adiposity_Index_Calculator.this.tv_weightunit.getText().toString();
                    Body_Adiposity_Index_Calculator.this.inserted_height = Float.parseFloat(Body_Adiposity_Index_Calculator.this.et_height.getText().toString());
                    Body_Adiposity_Index_Calculator.this.inserted_waist = Float.parseFloat(Body_Adiposity_Index_Calculator.this.et_weight.getText().toString());
                    StringBuilder sb = new StringBuilder();
                    sb.append("inserted_waist");
                    sb.append(Body_Adiposity_Index_Calculator.this.inserted_waist);
                    Log.d("inserted_waist", sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("inserted_height");
                    sb2.append(Body_Adiposity_Index_Calculator.this.inserted_height);
                    Log.d("inserted_height", sb2.toString());
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("random_number==>");
                    sb3.append(random);
                    printStream.println(sb3.toString());
                    if (random == 2) {
                        Body_Adiposity_Index_Calculator.this.showIntertitial();
                    } else {
                        Body_Adiposity_Index_Calculator.this.calculate();
                    }
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Body_Adiposity_Index_Calculator.this.onBackPressed();
            }
        });
    }

    private OnClickListener showPopupWindowHeight(final boolean z) {
        return new OnClickListener() {
            public void onClick(View view) {
                Body_Adiposity_Index_Calculator.this.popupWindowHeight(z).showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowHeight(final boolean z) {
        this.popupWindowHeight = new PopupWindow(this);
        this.listViewHeight = new ListView(this);
        this.listViewHeight.setDividerHeight(0);
        if (z) {
            this.listViewHeight.setAdapter(this.adapter_height);
        } else {
            this.listViewHeight.setAdapter(this.adapter_wrist);
        }
        this.listViewHeight.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (z) {
                    Body_Adiposity_Index_Calculator.this.tv_heightunit.setText((CharSequence) Body_Adiposity_Index_Calculator.this.arraylist_height.get(i));
                } else {
                    Body_Adiposity_Index_Calculator.this.tv_weightunit.setText((CharSequence) Body_Adiposity_Index_Calculator.this.arraylist_wrist.get(i));
                }
                Body_Adiposity_Index_Calculator.this.popupWindowHeight.dismiss();
            }
        });
        this.popupWindowHeight.setFocusable(true);
        this.popupWindowHeight.setWidth(this.tv_heightunit.getMeasuredWidth());
        this.popupWindowHeight.setHeight(-2);
        this.popupWindowHeight.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight.setContentView(this.listViewHeight);
        return this.popupWindowHeight;
    }


    public void calculate() {
        StringBuilder sb = new StringBuilder();
        sb.append("inserted_waist");
        sb.append(this.inserted_waist);
        Log.d("inserted_waist", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("inserted_height");
        sb2.append(this.inserted_height);
        Log.d("inserted_height", sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("height_unit");
        sb3.append(this.height_unit);
        Log.d("height_unit", sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append("weight_unit");
        sb4.append(this.weight_unit);
        Log.d("weight_unit", sb4.toString());
        if (this.weight_unit.equalsIgnoreCase(getString(R.string.cm))) {
            this.waist = this.inserted_waist;
        } else {
            this.waist = this.inserted_waist * 2.54f;
        }
        if (this.height_unit.equalsIgnoreCase(getString(R.string.cm))) {
            this.height = this.inserted_height;
        } else {
            this.height = this.inserted_height / 0.032808f;
        }
        this.waist /= 100.0f;
        this.height /= 100.0f;
        double d = (double) (this.waist * 100.0f);
        double d2 = (double) this.height;
        double sqrt = Math.sqrt((double) this.height);
        Double.isNaN(d2);
        double d3 = d2 * sqrt;
        Double.isNaN(d);
        this.bai = d / d3;
        this.bai -= 18.0d;
        StringBuilder sb5 = new StringBuilder();
        sb5.append("bai->");
        sb5.append(this.bai);
        Log.d("bai->", sb5.toString());
        Intent intent = new Intent(this, Body_Adiposity_Index_Result.class);
        intent.putExtra("bai", String.format("%.2f", new Object[]{Double.valueOf(this.bai)}));
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
        this.adView.setVisibility(8);
        ActivityCompat.finishAfterTransition(this);
    }

    public void showIntertitial() {
        if (this.sharedPreferenceManager.get_Remove_Ad().booleanValue()) {
            calculate();
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
            calculate();
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
                    Body_Adiposity_Index_Calculator.this.calculate();
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
