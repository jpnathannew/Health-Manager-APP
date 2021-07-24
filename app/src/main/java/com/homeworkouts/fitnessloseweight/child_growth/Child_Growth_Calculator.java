package com.homeworkouts.fitnessloseweight.child_growth;

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


public class Child_Growth_Calculator extends Activity {
    String TAG = getClass().getSimpleName();
    AdView adView;
    ArrayAdapter<String> adapter_month;
    ArrayList<String> arraylist_month = new ArrayList<>();
    GlobalFunction globalFunction;
    String headcircumference;
    String height;
    ImageView iv_back;
    ListView listViewmonth;
    int months;
    private PopupWindow popupWindowmonth;
    String putext_val = "";
    SharedPreferenceManager sharedPreferenceManager;
    TextView tv_child_growth;
    TextView tv_childmonth;
    TextView tv_search_child_height;
    TextView tv_search_child_weight;
    TextView tv_search_headcircumference;
    TextView tv_select_age;
    TypefaceManager typefaceManager;
    String weight;


    public void attachBaseContext(Context context) {
        super.attachBaseContext(uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper.wrap(context));
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_child_growth);
        this.sharedPreferenceManager = new SharedPreferenceManager(this);
        this.globalFunction = new GlobalFunction(this);
        this.typefaceManager = new TypefaceManager(getAssets(), this);
        this.globalFunction.set_locale_language();
        this.globalFunction.sendAnalyticsData(this.TAG, this.TAG);
        this.tv_child_growth = (TextView) findViewById(R.id.tv_child_growth);
        this.tv_childmonth = (TextView) findViewById(R.id.tv_childmonth);
        this.tv_search_child_height = (TextView) findViewById(R.id.tv_search_child_height);
        this.tv_search_child_weight = (TextView) findViewById(R.id.tv_search_child_weight);
        this.tv_childmonth = (TextView) findViewById(R.id.tv_childmonth);
        this.tv_search_headcircumference = (TextView) findViewById(R.id.tv_search_headcircumference);
        this.tv_select_age = (TextView) findViewById(R.id.tv_search_headcircumference);
        this.adView = (AdView) findViewById(R.id.adView);
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        this.tv_childmonth.setOnClickListener(showPopupWindow_month());
        this.tv_child_growth.setTypeface(this.typefaceManager.getBold());
        this.tv_childmonth.setTypeface(this.typefaceManager.getLight());
        this.tv_search_child_height.setTypeface(this.typefaceManager.getBold());
        this.tv_search_child_weight.setTypeface(this.typefaceManager.getBold());
        this.tv_childmonth.setTypeface(this.typefaceManager.getLight());
        this.tv_search_headcircumference.setTypeface(this.typefaceManager.getBold());
        this.tv_childmonth.setTypeface(this.typefaceManager.getLight());
        this.tv_select_age.setTypeface(this.typefaceManager.getBold());
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Child_Growth_Calculator.this.onBackPressed();
            }
        });
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(67108864);
        }
        this.arraylist_month.clear();
        for (int i = 0; i <= 36; i++) {
            ArrayList<String> arrayList = this.arraylist_month;
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(i));
            sb.append(getString(R.string.Month));
            arrayList.add(sb.toString());
        }
        this.adapter_month = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.text1, this.arraylist_month);
        this.tv_search_child_height.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Child_Growth_Calculator.this.months = Integer.parseInt(Child_Growth_Calculator.this.tv_childmonth.getText().toString().trim().replace(Child_Growth_Calculator.this.getString(R.string.Month), ""));
                    Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months);
                    String[] split = Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months).split("~");
                    StringBuilder sb = new StringBuilder();
                    sb.append("Height");
                    sb.append(split[0]);
                    Log.d("Height", sb.toString());
                    Child_Growth_Calculator child_Growth_Calculator = Child_Growth_Calculator.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Height));
                    sb2.append(" : \n");
                    sb2.append(split[0]);
                    child_Growth_Calculator.putext_val = sb2.toString();
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("random_number==>");
                    sb3.append(random);
                    printStream.println(sb3.toString());
                    if (random == 2) {
                        Child_Growth_Calculator.this.showIntertitial();
                        return;
                    }
                    Intent intent = new Intent(Child_Growth_Calculator.this, Child_growth_result.class);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Height));
                    sb4.append(" : \n");
                    sb4.append(split[0]);
                    intent.putExtra("result", sb4.toString());
                    intent.putExtra("age", String.valueOf(Child_Growth_Calculator.this.months));
                    Child_Growth_Calculator.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.tv_search_child_weight.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Child_Growth_Calculator.this.months = Integer.parseInt(Child_Growth_Calculator.this.tv_childmonth.getText().toString().trim().replace(Child_Growth_Calculator.this.getString(R.string.Month), ""));
                Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months);
                String[] split = Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months).split("~");
                Child_Growth_Calculator child_Growth_Calculator = Child_Growth_Calculator.this;
                StringBuilder sb = new StringBuilder();
                sb.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Weight));
                sb.append(" : \n");
                sb.append(split[1]);
                child_Growth_Calculator.putext_val = sb.toString();
                int random = ((int) (Math.random() * 2.0d)) + 1;
                PrintStream printStream = System.out;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("random_number==>");
                sb2.append(random);
                printStream.println(sb2.toString());
                if (random == 2) {
                    Child_Growth_Calculator.this.showIntertitial();
                    return;
                }
                Intent intent = new Intent(Child_Growth_Calculator.this, Child_growth_result.class);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Weight));
                sb3.append(" : \n");
                sb3.append(split[1]);
                intent.putExtra("result", sb3.toString());
                intent.putExtra("age", String.valueOf(Child_Growth_Calculator.this.months));
                Child_Growth_Calculator.this.startActivity(intent);
            }
        });
        this.tv_search_headcircumference.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    Child_Growth_Calculator.this.months = Integer.parseInt(Child_Growth_Calculator.this.tv_childmonth.getText().toString().trim().replace(Child_Growth_Calculator.this.getString(R.string.Month), ""));
                    Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months);
                    String[] split = Child_Growth_Calculator.this.calculate(Child_Growth_Calculator.this.months).split("~");
                    Child_Growth_Calculator child_Growth_Calculator = Child_Growth_Calculator.this;
                    StringBuilder sb = new StringBuilder();
                    sb.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Head_Circumference));
                    sb.append(" : \n");
                    sb.append(split[2]);
                    child_Growth_Calculator.putext_val = sb.toString();
                    int random = ((int) (Math.random() * 2.0d)) + 1;
                    PrintStream printStream = System.out;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("random_number==>");
                    sb2.append(random);
                    printStream.println(sb2.toString());
                    if (random == 2) {
                        Child_Growth_Calculator.this.showIntertitial();
                        return;
                    }
                    Intent intent = new Intent(Child_Growth_Calculator.this, Child_growth_result.class);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(Child_Growth_Calculator.this.getString(R.string.Estimated_Head_Circumference));
                    sb3.append(" : \n");
                    sb3.append(split[2]);
                    intent.putExtra("result", sb3.toString());
                    intent.putExtra("age", String.valueOf(Child_Growth_Calculator.this.months));
                    Child_Growth_Calculator.this.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private OnClickListener showPopupWindow_month() {
        return new OnClickListener() {
            public void onClick(View view) {
                Child_Growth_Calculator.this.popupWindowmonth().showAsDropDown(view, 0, 0);
            }
        };
    }


    public PopupWindow popupWindowmonth() {
        this.popupWindowmonth = new PopupWindow(this);
        this.listViewmonth = new ListView(this);
        this.listViewmonth.setDividerHeight(0);
        this.listViewmonth.setAdapter(this.adapter_month);
        this.listViewmonth.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                StringBuilder sb = new StringBuilder();
                sb.append("position->");
                sb.append(i);
                Log.d("position", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("arraylist_weigth->");
                sb2.append((String) Child_Growth_Calculator.this.arraylist_month.get(i));
                Log.d("arraylist_weigth", sb2.toString());
                Child_Growth_Calculator.this.tv_childmonth.setText((CharSequence) Child_Growth_Calculator.this.arraylist_month.get(i));
                Child_Growth_Calculator.this.dismissPopupTopics();
            }
        });
        this.popupWindowmonth.setFocusable(true);
        this.popupWindowmonth.setWidth(this.tv_childmonth.getMeasuredWidth());
        this.popupWindowmonth.setHeight(500);
        this.popupWindowmonth.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), 17170443));
        this.popupWindowmonth.setContentView(this.listViewmonth);
        return this.popupWindowmonth;
    }


    public void dismissPopupTopics() {
        if (this.popupWindowmonth != null) {
            this.popupWindowmonth.dismiss();
        }
    }

    public String calculate(int i) {
        if (i == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("45 - 54 ");
            sb.append(getString(R.string.cm));
            this.height = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("2.4 - 4.2 ");
            sb2.append(getString(R.string.kg));
            this.weight = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("32.1 - 36.9 ");
            sb3.append(getString(R.string.cm));
            this.headcircumference = sb3.toString();
        } else if (i == 1) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("48.5 - 58 ");
            sb4.append(getString(R.string.cm));
            this.height = sb4.toString();
            StringBuilder sb5 = new StringBuilder();
            sb5.append("3.0 - 5.4 ");
            sb5.append(getString(R.string.kg));
            this.weight = sb5.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append("35.1 - 39.5 ");
            sb6.append(getString(R.string.cm));
            this.headcircumference = sb6.toString();
        } else if (i == 2) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("52 - 63 ");
            sb7.append(getString(R.string.cm));
            this.height = sb7.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append("3.6 - 6.4 ");
            sb8.append(getString(R.string.kg));
            this.weight = sb8.toString();
            StringBuilder sb9 = new StringBuilder();
            sb9.append("36.9 - 41.3 ");
            sb9.append(getString(R.string.cm));
            this.headcircumference = sb9.toString();
        } else if (i == 3) {
            StringBuilder sb10 = new StringBuilder();
            sb10.append("55 - 65 ");
            sb10.append(getString(R.string.cm));
            this.height = sb10.toString();
            StringBuilder sb11 = new StringBuilder();
            sb11.append("4.2 - 7.5 ");
            sb11.append(getString(R.string.kg));
            this.weight = sb11.toString();
            StringBuilder sb12 = new StringBuilder();
            sb12.append("38.3 - 42.7 ");
            sb12.append(getString(R.string.cm));
            this.headcircumference = sb12.toString();
        } else if (i == 4) {
            StringBuilder sb13 = new StringBuilder();
            sb13.append("57.5 - 67.5 ");
            sb13.append(getString(R.string.cm));
            this.height = sb13.toString();
            StringBuilder sb14 = new StringBuilder();
            sb14.append("4.7 - 8.1 ");
            sb14.append(getString(R.string.kg));
            this.weight = sb14.toString();
            StringBuilder sb15 = new StringBuilder();
            sb15.append("39.4 - 43.9 ");
            sb15.append(getString(R.string.cm));
            this.headcircumference = sb15.toString();
        } else if (i == 5) {
            StringBuilder sb16 = new StringBuilder();
            sb16.append("59 - 70 ");
            sb16.append(getString(R.string.cm));
            this.height = sb16.toString();
            StringBuilder sb17 = new StringBuilder();
            sb17.append("5.3 - 8.9 ");
            sb17.append(getString(R.string.kg));
            this.weight = sb17.toString();
            StringBuilder sb18 = new StringBuilder();
            sb18.append("40.3 - 44.8 ");
            sb18.append(getString(R.string.cm));
            this.headcircumference = sb18.toString();
        } else if (i == 6) {
            StringBuilder sb19 = new StringBuilder();
            sb19.append("62 - 72.5 ");
            sb19.append(getString(R.string.cm));
            this.height = sb19.toString();
            StringBuilder sb20 = new StringBuilder();
            sb20.append("5.8 - 9.5 ");
            sb20.append(getString(R.string.kg));
            this.weight = sb20.toString();
            StringBuilder sb21 = new StringBuilder();
            sb21.append("41.0 - 45.6 ");
            sb21.append(getString(R.string.cm));
            this.headcircumference = sb21.toString();
        } else if (i == 7) {
            StringBuilder sb22 = new StringBuilder();
            sb22.append("63 - 74 ");
            sb22.append(getString(R.string.cm));
            this.height = sb22.toString();
            StringBuilder sb23 = new StringBuilder();
            sb23.append("6.2 - 10.0 ");
            sb23.append(getString(R.string.kg));
            this.weight = sb23.toString();
            StringBuilder sb24 = new StringBuilder();
            sb24.append("41.7 - 46.3 ");
            sb24.append(getString(R.string.cm));
            this.headcircumference = sb24.toString();
        } else if (i == 8) {
            StringBuilder sb25 = new StringBuilder();
            sb25.append("64 - 76 ");
            sb25.append(getString(R.string.cm));
            this.height = sb25.toString();
            StringBuilder sb26 = new StringBuilder();
            sb26.append("6.5 - 10.5 ");
            sb26.append(getString(R.string.kg));
            this.weight = sb26.toString();
            StringBuilder sb27 = new StringBuilder();
            sb27.append("42.2 - 46.9 ");
            sb27.append(getString(R.string.cm));
            this.headcircumference = sb27.toString();
        } else if (i == 9) {
            StringBuilder sb28 = new StringBuilder();
            sb28.append("66 - 77 ");
            sb28.append(getString(R.string.cm));
            this.height = sb28.toString();
            StringBuilder sb29 = new StringBuilder();
            sb29.append("7.0 - 11.0 ");
            sb29.append(getString(R.string.kg));
            this.weight = sb29.toString();
            StringBuilder sb30 = new StringBuilder();
            sb30.append("42.6 - 47.4 ");
            sb30.append(getString(R.string.cm));
            this.headcircumference = sb30.toString();
        } else if (i == 10) {
            StringBuilder sb31 = new StringBuilder();
            sb31.append("67.5 - 78 ");
            sb31.append(getString(R.string.cm));
            this.height = sb31.toString();
            StringBuilder sb32 = new StringBuilder();
            sb32.append("7.3 - 11.6 ");
            sb32.append(getString(R.string.kg));
            this.weight = sb32.toString();
            StringBuilder sb33 = new StringBuilder();
            sb33.append("43.0 - 47.8 ");
            sb33.append(getString(R.string.cm));
            this.headcircumference = sb33.toString();
        } else if (i == 11) {
            StringBuilder sb34 = new StringBuilder();
            sb34.append("68 - 80 ");
            sb34.append(getString(R.string.cm));
            this.height = sb34.toString();
            StringBuilder sb35 = new StringBuilder();
            sb35.append("7.5 - 11.6 ");
            sb35.append(getString(R.string.kg));
            this.weight = sb35.toString();
            StringBuilder sb36 = new StringBuilder();
            sb36.append("43.4 - 48.2 ");
            sb36.append(getString(R.string.cm));
            this.headcircumference = sb36.toString();
        } else if (i == 12) {
            StringBuilder sb37 = new StringBuilder();
            sb37.append("70 - 82 ");
            sb37.append(getString(R.string.cm));
            this.height = sb37.toString();
            StringBuilder sb38 = new StringBuilder();
            sb38.append("7.8 - 12.0 ");
            sb38.append(getString(R.string.kg));
            this.weight = sb38.toString();
            StringBuilder sb39 = new StringBuilder();
            sb39.append("43.0 - 47.8 ");
            sb39.append(getString(R.string.cm));
            this.headcircumference = sb39.toString();
        } else if (i == 13) {
            StringBuilder sb40 = new StringBuilder();
            sb40.append("70 - 82 ");
            sb40.append(getString(R.string.cm));
            this.height = sb40.toString();
            StringBuilder sb41 = new StringBuilder();
            sb41.append("7.8 - 12.0 ");
            sb41.append(getString(R.string.kg));
            this.weight = sb41.toString();
            StringBuilder sb42 = new StringBuilder();
            sb42.append("42.6 - 47.7 ");
            sb42.append(getString(R.string.cm));
            this.headcircumference = sb42.toString();
        } else if (i == 14) {
            StringBuilder sb43 = new StringBuilder();
            sb43.append("72 - 83.5 ");
            sb43.append(getString(R.string.cm));
            this.height = sb43.toString();
            StringBuilder sb44 = new StringBuilder();
            sb44.append("8.2 - 12.5 ");
            sb44.append(getString(R.string.kg));
            this.weight = sb44.toString();
            StringBuilder sb45 = new StringBuilder();
            sb45.append("442.9 - 48.0 ");
            sb45.append(getString(R.string.cm));
            this.headcircumference = sb45.toString();
        } else if (i == 15) {
            StringBuilder sb46 = new StringBuilder();
            sb46.append("72 - 83.5 ");
            sb46.append(getString(R.string.cm));
            this.height = sb46.toString();
            StringBuilder sb47 = new StringBuilder();
            sb47.append("8.2 - 12.5 ");
            sb47.append(getString(R.string.kg));
            this.weight = sb47.toString();
            StringBuilder sb48 = new StringBuilder();
            sb48.append("43.1 - 48.2 ");
            sb48.append(getString(R.string.cm));
            this.headcircumference = sb48.toString();
        } else if (i == 16) {
            StringBuilder sb49 = new StringBuilder();
            sb49.append("74 - 86 ");
            sb49.append(getString(R.string.cm));
            this.height = sb49.toString();
            StringBuilder sb50 = new StringBuilder();
            sb50.append("8.5 - 13.0 ");
            sb50.append(getString(R.string.kg));
            this.weight = sb50.toString();
            StringBuilder sb51 = new StringBuilder();
            sb51.append("43.3 - 48.5 ");
            sb51.append(getString(R.string.cm));
            this.headcircumference = sb51.toString();
        } else if (i == 17) {
            StringBuilder sb52 = new StringBuilder();
            sb52.append("74 - 86 ");
            sb52.append(getString(R.string.cm));
            this.height = sb52.toString();
            StringBuilder sb53 = new StringBuilder();
            sb53.append("8.5 - 13.0 ");
            sb53.append(getString(R.string.kg));
            this.weight = sb53.toString();
            StringBuilder sb54 = new StringBuilder();
            sb54.append(" 43.5 - 48.7 ");
            sb54.append(getString(R.string.cm));
            this.headcircumference = sb54.toString();
        } else if (i == 18) {
            StringBuilder sb55 = new StringBuilder();
            sb55.append("76 - 87.5 ");
            sb55.append(getString(R.string.cm));
            this.height = sb55.toString();
            StringBuilder sb56 = new StringBuilder();
            sb56.append("8.9 - 13.4 ");
            sb56.append(getString(R.string.kg));
            this.weight = sb56.toString();
            StringBuilder sb57 = new StringBuilder();
            sb57.append("43.6 - 48.8 ");
            sb57.append(getString(R.string.cm));
            this.headcircumference = sb57.toString();
        } else if (i == 19) {
            StringBuilder sb58 = new StringBuilder();
            sb58.append("76 - 87.5 ");
            sb58.append(getString(R.string.cm));
            this.height = sb58.toString();
            StringBuilder sb59 = new StringBuilder();
            sb59.append("8.9 - 13.4 ");
            sb59.append(getString(R.string.kg));
            this.weight = sb59.toString();
            StringBuilder sb60 = new StringBuilder();
            sb60.append(" 43.8 - 49.0 ");
            sb60.append(getString(R.string.cm));
            this.headcircumference = sb60.toString();
        } else if (i == 20) {
            StringBuilder sb61 = new StringBuilder();
            sb61.append("77.5 - 90 ");
            sb61.append(getString(R.string.cm));
            this.height = sb61.toString();
            StringBuilder sb62 = new StringBuilder();
            sb62.append("9.2 - 13.8 ");
            sb62.append(getString(R.string.kg));
            this.weight = sb62.toString();
            StringBuilder sb63 = new StringBuilder();
            sb63.append("44.0 - 49.2 ");
            sb63.append(getString(R.string.cm));
            this.headcircumference = sb63.toString();
        } else if (i == 21) {
            StringBuilder sb64 = new StringBuilder();
            sb64.append("77.5 - 90 ");
            sb64.append(getString(R.string.cm));
            this.height = sb64.toString();
            StringBuilder sb65 = new StringBuilder();
            sb65.append("9.2 - 13.8 ");
            sb65.append(getString(R.string.kg));
            this.weight = sb65.toString();
            StringBuilder sb66 = new StringBuilder();
            sb66.append("44.1 - 49.4 ");
            sb66.append(getString(R.string.cm));
            this.headcircumference = sb66.toString();
        } else if (i == 22) {
            StringBuilder sb67 = new StringBuilder();
            sb67.append("79 - 92 ");
            sb67.append(getString(R.string.cm));
            this.height = sb67.toString();
            StringBuilder sb68 = new StringBuilder();
            sb68.append("9.5 - 14.2 ");
            sb68.append(getString(R.string.kg));
            this.weight = sb68.toString();
            StringBuilder sb69 = new StringBuilder();
            sb69.append("44.3 - 49.5 ");
            sb69.append(getString(R.string.cm));
            this.headcircumference = sb69.toString();
        } else if (i == 23) {
            StringBuilder sb70 = new StringBuilder();
            sb70.append("79 - 92 ");
            sb70.append(getString(R.string.cm));
            this.height = sb70.toString();
            StringBuilder sb71 = new StringBuilder();
            sb71.append("9.5 - 14.2 ");
            sb71.append(getString(R.string.kg));
            this.weight = sb71.toString();
            StringBuilder sb72 = new StringBuilder();
            sb72.append("44.4 - 49.7 ");
            sb72.append(getString(R.string.cm));
            this.headcircumference = sb72.toString();
        } else if (i == 24) {
            StringBuilder sb73 = new StringBuilder();
            sb73.append("81 - 94 ");
            sb73.append(getString(R.string.cm));
            this.height = sb73.toString();
            StringBuilder sb74 = new StringBuilder();
            sb74.append("9.8 - 14.7 ");
            sb74.append(getString(R.string.kg));
            this.weight = sb74.toString();
            StringBuilder sb75 = new StringBuilder();
            sb75.append("44.6 - 49.8 ");
            sb75.append(getString(R.string.cm));
            this.headcircumference = sb75.toString();
        } else if (i == 25) {
            StringBuilder sb76 = new StringBuilder();
            sb76.append("81 - 94 ");
            sb76.append(getString(R.string.cm));
            this.height = sb76.toString();
            StringBuilder sb77 = new StringBuilder();
            sb77.append("9.8 - 14.7 ");
            sb77.append(getString(R.string.kg));
            this.weight = sb77.toString();
            StringBuilder sb78 = new StringBuilder();
            sb78.append("45.8 - 50.9 ");
            sb78.append(getString(R.string.cm));
            this.headcircumference = sb78.toString();
        } else if (i == 26) {
            StringBuilder sb79 = new StringBuilder();
            sb79.append("83 - 95.5 ");
            sb79.append(getString(R.string.cm));
            this.height = sb79.toString();
            StringBuilder sb80 = new StringBuilder();
            sb80.append("10.1 - 15.1 ");
            sb80.append(getString(R.string.kg));
            this.weight = sb80.toString();
            StringBuilder sb81 = new StringBuilder();
            sb81.append(" 45.9 - 51.1 ");
            sb81.append(getString(R.string.cm));
            this.headcircumference = sb81.toString();
        } else if (i == 27) {
            StringBuilder sb82 = new StringBuilder();
            sb82.append("83 - 95.5 ");
            sb82.append(getString(R.string.cm));
            this.height = sb82.toString();
            StringBuilder sb83 = new StringBuilder();
            sb83.append("10.1 - 15.1 ");
            sb83.append(getString(R.string.kg));
            this.weight = sb83.toString();
            StringBuilder sb84 = new StringBuilder();
            sb84.append("46.0 - 51.2 ");
            sb84.append(getString(R.string.cm));
            this.headcircumference = sb84.toString();
        } else if (i == 28) {
            StringBuilder sb85 = new StringBuilder();
            sb85.append("84 - 97 ");
            sb85.append(getString(R.string.cm));
            this.height = sb85.toString();
            StringBuilder sb86 = new StringBuilder();
            sb86.append("10.4 - 15.5 ");
            sb86.append(getString(R.string.kg));
            this.weight = sb86.toString();
            StringBuilder sb87 = new StringBuilder();
            sb87.append("46.1 - 51.3 ");
            sb87.append(getString(R.string.cm));
            this.headcircumference = sb87.toString();
        } else if (i == 29) {
            StringBuilder sb88 = new StringBuilder();
            sb88.append("84 - 97 ");
            sb88.append(getString(R.string.cm));
            this.height = sb88.toString();
            StringBuilder sb89 = new StringBuilder();
            sb89.append("10.4 - 15.5 ");
            sb89.append(getString(R.string.kg));
            this.weight = sb89.toString();
            StringBuilder sb90 = new StringBuilder();
            sb90.append("46.2 - 51.4 ");
            sb90.append(getString(R.string.cm));
            this.headcircumference = sb90.toString();
        } else if (i == 30) {
            StringBuilder sb91 = new StringBuilder();
            sb91.append("86 - 98.5 ");
            sb91.append(getString(R.string.cm));
            this.height = sb91.toString();
            StringBuilder sb92 = new StringBuilder();
            sb92.append("10.6 - 16.0 ");
            sb92.append(getString(R.string.kg));
            this.weight = sb92.toString();
            StringBuilder sb93 = new StringBuilder();
            sb93.append("46.3 - 51.6 ");
            sb93.append(getString(R.string.cm));
            this.headcircumference = sb93.toString();
        } else if (i == 31) {
            StringBuilder sb94 = new StringBuilder();
            sb94.append("86 - 98.5 ");
            sb94.append(getString(R.string.cm));
            this.height = sb94.toString();
            StringBuilder sb95 = new StringBuilder();
            sb95.append("10.6 - 16.0 ");
            sb95.append(getString(R.string.kg));
            this.weight = sb95.toString();
            StringBuilder sb96 = new StringBuilder();
            sb96.append("46.4 - 51.7 ");
            sb96.append(getString(R.string.cm));
            this.headcircumference = sb96.toString();
        } else if (i == 32) {
            StringBuilder sb97 = new StringBuilder();
            sb97.append("87.5 - 99 ");
            sb97.append(getString(R.string.cm));
            this.height = sb97.toString();
            StringBuilder sb98 = new StringBuilder();
            sb98.append("11.0 - 16.4 ");
            sb98.append(getString(R.string.kg));
            this.weight = sb98.toString();
            StringBuilder sb99 = new StringBuilder();
            sb99.append("46.5 - 51.8 ");
            sb99.append(getString(R.string.cm));
            this.headcircumference = sb99.toString();
        } else if (i == 33) {
            StringBuilder sb100 = new StringBuilder();
            sb100.append("87.5 - 99 ");
            sb100.append(getString(R.string.cm));
            this.height = sb100.toString();
            StringBuilder sb101 = new StringBuilder();
            sb101.append("11.0 - 16.4 ");
            sb101.append(getString(R.string.kg));
            this.weight = sb101.toString();
            StringBuilder sb102 = new StringBuilder();
            sb102.append("46.6 - 51.9 ");
            sb102.append(getString(R.string.cm));
            this.headcircumference = sb102.toString();
        } else if (i == 34) {
            StringBuilder sb103 = new StringBuilder();
            sb103.append("88.5 - 103 ");
            sb103.append(getString(R.string.cm));
            this.height = sb103.toString();
            StringBuilder sb104 = new StringBuilder();
            sb104.append("11.2 - 16.8 ");
            sb104.append(getString(R.string.kg));
            this.weight = sb104.toString();
            StringBuilder sb105 = new StringBuilder();
            sb105.append("46.6 - 52.0 ");
            sb105.append(getString(R.string.cm));
            this.headcircumference = sb105.toString();
        } else if (i == 35) {
            StringBuilder sb106 = new StringBuilder();
            sb106.append("88.5 - 103 ");
            sb106.append(getString(R.string.cm));
            this.height = sb106.toString();
            StringBuilder sb107 = new StringBuilder();
            sb107.append("11.2 - 16.8 ");
            sb107.append(getString(R.string.kg));
            this.weight = sb107.toString();
            StringBuilder sb108 = new StringBuilder();
            sb108.append("4 46.7 - 52.0 ");
            sb108.append(getString(R.string.cm));
            this.headcircumference = sb108.toString();
        } else if (i == 36) {
            StringBuilder sb109 = new StringBuilder();
            sb109.append("90 - 106 ");
            sb109.append(getString(R.string.cm));
            this.height = sb109.toString();
            StringBuilder sb110 = new StringBuilder();
            sb110.append("11.5 - 17.2 ");
            sb110.append(getString(R.string.kg));
            this.weight = sb110.toString();
            StringBuilder sb111 = new StringBuilder();
            sb111.append("46.8 - 52.1 ");
            sb111.append(getString(R.string.cm));
            this.headcircumference = sb111.toString();
        }
        StringBuilder sb112 = new StringBuilder();
        sb112.append(this.height);
        sb112.append("~");
        sb112.append(this.weight);
        sb112.append("~");
        sb112.append(this.headcircumference);
        return sb112.toString();
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
            Intent intent = new Intent(this, Child_growth_result.class);
            intent.putExtra("result", this.putext_val);
            intent.putExtra("age", String.valueOf(this.months));
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
            Intent intent2 = new Intent(this, Child_growth_result.class);
            intent2.putExtra("result", this.putext_val);
            intent2.putExtra("age", String.valueOf(this.months));
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
                    Intent intent = new Intent(Child_Growth_Calculator.this, Child_growth_result.class);
                    intent.putExtra("result", Child_Growth_Calculator.this.putext_val);
                    intent.putExtra("age", String.valueOf(Child_Growth_Calculator.this.months));
                    Child_Growth_Calculator.this.startActivity(intent);
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
