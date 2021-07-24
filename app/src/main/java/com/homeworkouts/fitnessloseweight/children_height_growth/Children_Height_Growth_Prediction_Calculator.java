package com.homeworkouts.fitnessloseweight.children_height_growth;

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


public class Children_Height_Growth_Prediction_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_gender;
    ArrayAdapter<String> adapter_height;
    ArrayList<String> arraylist_gender = new ArrayList<>();
    ArrayList<String> arraylist_height = new ArrayList<>();
    float child_predicted_height;
    EditText et_fathers_height;
    EditText et_mother_height;
    float fathers_height;
    String fathers_height_unit;
    String gender;
    GlobalFunction globalFunction;
    float inserted_fathers_height;
    float inserted_mothers_height;
    ImageView iv_back;
    ListView listViewHeight;
    ListView listViewgender;
    String mothers_height_unit;
    float mothes_height;
    private PopupWindow popupWindowHeight_mother;
    private PopupWindow popupWindowgender;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_children_height_growth;
    TextView tv_gender;
    TextView tv_heightunit_father;
    TextView tv_heightunit_mother;
    TextView tv_search_predicted_height;
    TextView tv_select_gender;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.children_height_prediction_calculator);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_children_height_growth = (TextView) findViewById(R.id.tv_children_height_growth);
        this.tv_gender = (TextView) findViewById(R.id.tv_gender);
        this.tv_heightunit_mother = (TextView) findViewById(R.id.tv_heightunit_mother);
        this.tv_heightunit_father = (TextView) findViewById(R.id.tv_heightunit_father);
        this.tv_search_predicted_height = (TextView) findViewById(R.id.tv_search_predicted_height);
        this.tv_select_gender = (TextView) findViewById(R.id.tv_select_gender);
        this.et_fathers_height = (EditText) findViewById(R.id.et_fathers_height);
        this.et_mother_height = (EditText) findViewById(R.id.et_mother_height);
        this.tv_gender.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit_mother.setTypeface(this.typefaceManager.getLight());
        this.tv_heightunit_father.setTypeface(this.typefaceManager.getLight());
        this.tv_search_predicted_height.setTypeface(this.typefaceManager.getBold());
        this.tv_select_gender.setTypeface(this.typefaceManager.getLight());
        this.et_fathers_height.setTypeface(this.typefaceManager.getLight());
        this.et_mother_height.setTypeface(this.typefaceManager.getLight());
        this.tv_children_height_growth.setTypeface(this.typefaceManager.getBold());
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.arraylist_gender.clear();
        this.arraylist_gender.add(getString(R.string.Male));
        this.arraylist_gender.add(getString(R.string.Female));
        this.arraylist_height.clear();
        this.arraylist_height.add(getString(R.string.feet));
        this.arraylist_height.add(getString(R.string.cm));
        this.tv_gender.setOnClickListener(showPopupWindow_gender());
        this.tv_heightunit_mother.setOnClickListener(showPopupWindowHeight_mother());
        this.tv_heightunit_father.setOnClickListener(showPopupWindowHeight_father());
        this.adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_gender);
        this.adapter_height = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_height);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_search_predicted_height.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Children_Height_Growth_Prediction_Calculator.this.et_mother_height.getText().toString().trim().equals("") || Children_Height_Growth_Prediction_Calculator.this.et_mother_height.getText().toString().trim().equals(".")) {
                    Children_Height_Growth_Prediction_Calculator.this.et_mother_height.setError(Children_Height_Growth_Prediction_Calculator.this.getString(R.string.Enter_Mothers_Height));
                } else if (Children_Height_Growth_Prediction_Calculator.this.et_fathers_height.getText().toString().trim().equals("") || Children_Height_Growth_Prediction_Calculator.this.et_fathers_height.getText().toString().trim().equals(".")) {
                    Children_Height_Growth_Prediction_Calculator.this.et_fathers_height.setError(Children_Height_Growth_Prediction_Calculator.this.getString(R.string.Enter_Fathers_Height));
                } else {
                    Children_Height_Growth_Prediction_Calculator.this.gender = Children_Height_Growth_Prediction_Calculator.this.tv_gender.getText().toString();
                    Children_Height_Growth_Prediction_Calculator.this.inserted_mothers_height = Float.parseFloat(Children_Height_Growth_Prediction_Calculator.this.et_mother_height.getText().toString());
                    Children_Height_Growth_Prediction_Calculator.this.inserted_fathers_height = Float.parseFloat(Children_Height_Growth_Prediction_Calculator.this.et_fathers_height.getText().toString());
                    Children_Height_Growth_Prediction_Calculator.this.mothers_height_unit = Children_Height_Growth_Prediction_Calculator.this.tv_heightunit_mother.getText().toString();
                    Children_Height_Growth_Prediction_Calculator.this.fathers_height_unit = Children_Height_Growth_Prediction_Calculator.this.tv_heightunit_father.getText().toString();
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("random_number==>");
                    sb.append(random);
                    printStream.println(sb.toString());
                    if (random == 2) {
                        Children_Height_Growth_Prediction_Calculator.this.showIntertitial();
                    } else {
                        Children_Height_Growth_Prediction_Calculator.this.calculate();
                    }
                }
            }
        });
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Children_Height_Growth_Prediction_Calculator.this.onBackPressed();
            }
        });
    }

    private OnClickListener showPopupWindow_gender() {
        return new OnClickListener() {
            public void onClick(View view) {
                Children_Height_Growth_Prediction_Calculator.this.popupWindowgender().showAsDropDown(view, 0, 0);
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
                sb2.append((String) Children_Height_Growth_Prediction_Calculator.this.arraylist_gender.get(i));
                Log.d("arraylist_weigth", sb2.toString());
                Children_Height_Growth_Prediction_Calculator.this.tv_gender.setText((CharSequence) Children_Height_Growth_Prediction_Calculator.this.arraylist_gender.get(i));
                Children_Height_Growth_Prediction_Calculator.this.dismissPopupgender();
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

    private OnClickListener showPopupWindowHeight_mother() {
        return new OnClickListener() {
            public void onClick(View view) {
                Children_Height_Growth_Prediction_Calculator.this.showPopupWindowHeight_mother1().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow showPopupWindowHeight_mother1() {
        this.popupWindowHeight_mother = new PopupWindow(this);
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
                sb2.append((String) Children_Height_Growth_Prediction_Calculator.this.arraylist_height.get(i));
                Log.d("arraylist_height", sb2.toString());
                Children_Height_Growth_Prediction_Calculator.this.tv_heightunit_mother.setText((CharSequence) Children_Height_Growth_Prediction_Calculator.this.arraylist_height.get(i));
                Children_Height_Growth_Prediction_Calculator.this.dismissPopupHeightmother();
            }
        });
        this.popupWindowHeight_mother.setFocusable(true);
        this.popupWindowHeight_mother.setWidth(this.tv_heightunit_mother.getMeasuredWidth());
        this.popupWindowHeight_mother.setHeight(-2);
        this.popupWindowHeight_mother.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight_mother.setContentView(this.listViewHeight);
        return this.popupWindowHeight_mother;
    }


    public void dismissPopupHeightmother() {
        if (this.popupWindowHeight_mother != null) {
            this.popupWindowHeight_mother.dismiss();
        }
    }

    private OnClickListener showPopupWindowHeight_father() {
        return new OnClickListener() {
            public void onClick(View view) {
                Children_Height_Growth_Prediction_Calculator.this.showPopupWindowHeight_father1().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow showPopupWindowHeight_father1() {
        this.popupWindowHeight_mother = new PopupWindow(this);
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
                sb2.append((String) Children_Height_Growth_Prediction_Calculator.this.arraylist_height.get(i));
                Log.d("arraylist_height", sb2.toString());
                Children_Height_Growth_Prediction_Calculator.this.tv_heightunit_father.setText((CharSequence) Children_Height_Growth_Prediction_Calculator.this.arraylist_height.get(i));
                Children_Height_Growth_Prediction_Calculator.this.dismissPopupHeightmfatehr();
            }
        });
        this.popupWindowHeight_mother.setFocusable(true);
        this.popupWindowHeight_mother.setWidth(this.tv_heightunit_father.getMeasuredWidth());
        this.popupWindowHeight_mother.setHeight(-2);
        this.popupWindowHeight_mother.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowHeight_mother.setContentView(this.listViewHeight);
        return this.popupWindowHeight_mother;
    }


    public void dismissPopupHeightmfatehr() {
        if (this.popupWindowHeight_mother != null) {
            this.popupWindowHeight_mother.dismiss();
        }
    }

    public void calculate() {
        if (this.mothers_height_unit.equalsIgnoreCase(getString(R.string.feet))) {
            this.mothes_height = this.inserted_mothers_height;
        } else {
            this.mothes_height = this.inserted_mothers_height * 0.032808f;
        }
        if (this.fathers_height_unit.equalsIgnoreCase(getString(R.string.feet))) {
            this.fathers_height = this.inserted_fathers_height;
        } else {
            this.fathers_height = this.inserted_fathers_height * 0.032808f;
        }
        if (this.gender.equalsIgnoreCase(getString(R.string.Male))) {
            this.child_predicted_height = (this.mothes_height + this.fathers_height) / 2.0f;
            this.child_predicted_height *= 12.0f;
            this.child_predicted_height += 5.0f;
            this.child_predicted_height /= 12.0f;
        } else {
            this.child_predicted_height = (this.mothes_height + this.fathers_height) / 2.0f;
            this.child_predicted_height *= 12.0f;
            this.child_predicted_height -= 5.0f;
            this.child_predicted_height /= 12.0f;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("child_predicted_height");
        sb.append(this.child_predicted_height);
        Log.d("child_predicted_height", sb.toString());
        Intent intent = new Intent(this, Children_Predicted_Height_Result.class);
        intent.putExtra("child_predicted_height", this.child_predicted_height);
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
                    Children_Height_Growth_Prediction_Calculator.this.calculate();
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
