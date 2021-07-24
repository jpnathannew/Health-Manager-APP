package com.homeworkouts.fitnessloseweight.smoking_risk;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.general.MyApplication;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;


public class Smoking_Risk_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_gender;
    ArrayList<String> arraylist_gender = new ArrayList<>();
    String end_date;
    EditText et_no_of_cigarettes;

    public int from_Day;

    public int from_Month;

    public int from_Year;
    GlobalFunction globalFunction;
    ImageView iv_back;
    ListView listViewGender;
    String msg = "";
    private PopupWindow popupWindowGender;
    SharedPreferenceManager sharedPreferenceManager;
    String start_date;
    int timePerDay;
    int totalTimeMin;
    int totalTimehr;
    int total_cigarettes;
    int total_days = 1;
    TextView tv_enddate;
    TextView tv_gender;
    TextView tv_genderunit;
    TextView tv_search_bloodsmokingrisk;
    TextView tv_smoking_risk;
    TextView tv_startdate;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.smoking_risk);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.et_no_of_cigarettes = (EditText) findViewById(R.id.et_no_of_cigarettes);
        this.tv_smoking_risk = (TextView) findViewById(R.id.tv_smoking_risk);
        this.tv_startdate = (TextView) findViewById(R.id.tv_startdate);
        this.tv_enddate = (TextView) findViewById(R.id.tv_enddate);
        this.tv_genderunit = (TextView) findViewById(R.id.tv_genderunit);
        this.tv_gender = (TextView) findViewById(R.id.tv_gender);
        this.tv_search_bloodsmokingrisk = (TextView) findViewById(R.id.tv_search_bloodsmokingrisk);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.adView = (AdView) findViewById(R.id.adView);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        this.start_date = getDateTime();
        this.end_date = getDateTime();
        this.tv_startdate.setText(this.start_date);
        this.tv_enddate.setText(this.end_date);
        this.tv_smoking_risk.setTypeface(this.typefaceManager.getBold());
        this.tv_search_bloodsmokingrisk.setTypeface(this.typefaceManager.getBold());
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.tv_gender.setOnClickListener(showPopupWindowGender());
        this.arraylist_gender.clear();
        this.arraylist_gender.add(getString(R.string.Male));
        this.arraylist_gender.add(getString(R.string.Female));
        this.adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_gender);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Smoking_Risk_Calculator.this.onBackPressed();
            }
        });
        this.tv_search_bloodsmokingrisk.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Smoking_Risk_Calculator.this.et_no_of_cigarettes.getText().toString().trim().equals("")) {
                    Toast.makeText(Smoking_Risk_Calculator.this, Smoking_Risk_Calculator.this.getString(R.string.Enter_no_of_cigarettes), 0).show();
                    return;
                }
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("random_number==>");
                sb.append(random);
                printStream.println(sb.toString());
                if (random == 2) {
                    Smoking_Risk_Calculator.this.showIntertitial();
                } else {
                    Smoking_Risk_Calculator.this.calculate_smokingrisk();
                }
            }
        });
        this.tv_startdate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View currentFocus = Smoking_Risk_Calculator.this.getCurrentFocus();
                if (currentFocus != null) {
                    ((InputMethodManager) Smoking_Risk_Calculator.this.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
                Calendar instance = Calendar.getInstance();
                Smoking_Risk_Calculator.this.from_Year = instance.get(1);
                Smoking_Risk_Calculator.this.from_Month = instance.get(2);
                Smoking_Risk_Calculator.this.from_Day = instance.get(5);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Smoking_Risk_Calculator.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        try {
                            Smoking_Risk_Calculator smoking_Risk_Calculator = Smoking_Risk_Calculator.this;
                            StringBuilder sb = new StringBuilder();
                            sb.append(i3);
                            sb.append("-");
                            sb.append(i2 + 1);
                            sb.append("-");
                            sb.append(i);
                            smoking_Risk_Calculator.start_date = sb.toString();
                            String str = Param.START_DATE;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Param.START_DATE);
                            sb2.append(Smoking_Risk_Calculator.this.start_date);
                            Log.d(str, sb2.toString());
                            Smoking_Risk_Calculator.this.tv_startdate.setText(Smoking_Risk_Calculator.this.start_date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, Smoking_Risk_Calculator.this.from_Year, Smoking_Risk_Calculator.this.from_Month, Smoking_Risk_Calculator.this.from_Day);
                datePickerDialog.show();
            }
        });
        this.tv_enddate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                View currentFocus = Smoking_Risk_Calculator.this.getCurrentFocus();
                if (currentFocus != null) {
                    ((InputMethodManager) Smoking_Risk_Calculator.this.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
                Calendar instance = Calendar.getInstance();
                Smoking_Risk_Calculator.this.from_Year = instance.get(1);
                Smoking_Risk_Calculator.this.from_Month = instance.get(2);
                Smoking_Risk_Calculator.this.from_Day = instance.get(5);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Smoking_Risk_Calculator.this, new OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        Smoking_Risk_Calculator smoking_Risk_Calculator = Smoking_Risk_Calculator.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append(i3);
                        sb.append("-");
                        sb.append(i2 + 1);
                        sb.append("-");
                        sb.append(i);
                        smoking_Risk_Calculator.end_date = sb.toString();
                        Smoking_Risk_Calculator.this.tv_enddate.setText(Smoking_Risk_Calculator.this.end_date);
                        String str = Param.END_DATE;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(Param.END_DATE);
                        sb2.append(Smoking_Risk_Calculator.this.end_date);
                        Log.d(str, sb2.toString());
                    }
                }, Smoking_Risk_Calculator.this.from_Year, Smoking_Risk_Calculator.this.from_Month, Smoking_Risk_Calculator.this.from_Day);
                datePickerDialog.show();
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
                    Smoking_Risk_Calculator.this.calculate_smokingrisk();
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

    private String getDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

    private OnClickListener showPopupWindowGender() {
        return new OnClickListener() {
            public void onClick(View view) {
                Smoking_Risk_Calculator.this.popupWindowGender().showAsDropDown(view, 0, 0);
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
                Smoking_Risk_Calculator.this.tv_gender.setText((CharSequence) Smoking_Risk_Calculator.this.arraylist_gender.get(i));
                Smoking_Risk_Calculator.this.tv_genderunit.setText((CharSequence) Smoking_Risk_Calculator.this.arraylist_gender.get(i));
                Smoking_Risk_Calculator.this.dismissPopupGender();
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

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0096 A[Catch:{ Exception -> 0x0138 }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00ae A[Catch:{ Exception -> 0x0138 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void calculate_smokingrisk() {
        Date date;
        Date date2;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date3 = null;
            try {
                date = simpleDateFormat.parse(this.start_date);
                try {
                    date2 = simpleDateFormat.parse(this.end_date);
                    System.out.println(date);
                } catch (ParseException e2) {

                    e2.printStackTrace();
                    date2 = date3;
                    String str = Param.START_DATE;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Param.START_DATE);
                    sb.append(date);
                    Log.d(str, sb.toString());
                    String str2 = Param.END_DATE;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Param.END_DATE);
                    sb2.append(date2);
                    Log.d(str2, sb2.toString());
                    this.total_days = (int) (((((date2.getTime() - date.getTime()) / 1000) / 60) / 60) / 24);
                    this.total_cigarettes = Integer.parseInt(this.et_no_of_cigarettes.getText().toString().trim());
                    this.timePerDay = this.total_cigarettes * 11;
                    this.totalTimeMin = this.timePerDay * this.total_days;
                    this.totalTimehr = this.totalTimeMin / 60;
                    if (this.totalTimehr < 0) {
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("msg->");
                    sb3.append(this.msg);
                    Log.d("msg->", sb3.toString());
                }
            } catch (ParseException e3) {

                date = null;
                e3.printStackTrace();
                date2 = date3;
                String str3 = Param.START_DATE;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Param.START_DATE);
                sb4.append(date);
                Log.d(str3, sb4.toString());
                String str22 = Param.END_DATE;
                StringBuilder sb22 = new StringBuilder();
                sb22.append(Param.END_DATE);
                sb22.append(date2);
                Log.d(str22, sb22.toString());
                this.total_days = (int) (((((date2.getTime() - date.getTime()) / 1000) / 60) / 60) / 24);
                this.total_cigarettes = Integer.parseInt(this.et_no_of_cigarettes.getText().toString().trim());
                this.timePerDay = this.total_cigarettes * 11;
                this.totalTimeMin = this.timePerDay * this.total_days;
                this.totalTimehr = this.totalTimeMin / 60;
                if (this.totalTimehr < 0) {
                }
                StringBuilder sb32 = new StringBuilder();
                sb32.append("msg->");
                sb32.append(this.msg);
                Log.d("msg->", sb32.toString());
            }
            String str32 = Param.START_DATE;
            StringBuilder sb42 = new StringBuilder();
            sb42.append(Param.START_DATE);
            sb42.append(date);
            Log.d(str32, sb42.toString());
            String str222 = Param.END_DATE;
            StringBuilder sb222 = new StringBuilder();
            sb222.append(Param.END_DATE);
            sb222.append(date2);
            Log.d(str222, sb222.toString());
            this.total_days = (int) (((((date2.getTime() - date.getTime()) / 1000) / 60) / 60) / 24);
            this.total_cigarettes = Integer.parseInt(this.et_no_of_cigarettes.getText().toString().trim());
            this.timePerDay = this.total_cigarettes * 11;
            this.totalTimeMin = this.timePerDay * this.total_days;
            this.totalTimehr = this.totalTimeMin / 60;
            if (this.totalTimehr < 0) {
                this.msg = getString(R.string.Please_select_valid_date);
                Toast.makeText(getApplicationContext(), this.msg, 0).show();
            } else {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(getString(R.string.The_fact_that_you_smoked));
                sb5.append(" ");
                sb5.append(this.total_cigarettes);
                sb5.append(" ");
                sb5.append(getString(R.string.cigarettes_per_day_for_a_period_of));
                sb5.append(" ");
                sb5.append(this.total_days);
                sb5.append(" ");
                sb5.append(getString(R.string.means_that_cigarettes_have_taken));
                sb5.append(" ");
                sb5.append(this.totalTimehr);
                sb5.append(" ");
                sb5.append(getString(R.string.hours_of_your_life));
                this.msg = sb5.toString();
                Intent intent = new Intent(this, Smoking_Risk_Result.class);
                intent.putExtra("smoking_risk_msg", this.msg);
                startActivity(intent);
            }
            StringBuilder sb322 = new StringBuilder();
            sb322.append("msg->");
            sb322.append(this.msg);
            Log.d("msg->", sb322.toString());
        } catch (Exception e4) {
            e4.printStackTrace();
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
            calculate_smokingrisk();
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
            calculate_smokingrisk();
        } else {
            MyApplication.interstitial.show();
        }
    }
}
