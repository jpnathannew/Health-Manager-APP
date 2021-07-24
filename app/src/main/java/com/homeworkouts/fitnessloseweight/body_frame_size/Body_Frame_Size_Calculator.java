package com.homeworkouts.fitnessloseweight.body_frame_size;

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

//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Body_Frame_Size_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_height;
    ArrayAdapter<String> adapter_wrist;
    ArrayList<String> arraylist_gender = new ArrayList<>();
    ArrayList<String> arraylist_height = new ArrayList<>();
    ArrayList<String> arraylist_wrist = new ArrayList<>();
    float bmi_height;
    float bmi_wrist;
    String body_frame = "";
    EditText et_height;
    EditText et_weight;
    String gender = "";
    GlobalFunction globalFunction;
    String height_unit;
    float inserted_height;
    float inserted_weight;
    ImageView iv_back;
    String large_bodyframe = "Large";
    ListView listViewGender;
    ListView listViewHeight;
    String medium_bodyframe = "Medium";
    private PopupWindow popupWindowGender;

    public PopupWindow popupWindowHeight;
    SharedPreferenceManager sharedPreferenceManager;
    String small_bodyframe = "Small";
    TextView tv_body_frame;
    TextView tv_gender;
    TextView tv_genderunit;
    TextView tv_heightunit;
    TextView tv_search_bmi;
    TextView tv_weightunit;
    TypefaceManager typefaceManager;
    String weight_unit;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.body_frame_calculator);
        this.globalFunction = new GlobalFunction(this);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.small_bodyframe = getString(R.string.Body_frame_text_small);
        this.medium_bodyframe = getString(R.string.Body_frame_text_medium);
        this.large_bodyframe = getString(R.string.Body_frame_text_large);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.et_height = (EditText) findViewById(R.id.et_height);
        this.et_weight = (EditText) findViewById(R.id.et_weight);
        this.tv_body_frame = (TextView) findViewById(R.id.tv_body_frame);
        this.tv_heightunit = (TextView) findViewById(R.id.tv_heightunit);
        this.tv_weightunit = (TextView) findViewById(R.id.tv_weightunit);
        this.tv_search_bmi = (TextView) findViewById(R.id.tv_search_bmi);
        this.tv_genderunit = (TextView) findViewById(R.id.tv_genderunit);
        this.tv_gender = (TextView) findViewById(R.id.tv_gender);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_body_frame.setTypeface(this.typefaceManager.getBold());
        this.et_height.setTypeface(this.typefaceManager.getLight());
        this.et_weight.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_weightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_search_bmi.setTypeface(this.typefaceManager.getBold());
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
        this.arraylist_height.clear();
        this.arraylist_height.add(getString(R.string.feet));
        this.arraylist_height.add(getString(R.string.cm));
        this.arraylist_wrist.clear();
        this.arraylist_wrist.add(getString(R.string.cm));
        this.arraylist_wrist.add(getString(R.string.inch));
        this.arraylist_gender.clear();
        this.arraylist_gender.add(getString(R.string.Male));
        this.arraylist_gender.add(getString(R.string.Female));
        this.adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_gender);
        this.adapter_height = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_height);
        this.adapter_wrist = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_wrist);
        this.tv_search_bmi.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Body_Frame_Size_Calculator.this.et_height.getText().toString().trim().equals("") || Body_Frame_Size_Calculator.this.et_height.getText().toString().trim().equals(".")) {
                    Body_Frame_Size_Calculator.this.et_height.setError(Body_Frame_Size_Calculator.this.getString(R.string.Enter_Height));
                } else if (Body_Frame_Size_Calculator.this.et_weight.getText().toString().trim().equals("") || Body_Frame_Size_Calculator.this.et_weight.getText().toString().trim().equals(".")) {
                    Body_Frame_Size_Calculator.this.et_weight.setError(Body_Frame_Size_Calculator.this.getString(R.string.Enter_Weight));
                } else {
                    Body_Frame_Size_Calculator.this.height_unit = Body_Frame_Size_Calculator.this.tv_heightunit.getText().toString();
                    Body_Frame_Size_Calculator.this.weight_unit = Body_Frame_Size_Calculator.this.tv_weightunit.getText().toString();
                    Body_Frame_Size_Calculator.this.inserted_weight = Float.parseFloat(Body_Frame_Size_Calculator.this.et_weight.getText().toString());
                    Body_Frame_Size_Calculator.this.inserted_height = Float.parseFloat(Body_Frame_Size_Calculator.this.et_height.getText().toString());
                    StringBuilder sb = new StringBuilder();
                    sb.append("inserted_weight");
                    sb.append(Body_Frame_Size_Calculator.this.inserted_weight);
                    Log.d("inserted_weight", sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("inserted_height");
                    sb2.append(Body_Frame_Size_Calculator.this.inserted_height);
                    Log.d("inserted_height", sb2.toString());
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("random_number==>");
                    sb3.append(random);
                    printStream.println(sb3.toString());
                    if (random == 2) {
                        Body_Frame_Size_Calculator.this.showIntertitial();
                    } else {
                        Body_Frame_Size_Calculator.this.calculate();
                    }
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Body_Frame_Size_Calculator.this.onBackPressed();
            }
        });
    }

    private OnClickListener showPopupWindowHeight(final boolean z) {
        return new OnClickListener() {
            public void onClick(View view) {
                Body_Frame_Size_Calculator.this.popupWindowHeight(z).showAsDropDown(view, 0, 0);
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
                    Body_Frame_Size_Calculator.this.tv_heightunit.setText((CharSequence) Body_Frame_Size_Calculator.this.arraylist_height.get(i));
                } else {
                    Body_Frame_Size_Calculator.this.tv_weightunit.setText((CharSequence) Body_Frame_Size_Calculator.this.arraylist_wrist.get(i));
                }
                Body_Frame_Size_Calculator.this.popupWindowHeight.dismiss();
            }
        });
        this.popupWindowHeight.setFocusable(true);
        this.popupWindowHeight.setWidth(this.tv_heightunit.getMeasuredWidth());
        this.popupWindowHeight.setHeight(-2);
        this.popupWindowHeight.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight.setContentView(this.listViewHeight);
        return this.popupWindowHeight;
    }

    private OnClickListener showPopupWindowGender() {
        return new OnClickListener() {
            public void onClick(View view) {
                Body_Frame_Size_Calculator.this.popupWindowGender().showAsDropDown(view, 0, 0);
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
                Body_Frame_Size_Calculator.this.tv_gender.setText((CharSequence) Body_Frame_Size_Calculator.this.arraylist_gender.get(i));
                Body_Frame_Size_Calculator.this.tv_genderunit.setText((CharSequence) Body_Frame_Size_Calculator.this.arraylist_gender.get(i));
                Body_Frame_Size_Calculator.this.dismissPopupGender();
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


    public void calculate() {
        StringBuilder sb = new StringBuilder();
        sb.append("inserted_weight");
        sb.append(this.inserted_weight);
        Log.d("inserted_weight", sb.toString());
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
        if (this.height_unit.equalsIgnoreCase(getString(R.string.feet))) {
            this.bmi_height = this.inserted_height;
        } else {
            this.bmi_height = this.inserted_height * 0.032808f;
        }
        if (this.weight_unit.equalsIgnoreCase(getString(R.string.inch))) {
            this.bmi_wrist = this.inserted_weight;
        } else {
            this.bmi_wrist = this.inserted_weight / 2.54f;
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("");
        sb5.append(this.bmi_wrist);
        Log.d("wrist_in_inch->", sb5.toString());
        this.gender = this.tv_gender.getText().toString().trim();
        if (this.gender.equalsIgnoreCase("female")) {
            if (((double) this.bmi_height) < 5.2d) {
                if (((double) this.bmi_wrist) < 5.5d) {
                    this.body_frame = this.small_bodyframe;
                } else if (((double) this.bmi_wrist) >= 5.5d && ((double) this.bmi_wrist) < 5.75d) {
                    this.body_frame = this.medium_bodyframe;
                } else if (((double) this.bmi_wrist) >= 5.75d) {
                    this.body_frame = this.large_bodyframe;
                }
            } else if (((double) this.bmi_height) >= 5.2d || ((double) this.bmi_height) <= 5.5d) {
                if (((double) this.bmi_wrist) < 6.0d) {
                    this.body_frame = this.small_bodyframe;
                } else if (((double) this.bmi_wrist) >= 6.0d && ((double) this.bmi_wrist) < 6.25d) {
                    this.body_frame = this.medium_bodyframe;
                } else if (((double) this.bmi_wrist) >= 6.25d) {
                    this.body_frame = this.large_bodyframe;
                }
            } else if (((double) this.bmi_height) > 5.5d) {
                if (((double) this.bmi_wrist) < 6.25d) {
                    this.body_frame = this.small_bodyframe;
                } else if (((double) this.bmi_wrist) >= 6.25d && ((double) this.bmi_wrist) < 6.5d) {
                    this.body_frame = this.medium_bodyframe;
                } else if (((double) this.bmi_wrist) >= 6.5d) {
                    this.body_frame = this.large_bodyframe;
                }
            }
        } else if (((double) this.bmi_height) > 5.5d) {
            if (((double) this.bmi_wrist) < 6.5d) {
                this.body_frame = this.small_bodyframe;
            } else if (((double) this.bmi_wrist) >= 6.5d && ((double) this.bmi_wrist) < 7.5d) {
                this.body_frame = this.medium_bodyframe;
            } else if (((double) this.bmi_wrist) >= 7.5d) {
                this.body_frame = this.large_bodyframe;
            }
        } else if (((double) this.bmi_wrist) < 6.5d) {
            this.body_frame = this.small_bodyframe;
        } else if (((double) this.bmi_wrist) >= 6.5d && ((double) this.bmi_wrist) < 7.5d) {
            this.body_frame = this.medium_bodyframe;
        } else if (((double) this.bmi_wrist) >= 7.5d) {
            this.body_frame = this.large_bodyframe;
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append("body_frame->");
        sb6.append(this.body_frame);
        Log.d("body_frame->", sb6.toString());
        Intent intent = new Intent(this, BodyFrame_Result.class);
        intent.putExtra("body_frame", this.body_frame);
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
                    Body_Frame_Size_Calculator.this.calculate();
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
