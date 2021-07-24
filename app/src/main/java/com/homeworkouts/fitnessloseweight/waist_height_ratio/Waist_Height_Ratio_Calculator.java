package com.homeworkouts.fitnessloseweight.waist_height_ratio;

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


public class Waist_Height_Ratio_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_height;
    ArrayAdapter<String> adapter_wrist;
    ArrayList<String> arraylist_gender = new ArrayList<>();
    ArrayList<String> arraylist_height = new ArrayList<>();
    ArrayList<String> arraylist_wrist = new ArrayList<>();
    EditText et_height;
    EditText et_weight;
    String gender = "";
    GlobalFunction globalFunction;
    float height;
    String height_unit;
    float inserted_height;
    float inserted_waist;
    ImageView iv_back;
    ListView listViewGender;
    ListView listViewHeight;
    private PopupWindow popupWindowGender;

    public PopupWindow popupWindowHeight;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_gender;
    TextView tv_genderunit;
    TextView tv_heightunit;
    TextView tv_search_whr;
    TextView tv_waist_height_ratio;
    TextView tv_weightunit;
    TypefaceManager typefaceManager;
    float waist;
    double waist_height_ratio;
    String weight_unit;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.waist_height_ratio);
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
        this.tv_waist_height_ratio = (TextView) findViewById(R.id.tv_waist_height_ratio);
        this.tv_heightunit = (TextView) findViewById(R.id.tv_heightunit);
        this.tv_weightunit = (TextView) findViewById(R.id.tv_weightunit);
        this.tv_search_whr = (TextView) findViewById(R.id.tv_search_whr);
        this.tv_genderunit = (TextView) findViewById(R.id.tv_genderunit);
        this.tv_gender = (TextView) findViewById(R.id.tv_gender);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_waist_height_ratio.setTypeface(this.typefaceManager.getBold());
        this.et_height.setTypeface(this.typefaceManager.getLight());
        this.et_weight.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_weightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_search_whr.setTypeface(this.typefaceManager.getBold());
        this.tv_genderunit.setTypeface(this.typefaceManager.getLight());
        this.tv_gender.setTypeface(this.typefaceManager.getLight());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.height_unit = getString(R.string.feet);
        this.weight_unit = getString(R.string.lbs);
        this.tv_heightunit.setOnClickListener(showPopupWindowHeight(true));
        this.tv_weightunit.setOnClickListener(showPopupWindowHeight(false));
        this.tv_gender.setOnClickListener(showPopupWindowGender());
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
        this.tv_search_whr.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Waist_Height_Ratio_Calculator.this.et_height.getText().toString().trim().equals("") || Waist_Height_Ratio_Calculator.this.et_height.getText().toString().trim().equals(".")) {
                    Waist_Height_Ratio_Calculator.this.et_height.setError(Waist_Height_Ratio_Calculator.this.getString(R.string.Enter_Height));
                } else if (Waist_Height_Ratio_Calculator.this.et_weight.getText().toString().trim().equals("") || Waist_Height_Ratio_Calculator.this.et_weight.getText().toString().trim().equals(".")) {
                    Waist_Height_Ratio_Calculator.this.et_weight.setError(Waist_Height_Ratio_Calculator.this.getString(R.string.Enter_Weight));
                } else {
                    Waist_Height_Ratio_Calculator.this.height_unit = Waist_Height_Ratio_Calculator.this.tv_heightunit.getText().toString();
                    Waist_Height_Ratio_Calculator.this.weight_unit = Waist_Height_Ratio_Calculator.this.tv_weightunit.getText().toString();
                    Waist_Height_Ratio_Calculator.this.inserted_height = Float.parseFloat(Waist_Height_Ratio_Calculator.this.et_height.getText().toString());
                    Waist_Height_Ratio_Calculator.this.inserted_waist = Float.parseFloat(Waist_Height_Ratio_Calculator.this.et_weight.getText().toString());
                    StringBuilder sb = new StringBuilder();
                    sb.append("inserted_waist");
                    sb.append(Waist_Height_Ratio_Calculator.this.inserted_waist);
                    Log.d("inserted_waist", sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("inserted_height");
                    sb2.append(Waist_Height_Ratio_Calculator.this.inserted_height);
                    Log.d("inserted_height", sb2.toString());
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("random_number==>");
                    sb3.append(random);
                    printStream.println(sb3.toString());
                    if (random == 2) {
                        Waist_Height_Ratio_Calculator.this.showIntertitial();
                    } else {
                        Waist_Height_Ratio_Calculator.this.calculate();
                    }
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Waist_Height_Ratio_Calculator.this.onBackPressed();
            }
        });
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
        this.gender = this.tv_gender.getText().toString().trim();
        this.waist_height_ratio = (double) ((this.waist / this.height) * 100.0f);
        String str = "";
        if (this.gender.equals(getString(R.string.Male))) {
            if (this.waist_height_ratio < 35.0d) {
                str = getString(R.string.Abnormally_Slim);
            } else if (this.waist_height_ratio >= 35.0d && this.waist_height_ratio < 43.0d) {
                str = getString(R.string.Extremely_slim);
            } else if (this.waist_height_ratio >= 43.0d && this.waist_height_ratio < 46.0d) {
                str = getString(R.string.Slender);
            } else if (this.waist_height_ratio >= 46.0d && this.waist_height_ratio < 53.0d) {
                str = getString(R.string.Healthy);
            } else if (this.waist_height_ratio >= 53.0d && this.waist_height_ratio < 58.0d) {
                str = getString(R.string.Overweight);
            } else if (this.waist_height_ratio >= 58.0d && this.waist_height_ratio < 63.0d) {
                str = getString(R.string.Extremely_Overweight);
            } else if (this.waist_height_ratio >= 63.0d) {
                str = getString(R.string.Highly_Obese);
            }
        } else if (this.waist_height_ratio < 35.0d) {
            str = getString(R.string.Abnormally_Slim);
        } else if (this.waist_height_ratio >= 35.0d && this.waist_height_ratio < 42.0d) {
            str = getString(R.string.Extremely_slim);
        } else if (this.waist_height_ratio >= 42.0d && this.waist_height_ratio < 46.0d) {
            str = getString(R.string.Slender);
        } else if (this.waist_height_ratio >= 46.0d && this.waist_height_ratio < 49.0d) {
            str = getString(R.string.Healthy);
        } else if (this.waist_height_ratio >= 49.0d && this.waist_height_ratio < 54.0d) {
            str = getString(R.string.Overweight);
        } else if (this.waist_height_ratio >= 54.0d && this.waist_height_ratio < 58.0d) {
            str = getString(R.string.Extremely_Overweight);
        } else if (this.waist_height_ratio >= 58.0d) {
            str = getString(R.string.Highly_Obese);
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("waist_height_ratio->");
        sb5.append(String.format("%.2f", new Object[]{Double.valueOf(this.waist_height_ratio)}));
        Log.d("waist_height_ratio->", sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append("gender->");
        sb6.append(this.gender);
        Log.d("gender->", sb6.toString());
        StringBuilder sb7 = new StringBuilder();
        sb7.append("Body->");
        sb7.append(str);
        Log.d("Body->", sb7.toString());
        Intent intent = new Intent(this, Waist_Height_Ratio_Result.class);
        intent.putExtra("waist_height_ratio", String.format("%.2f", new Object[]{Double.valueOf(this.waist_height_ratio)}));
        intent.putExtra("Body", str);
        startActivity(intent);
    }

    private OnClickListener showPopupWindowHeight(final boolean z) {
        return new OnClickListener() {
            public void onClick(View view) {
                Waist_Height_Ratio_Calculator.this.popupWindowHeight(z).showAsDropDown(view, 0, 0);
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
                    Waist_Height_Ratio_Calculator.this.tv_heightunit.setText((CharSequence) Waist_Height_Ratio_Calculator.this.arraylist_height.get(i));
                } else {
                    Waist_Height_Ratio_Calculator.this.tv_weightunit.setText((CharSequence) Waist_Height_Ratio_Calculator.this.arraylist_wrist.get(i));
                }
                Waist_Height_Ratio_Calculator.this.popupWindowHeight.dismiss();
            }
        });
        this.popupWindowHeight.setFocusable(true);
        this.popupWindowHeight.setWidth(this.tv_heightunit.getMeasuredWidth());
        this.popupWindowHeight.setHeight(-2);
        this.popupWindowHeight.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight.setContentView(this.listViewHeight);
        return this.popupWindowHeight;
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

    private OnClickListener showPopupWindowGender() {
        return new OnClickListener() {
            public void onClick(View view) {
                Waist_Height_Ratio_Calculator.this.popupWindowGender().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowGender() {
        this.popupWindowGender = new PopupWindow(this);
        this.listViewGender = new ListView(this);
        this.listViewGender.setDividerHeight(0);
        this.listViewGender.setAdapter(this.adapter_gender);
        this.listViewGender.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Waist_Height_Ratio_Calculator.this.tv_gender.setText((CharSequence) Waist_Height_Ratio_Calculator.this.arraylist_gender.get(i));
                Waist_Height_Ratio_Calculator.this.tv_genderunit.setText((CharSequence) Waist_Height_Ratio_Calculator.this.arraylist_gender.get(i));
                Waist_Height_Ratio_Calculator.this.dismissPopupGender();
            }
        });
        this.popupWindowGender.setFocusable(true);
        this.popupWindowGender.setWidth(this.tv_gender.getMeasuredWidth());
        this.popupWindowGender.setHeight(-2);
        this.popupWindowGender.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowGender.setContentView(this.listViewGender);
        return this.popupWindowGender;
    }


    public void dismissPopupGender() {
        if (this.popupWindowGender != null) {
            this.popupWindowGender.dismiss();
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
                    Waist_Height_Ratio_Calculator.this.calculate();
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
