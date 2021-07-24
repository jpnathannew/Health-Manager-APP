package com.homeworkouts.fitnessloseweight.ideal_body_weight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
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


public class Ideal_Body_Weight_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_bodyframe;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_height;
    float additional_height;
    ArrayList<String> arraylist_bodyframe = new ArrayList<>();
    ArrayList<String> arraylist_gender = new ArrayList<>();
    ArrayList<String> arraylist_height = new ArrayList<>();
    String body_ftrame;
    float bodyframe_height;
    EditText et_height;
    String gender;
    GlobalFunction globalFunction;
    String height_unit;
    float ideal_body_weight;
    float inserted_height;
    ImageView iv_back;
    ListView listViewHeight;
    ListView listViewbodyframe;
    ListView listViewgender;
    private PopupWindow popupWindowHeight;
    private PopupWindow popupWindowbodyframe;
    private PopupWindow popupWindowgender;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_body_body_weight;
    TextView tv_body_frame;
    TextView tv_bodyframe;
    TextView tv_gender;
    TextView tv_heightunit;
    TextView tv_search_ideal_bodyweight;
    TextView tv_select;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ideal_body_weight_calculator);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.et_height = (EditText) findViewById(R.id.et_height);
        this.tv_body_body_weight = (TextView) findViewById(R.id.tv_body_body_weight);
        this.tv_heightunit = (TextView) findViewById(R.id.tv_heightunit);
        this.tv_gender = (TextView) findViewById(R.id.tv_gender);
        this.tv_body_frame = (TextView) findViewById(R.id.tv_body_frame);
        this.tv_search_ideal_bodyweight = (TextView) findViewById(R.id.tv_search_ideal_bodyweight);
        this.tv_select = (TextView) findViewById(R.id.tv_select);
        this.tv_bodyframe = (TextView) findViewById(R.id.tv_bodyframe);
        this.et_height.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit.setTypeface(this.typefaceManager.getLight());
        this.tv_gender.setTypeface(this.typefaceManager.getLight());
        this.tv_body_frame.setTypeface(this.typefaceManager.getLight());
        this.tv_search_ideal_bodyweight.setTypeface(this.typefaceManager.getBold());
        this.tv_select.setTypeface(this.typefaceManager.getLight());
        this.tv_bodyframe.setTypeface(this.typefaceManager.getLight());
        this.tv_body_body_weight.setTypeface(this.typefaceManager.getBold());
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.tv_heightunit.setOnClickListener(showPopupWindowHeight());
        this.tv_gender.setOnClickListener(showPopupWindow_gender());
        this.tv_body_frame.setOnClickListener(showPopupWindow_bodyframe());
        this.arraylist_height.clear();
        this.arraylist_height.add(getString(R.string.feet));
        this.arraylist_height.add(getString(R.string.cm));
        this.arraylist_bodyframe.clear();
        this.arraylist_bodyframe.add(getString(R.string.Light));
        this.arraylist_bodyframe.add(getString(R.string.Medium));
        this.arraylist_bodyframe.add(getString(R.string.Heavy));
        this.arraylist_gender.clear();
        this.arraylist_gender.add(getString(R.string.Male));
        this.arraylist_gender.add(getString(R.string.Female));
        this.adapter_height = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_height);
        this.adapter_bodyframe = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_bodyframe);
        this.adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_gender);
        this.tv_search_ideal_bodyweight.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Ideal_Body_Weight_Calculator.this.et_height.getText().toString().trim().equals("") || Ideal_Body_Weight_Calculator.this.et_height.getText().toString().trim().equals(".")) {
                    Ideal_Body_Weight_Calculator.this.et_height.setError(Ideal_Body_Weight_Calculator.this.getString(R.string.Enter_Height));
                    return;
                }
                Ideal_Body_Weight_Calculator.this.inserted_height = Float.parseFloat(Ideal_Body_Weight_Calculator.this.et_height.getText().toString().trim());
                Ideal_Body_Weight_Calculator.this.gender = Ideal_Body_Weight_Calculator.this.tv_gender.getText().toString().trim();
                Ideal_Body_Weight_Calculator.this.body_ftrame = Ideal_Body_Weight_Calculator.this.tv_body_frame.getText().toString();
                Ideal_Body_Weight_Calculator.this.height_unit = Ideal_Body_Weight_Calculator.this.tv_heightunit.getText().toString();
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Ideal_Body_Weight_Calculator.this.showIntertitial();
                } else {
                    Ideal_Body_Weight_Calculator.this.calculate();
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Ideal_Body_Weight_Calculator.this.onBackPressed();
            }
        });
    }

    private OnClickListener showPopupWindowHeight() {
        return new OnClickListener() {
            public void onClick(View view) {
                Ideal_Body_Weight_Calculator.this.popupWindowHeight().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowHeight() {
        this.popupWindowHeight = new PopupWindow(this);
        this.listViewHeight = new ListView(this);
        this.listViewHeight.setDividerHeight(0);
        this.listViewHeight.setAdapter(this.adapter_height);
        this.listViewHeight.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("position->");
                sb.append(i);
                Log.d("position", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("arraylist_height->");
                sb2.append((String) Ideal_Body_Weight_Calculator.this.arraylist_height.get(i));
                Log.d("arraylist_height", sb2.toString());
                Ideal_Body_Weight_Calculator.this.tv_heightunit.setText((CharSequence) Ideal_Body_Weight_Calculator.this.arraylist_height.get(i));
                Ideal_Body_Weight_Calculator.this.dismissPopupHeight();
            }
        });
        this.popupWindowHeight.setFocusable(true);
        this.popupWindowHeight.setWidth(this.tv_heightunit.getMeasuredWidth());
        this.popupWindowHeight.setHeight(-2);
        this.popupWindowHeight.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight.setContentView(this.listViewHeight);
        return this.popupWindowHeight;
    }


    public void dismissPopupHeight() {
        if (this.popupWindowHeight != null) {
            this.popupWindowHeight.dismiss();
        }
    }

    private OnClickListener showPopupWindow_gender() {
        return new OnClickListener() {
            public void onClick(View view) {
                Ideal_Body_Weight_Calculator.this.popupWindowgender().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowgender() {
        this.popupWindowgender = new PopupWindow(this);
        this.listViewgender = new ListView(this);
        this.listViewgender.setDividerHeight(0);
        this.listViewgender.setAdapter(this.adapter_gender);
        this.listViewgender.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("position->");
                sb.append(i);
                Log.d("arraylist_gender", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("arraylist_gender->");
                sb2.append((String) Ideal_Body_Weight_Calculator.this.arraylist_gender.get(i));
                Log.d("arraylist_bodyframe", sb2.toString());
                Ideal_Body_Weight_Calculator.this.tv_gender.setText((CharSequence) Ideal_Body_Weight_Calculator.this.arraylist_gender.get(i));
                Ideal_Body_Weight_Calculator.this.dismissPopupgender();
            }
        });
        this.popupWindowgender.setFocusable(true);
        this.popupWindowgender.setWidth(this.tv_gender.getMeasuredWidth());
        this.popupWindowgender.setHeight(-2);
        this.popupWindowgender.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowgender.setContentView(this.listViewgender);
        return this.popupWindowgender;
    }


    public void dismissPopupgender() {
        if (this.popupWindowgender != null) {
            this.popupWindowgender.dismiss();
        }
    }

    private OnClickListener showPopupWindow_bodyframe() {
        return new OnClickListener() {
            public void onClick(View view) {
                Ideal_Body_Weight_Calculator.this.popupWindowbodyframe().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowbodyframe() {
        this.popupWindowbodyframe = new PopupWindow(this);
        this.listViewbodyframe = new ListView(this);
        this.listViewbodyframe.setDividerHeight(0);
        this.listViewbodyframe.setAdapter(this.adapter_bodyframe);
        this.listViewbodyframe.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("position->");
                sb.append(i);
                Log.d("position", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("arraylist_bodyframe->");
                sb2.append((String) Ideal_Body_Weight_Calculator.this.arraylist_bodyframe.get(i));
                Log.d("arraylist_bodyframe", sb2.toString());
                Ideal_Body_Weight_Calculator.this.tv_body_frame.setText((CharSequence) Ideal_Body_Weight_Calculator.this.arraylist_bodyframe.get(i));
                Ideal_Body_Weight_Calculator.this.dismissPopupTopics();
            }
        });
        this.popupWindowbodyframe.setFocusable(true);
        this.popupWindowbodyframe.setWidth(this.tv_body_frame.getMeasuredWidth());
        this.popupWindowbodyframe.setHeight(-2);
        this.popupWindowbodyframe.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowbodyframe.setContentView(this.listViewbodyframe);
        return this.popupWindowbodyframe;
    }


    public void dismissPopupTopics() {
        if (this.popupWindowbodyframe != null) {
            this.popupWindowbodyframe.dismiss();
        }
    }

    public void calculate() {
        if (this.height_unit.equalsIgnoreCase(getString(R.string.feet))) {
            this.bodyframe_height = this.inserted_height * 12.0f;
        } else {
            this.bodyframe_height = this.inserted_height * 0.393f;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("bodyframe_height");
        sb.append(this.bodyframe_height);
        Log.d("bodyframe_height", sb.toString());
        if (this.gender.equals(getString(R.string.Female))) {
            if (this.bodyframe_height > 60.0f) {
                this.additional_height = this.bodyframe_height - 60.0f;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("additional_height");
                sb2.append(this.additional_height);
                Log.d("additional_height", sb2.toString());
                this.ideal_body_weight = this.additional_height * 2.3f;
                this.ideal_body_weight += 45.5f;
            } else if (this.bodyframe_height <= 60.0f) {
                this.additional_height = 60.0f - this.bodyframe_height;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("additional_height");
                sb3.append(this.additional_height);
                Log.d("additional_height", sb3.toString());
                this.ideal_body_weight = this.additional_height * 2.3f;
                this.ideal_body_weight = 45.5f - this.ideal_body_weight;
            }
        } else if (this.bodyframe_height > 60.0f) {
            this.additional_height = this.bodyframe_height - 60.0f;
            this.ideal_body_weight = this.additional_height * 2.3f;
            this.ideal_body_weight += 50.0f;
        } else if (this.bodyframe_height <= 60.0f) {
            this.additional_height = 60.0f - this.bodyframe_height;
            this.ideal_body_weight = this.additional_height * 2.3f;
            this.ideal_body_weight = 50.0f - this.ideal_body_weight;
        }
        if (this.ideal_body_weight < 0.0f) {
            this.ideal_body_weight = 0.0f;
        }
        Intent intent = new Intent(this, Ideal_Body_Weight_Result.class);
        intent.putExtra("ideal_body_weight", this.ideal_body_weight);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
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
                    Ideal_Body_Weight_Calculator.this.calculate();
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

    public void onBackPressed() {
        this.adView.setVisibility(8);
        ActivityCompat.finishAfterTransition(this);
    }
}
