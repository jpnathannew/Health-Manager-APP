package com.homeworkouts.fitnessloseweight.menstrual_and_ovulation;

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
import com.homeworkouts.fitnessloseweight.utils.DateUtil;
import com.homeworkouts.fitnessloseweight.utils.GlobalFunction;
import com.homeworkouts.fitnessloseweight.utils.SharedPreferenceManager;
import com.homeworkouts.fitnessloseweight.utils.TypefaceManager;
import com.homeworkouts.fitnessloseweight.shashikant.calendar.SNPCalendarView;
import com.homeworkouts.fitnessloseweight.shashikant.calendar.onSNPCalendarViewListener;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.interfaces.NetworkRequestCheckListener;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Menstrual_Ovulation_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_time;
    ArrayList<String> arraylist_time = new ArrayList<>();
    String curr_date;
    int day_to_add;
    GlobalFunction globalFunction;
    ImageView iv_back;
    ListView listViewHeight;
    SNPCalendarView mFCalendarView;

    /* renamed from: n1 */
    int f2615n1;

    /* renamed from: n2 */
    int f2616n2;
    String nextdate1_ovulation;
    String nextdate2_ovulation;
    String nextdate_menstrual;
    private PopupWindow popupWindowTime;
    String prev_date;
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_calculate_mco;
    TextView tv_cycle_days;
    TextView tv_menstrual;
    TypefaceManager typefaceManager;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.menstrual_and_ovulation_calculator);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.mFCalendarView = (SNPCalendarView) findViewById(R.id.mFCalendarView);
        this.tv_cycle_days = (TextView) findViewById(R.id.tv_cycle_days);
        this.tv_calculate_mco = (TextView) findViewById(R.id.tv_calculate_mco);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.tv_menstrual = (TextView) findViewById(R.id.tv_menstrual);
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_menstrual.setTypeface(this.typefaceManager.getBold());
        this.tv_calculate_mco.setTypeface(this.typefaceManager.getBold());
        this.tv_menstrual.setFocusable(true);
        this.tv_menstrual.setFocusableInTouchMode(true);
        this.tv_menstrual.requestFocus();
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.curr_date = getDateTime();
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Menstrual_Ovulation_Calculator.this.onBackPressed();
            }
        });
        this.tv_calculate_mco.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Menstrual_Ovulation_Calculator.this.day_to_add = Integer.parseInt(Menstrual_Ovulation_Calculator.this.tv_cycle_days.getText().toString().trim().replace(Menstrual_Ovulation_Calculator.this.getString(R.string.Days), "").trim());
                    Menstrual_Ovulation_Calculator.this.f2615n1 = Menstrual_Ovulation_Calculator.this.day_to_add / 2;
                    Menstrual_Ovulation_Calculator.this.f2616n2 = Menstrual_Ovulation_Calculator.this.f2615n1 - 2;
                    Menstrual_Ovulation_Calculator.this.calculate_nextdate(Menstrual_Ovulation_Calculator.this.curr_date);
                    Menstrual_Ovulation_Calculator.this.calculate_nextdate1(Menstrual_Ovulation_Calculator.this.curr_date);
                    Menstrual_Ovulation_Calculator.this.calculate_nextdate2(Menstrual_Ovulation_Calculator.this.curr_date);
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb = new StringBuilder();
                    sb.append("random_number==>");
                    sb.append(random);
                    printStream.println(sb.toString());
                    if (random == 2) {
                        Menstrual_Ovulation_Calculator.this.showIntertitial();
                        return;
                    }
                    Intent intent = new Intent(Menstrual_Ovulation_Calculator.this, Menstrual_and_ovulation_Result.class);
                    intent.putExtra("nextdate_menstrual", Menstrual_Ovulation_Calculator.this.nextdate_menstrual);
                    intent.putExtra("nextdate1_ovulation", Menstrual_Ovulation_Calculator.this.nextdate1_ovulation);
                    intent.putExtra("nextdate2_ovulation", Menstrual_Ovulation_Calculator.this.nextdate2_ovulation);
                    intent.putExtra("curr_date", Menstrual_Ovulation_Calculator.this.curr_date);
                    Menstrual_Ovulation_Calculator.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.tv_cycle_days.setOnClickListener(showPopupWindowTime());
        this.arraylist_time.clear();
        ArrayList<String> arrayList = this.arraylist_time;
        StringBuilder sb = new StringBuilder();
        sb.append("21 ");
        sb.append(getString(R.string.Days));
        arrayList.add(sb.toString());
        ArrayList<String> arrayList2 = this.arraylist_time;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("22 ");
        sb2.append(getString(R.string.Days));
        arrayList2.add(sb2.toString());
        ArrayList<String> arrayList3 = this.arraylist_time;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("23 ");
        sb3.append(getString(R.string.Days));
        arrayList3.add(sb3.toString());
        ArrayList<String> arrayList4 = this.arraylist_time;
        StringBuilder sb4 = new StringBuilder();
        sb4.append("24 ");
        sb4.append(getString(R.string.Days));
        arrayList4.add(sb4.toString());
        ArrayList<String> arrayList5 = this.arraylist_time;
        StringBuilder sb5 = new StringBuilder();
        sb5.append("25 ");
        sb5.append(getString(R.string.Days));
        arrayList5.add(sb5.toString());
        ArrayList<String> arrayList6 = this.arraylist_time;
        StringBuilder sb6 = new StringBuilder();
        sb6.append("26 ");
        sb6.append(getString(R.string.Days));
        arrayList6.add(sb6.toString());
        ArrayList<String> arrayList7 = this.arraylist_time;
        StringBuilder sb7 = new StringBuilder();
        sb7.append("27 ");
        sb7.append(getString(R.string.Days));
        arrayList7.add(sb7.toString());
        ArrayList<String> arrayList8 = this.arraylist_time;
        StringBuilder sb8 = new StringBuilder();
        sb8.append("28 ");
        sb8.append(getString(R.string.Days));
        arrayList8.add(sb8.toString());
        ArrayList<String> arrayList9 = this.arraylist_time;
        StringBuilder sb9 = new StringBuilder();
        sb9.append("29 ");
        sb9.append(getString(R.string.Days));
        arrayList9.add(sb9.toString());
        ArrayList<String> arrayList10 = this.arraylist_time;
        StringBuilder sb10 = new StringBuilder();
        sb10.append("30 ");
        sb10.append(getString(R.string.Days));
        arrayList10.add(sb10.toString());
        ArrayList<String> arrayList11 = this.arraylist_time;
        StringBuilder sb11 = new StringBuilder();
        sb11.append("31 ");
        sb11.append(getString(R.string.Days));
        arrayList11.add(sb11.toString());
        ArrayList<String> arrayList12 = this.arraylist_time;
        StringBuilder sb12 = new StringBuilder();
        sb12.append("32 ");
        sb12.append(getString(R.string.Days));
        arrayList12.add(sb12.toString());
        ArrayList<String> arrayList13 = this.arraylist_time;
        StringBuilder sb13 = new StringBuilder();
        sb13.append("33 ");
        sb13.append(getString(R.string.Days));
        arrayList13.add(sb13.toString());
        ArrayList<String> arrayList14 = this.arraylist_time;
        StringBuilder sb14 = new StringBuilder();
        sb14.append("34 ");
        sb14.append(getString(R.string.Days));
        arrayList14.add(sb14.toString());
        ArrayList<String> arrayList15 = this.arraylist_time;
        StringBuilder sb15 = new StringBuilder();
        sb15.append("35 ");
        sb15.append(getString(R.string.Days));
        arrayList15.add(sb15.toString());
        ArrayList<String> arrayList16 = this.arraylist_time;
        StringBuilder sb16 = new StringBuilder();
        sb16.append("36 ");
        sb16.append(getString(R.string.Days));
        arrayList16.add(sb16.toString());
        ArrayList<String> arrayList17 = this.arraylist_time;
        StringBuilder sb17 = new StringBuilder();
        sb17.append("37 ");
        sb17.append(getString(R.string.Days));
        arrayList17.add(sb17.toString());
        ArrayList<String> arrayList18 = this.arraylist_time;
        StringBuilder sb18 = new StringBuilder();
        sb18.append("38 ");
        sb18.append(getString(R.string.Days));
        arrayList18.add(sb18.toString());
        ArrayList<String> arrayList19 = this.arraylist_time;
        StringBuilder sb19 = new StringBuilder();
        sb19.append("39 ");
        sb19.append(getString(R.string.Days));
        arrayList19.add(sb19.toString());
        ArrayList<String> arrayList20 = this.arraylist_time;
        StringBuilder sb20 = new StringBuilder();
        sb20.append("40 ");
        sb20.append(getString(R.string.Days));
        arrayList20.add(sb20.toString());
        ArrayList<String> arrayList21 = this.arraylist_time;
        StringBuilder sb21 = new StringBuilder();
        sb21.append("41 ");
        sb21.append(getString(R.string.Days));
        arrayList21.add(sb21.toString());
        ArrayList<String> arrayList22 = this.arraylist_time;
        StringBuilder sb22 = new StringBuilder();
        sb22.append("42 ");
        sb22.append(getString(R.string.Days));
        arrayList22.add(sb22.toString());
        ArrayList<String> arrayList23 = this.arraylist_time;
        StringBuilder sb23 = new StringBuilder();
        sb23.append("43 ");
        sb23.append(getString(R.string.Days));
        arrayList23.add(sb23.toString());
        ArrayList<String> arrayList24 = this.arraylist_time;
        StringBuilder sb24 = new StringBuilder();
        sb24.append("44 ");
        sb24.append(getString(R.string.Days));
        arrayList24.add(sb24.toString());
        ArrayList<String> arrayList25 = this.arraylist_time;
        StringBuilder sb25 = new StringBuilder();
        sb25.append("45 ");
        sb25.append(getString(R.string.Days));
        arrayList25.add(sb25.toString());
        this.adapter_time = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_time);
        this.mFCalendarView.setOnCalendarViewListener(new onSNPCalendarViewListener() {
            public void onDisplayedMonthChanged(int i, int i2, String str) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(" month:");
                stringBuffer.append(i);
                stringBuffer.append(" year:");
                stringBuffer.append(i2);
                stringBuffer.append(" monthStr: ");
                stringBuffer.append(str);
            }

            public void onDateChanged(String str) {
                try {
                    Menstrual_Ovulation_Calculator.this.curr_date = str;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private OnClickListener showPopupWindowTime() {
        return new OnClickListener() {
            public void onClick(View view) {
                Menstrual_Ovulation_Calculator.this.popupWindowTime().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowTime() {
        this.popupWindowTime = new PopupWindow(this);
        this.listViewHeight = new ListView(this);
        this.listViewHeight.setDividerHeight(0);
        this.listViewHeight.setAdapter(this.adapter_time);
        this.listViewHeight.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Menstrual_Ovulation_Calculator.this.tv_cycle_days.setText((CharSequence) Menstrual_Ovulation_Calculator.this.arraylist_time.get(i));
                Menstrual_Ovulation_Calculator.this.dismissPopupTime();
            }
        });
        this.popupWindowTime.setFocusable(true);
        this.popupWindowTime.setWidth(this.tv_cycle_days.getMeasuredWidth());
        this.popupWindowTime.setHeight(-2);
        this.popupWindowTime.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowTime.setContentView(this.listViewHeight);
        return this.popupWindowTime;
    }


    public void dismissPopupTime() {
        if (this.popupWindowTime != null) {
            this.popupWindowTime.dismiss();
        }
    }

    public void calculate_nextdate(String str) {
        try {
            Date addDays = DateUtil.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(str), this.day_to_add);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            new SimpleDateFormat("dd MMM yyyy");
            this.nextdate_menstrual = simpleDateFormat.format(addDays);
            StringBuilder sb = new StringBuilder();
            sb.append("nextdate_menstrual->");
            sb.append(this.nextdate_menstrual);
            Log.d("nextdate_menstrual", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculate_nextdate1(String str) {
        try {
            Date addDays = DateUtil.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(str), this.f2616n2);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            this.nextdate1_ovulation = simpleDateFormat.format(addDays);
            StringBuilder sb = new StringBuilder();
            sb.append("nextdate1_ovulation->");
            sb.append(simpleDateFormat.format(addDays));
            Log.d("nextdate1_ovulation", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calculate_nextdate2(String str) {
        try {
            Date addDays = DateUtil.addDays(new SimpleDateFormat("yyyy-MM-dd").parse(str), this.f2616n2 + 4);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            this.nextdate2_ovulation = simpleDateFormat.format(addDays);
            StringBuilder sb = new StringBuilder();
            sb.append("nextdate2_ovulation->");
            sb.append(simpleDateFormat.format(addDays));
            Log.d("nextdate2_ovulation", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDateTime() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
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
            Intent intent = new Intent(this, Menstrual_and_ovulation_Result.class);
            intent.putExtra("nextdate_menstrual", this.nextdate_menstrual);
            intent.putExtra("nextdate1_ovulation", this.nextdate1_ovulation);
            intent.putExtra("nextdate2_ovulation", this.nextdate2_ovulation);
            intent.putExtra("curr_date", this.curr_date);
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
            Intent intent2 = new Intent(this, Menstrual_and_ovulation_Result.class);
            intent2.putExtra("nextdate_menstrual", this.nextdate_menstrual);
            intent2.putExtra("nextdate1_ovulation", this.nextdate1_ovulation);
            intent2.putExtra("nextdate2_ovulation", this.nextdate2_ovulation);
            intent2.putExtra("curr_date", this.curr_date);
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
                    Intent intent = new Intent(Menstrual_Ovulation_Calculator.this, Menstrual_and_ovulation_Result.class);
                    intent.putExtra("nextdate_menstrual", Menstrual_Ovulation_Calculator.this.nextdate_menstrual);
                    intent.putExtra("nextdate1_ovulation", Menstrual_Ovulation_Calculator.this.nextdate1_ovulation);
                    intent.putExtra("nextdate2_ovulation", Menstrual_Ovulation_Calculator.this.nextdate2_ovulation);
                    intent.putExtra("curr_date", Menstrual_Ovulation_Calculator.this.curr_date);
                    Menstrual_Ovulation_Calculator.this.startActivity(intent);
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
