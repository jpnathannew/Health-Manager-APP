package com.homeworkouts.fitnessloseweight;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.homeworkouts.fitnessloseweight.adapters.IndividualDayAdapter;
import com.homeworkouts.fitnessloseweight.adapters.WorkoutData;
import com.homeworkouts.fitnessloseweight.database.DatabaseOperations;
import com.homeworkouts.fitnessloseweight.listners.RecyclerItemClickListener;
import com.homeworkouts.fitnessloseweight.listners.RecyclerItemClickListener.onItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DayActivity extends AppCompatActivity {
    public LinearLayout container;
    public DatabaseOperations databaseOperations;
    public Editor editorDay;
    public RecyclerView k;
    public Button l;
    public LinearLayoutManager m;
    public IndividualDayAdapter n;
    public String o;
    public float p;
    public SharedPreferences preferencesDay;
    public HashMap<String, Integer> q;
    public HashMap<String, Integer> r;
    public int[] s = {R.array.day1, R.array.day2, R.array.day3, R.array.day4, R.array.day5, R.array.day6, R.array.day7, R.array.day8, R.array.day9, R.array.day10, R.array.day11, R.array.day12, R.array.day13, R.array.day14, R.array.day15, R.array.day16, R.array.day17, R.array.day18, R.array.day19, R.array.day20, R.array.day21, R.array.day22, R.array.day23, R.array.day24, R.array.day25, R.array.day26, R.array.day27, R.array.day28, R.array.day29, R.array.day30};
    public int[] t = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public int u = -1;
    public ArrayList<WorkoutData> v;
    public InterstitialAd w;
    public AdRequest x;
    public Intent y;


    public void requestNewInterstitial() {
        this.w.loadAd(this.x);
    }

    private void setAdmodAds() {
        this.w = new InterstitialAd(this);
        this.w.setAdUnitId(getString(R.string.interstitial_key));
        this.x = new Builder().build();
        this.w.setAdListener(new AdListener() {
            public void onAdClosed() {
                super.onAdClosed();
                DayActivity dayActivity = DayActivity.this;
                dayActivity.startActivity(dayActivity.y);
                DayActivity.this.requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    public void b() {
        this.r = new HashMap<>();
        this.r.put("cat cow stretch", Integer.valueOf(R.string.desc_cat_cow_stretch));
        this.r.put("downward facing dog pose", Integer.valueOf(R.string.desc_downward_facing_dog_pose));
        this.r.put("dry land swim", Integer.valueOf(R.string.desc_dry_land_swim));
        this.r.put("forward spine stretch", Integer.valueOf(R.string.desc_forward_spine_stretch));
        this.r.put("hopping with one leg", Integer.valueOf(R.string.desc_hopping_with_one_leg));
        this.r.put("hands on the head bow down", Integer.valueOf(R.string.desc_hands_on_the_head_bow_down));
        this.r.put("pelvic shift", Integer.valueOf(R.string.desc_pelvic_shift));
        this.r.put("pilates roll over", Integer.valueOf(R.string.desc_pilates_roll_over));
        this.r.put("plank", Integer.valueOf(R.string.desc_plank));
        this.r.put("rope jumping", Integer.valueOf(R.string.desc_rope_jumping));
        this.r.put("seated toe touch", Integer.valueOf(R.string.desc_seated_toe_touch));
        this.r.put("spot jumping", Integer.valueOf(R.string.desc_spot_jumping));
        this.r.put("standing vertical stretch", Integer.valueOf(R.string.desc_standing_vertical_stretch));
        this.r.put("super cobra stretch", Integer.valueOf(R.string.desc_super_cobra_stretch));
        this.r.put("table top", Integer.valueOf(R.string.desc_table_top));
        this.r.put("triangle pose", Integer.valueOf(R.string.desc_triangle_pose));
        this.r.put("two straight legs up", Integer.valueOf(R.string.desc_two_straight_legs_up));
        this.r.put("wall stretch", Integer.valueOf(R.string.desc_wall_stretch));
        this.r.put("hanging exercise", Integer.valueOf(R.string.desc_hanging_exercise));
    }

    public void c() {
        this.q = new HashMap<>();
        this.q.put("cat cow stretch", Integer.valueOf(R.array.cat_cow_stretch));
        this.q.put("downward facing dog pose", Integer.valueOf(R.array.downward_facing_dog_pose));
        this.q.put("dry land swim", Integer.valueOf(R.array.dry_land_swim));
        this.q.put("forward spine stretch", Integer.valueOf(R.array.forward_spine_stretch));
        this.q.put("hopping with one leg", Integer.valueOf(R.array.hopping_with_one_leg));
        this.q.put("hands on the head bow down", Integer.valueOf(R.array.hands_on_the_head_bow_down));
        this.q.put("pelvic shift", Integer.valueOf(R.array.pelvic_shift));
        this.q.put("pilates roll over", Integer.valueOf(R.array.pilates_roll_over));
        this.q.put("plank", Integer.valueOf(R.array.plank));
        this.q.put("rope jumping", Integer.valueOf(R.array.rope_jumping));
        this.q.put("seated toe touch", Integer.valueOf(R.array.seated_toe_touch));
        this.q.put("spot jumping", Integer.valueOf(R.array.spot_jumping));
        this.q.put("standing vertical stretch", Integer.valueOf(R.array.standing_vertical_stretch));
        this.q.put("super cobra stretch", Integer.valueOf(R.array.super_cobra_stretch));
        this.q.put("table top", Integer.valueOf(R.array.table_top));
        this.q.put("triangle pose", Integer.valueOf(R.array.triangle_pose));
        this.q.put("two straight legs up", Integer.valueOf(R.array.two_straight_legs_up));
        this.q.put("wall stretch", Integer.valueOf(R.array.wall_stretch));
        this.q.put("hanging exercise", Integer.valueOf(R.array.hanging_exercise));
    }

    public ArrayList<WorkoutData> d() {
        ArrayList<WorkoutData> arrayList = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(this.s[this.u]);
        String dayExcCycles = this.databaseOperations.getDayExcCycles(this.o);
        StringBuilder sb = new StringBuilder();
        sb.append("Day exc cycles DayActivity: ");
        sb.append(dayExcCycles);
        String str = "TAG";
        Log.e(str, sb.toString());
        int[] iArr = new int[0];
        JSONObject jSONObject = null;
        if (dayExcCycles != null) {
            try {
                jSONObject = new JSONObject(dayExcCycles);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        iArr = new int[jSONObject.length()];
        for (int i = 0; i < stringArray.length; i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("prepareAdapterData: ");
            sb2.append(stringArray[i]);
            Log.i(str, sb2.toString());
            TypedArray obtainTypedArray = getResources().obtainTypedArray(((Integer) this.q.get(stringArray[i])).intValue());
            int length = obtainTypedArray.length();
            int[] iArr2 = new int[length];
            WorkoutData workoutData = new WorkoutData();
            for (int i2 = 0; i2 < length; i2++) {
                iArr2[i2] = obtainTypedArray.getResourceId(i2, -1);
            }
            workoutData.setExcName(stringArray[i]);
            workoutData.setExcDescResId(((Integer) this.r.get(stringArray[i])).intValue());
            try {
                iArr[i] = jSONObject.getInt(String.valueOf(i));
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            workoutData.setExcCycles(iArr[i]);
            workoutData.setPosition(i);
            workoutData.setImageIdList(iArr2);
            arrayList.add(workoutData);
        }
        return arrayList;
    }

    @RequiresApi(api = 21)
    @SuppressLint({"WrongConstant"})
    @TargetApi(23)
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.day_layout);
        this.k = (RecyclerView) findViewById(R.id.recyclerAllDaysList);
        this.l = (Button) findViewById(R.id.buttonTwo);
        this.m = new LinearLayoutManager(this, 1, false);
        setAdmodAds();
        c();
        b();
        Bundle extras = getIntent().getExtras();
        this.o = extras.getString("day");
        this.u = extras.getInt("day_num");
        this.p = extras.getFloat("progress");
        this.databaseOperations = new DatabaseOperations(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        ((TextView) toolbar.findViewById(R.id.mtoolbar_title)).setText(this.o);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DayActivity.this.finish();
            }
        });
        TextView textView = (TextView) findViewById(R.id.mtoolbar_title1);
        textView.setText(this.o);
        this.v = d();
        this.n = new IndividualDayAdapter(this, this.o, this.v, 200);
        this.k.setLayoutManager(this.m);
        this.k.setAdapter(this.n);
        this.k.addOnItemTouchListener(new RecyclerItemClickListener(this, new onItemClickListener() {
            public void OnItem(View view, int i) {
                if (i < DayActivity.this.v.size()) {
                    Intent intent = new Intent(DayActivity.this, ExcDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("day", DayActivity.this.o);
                    bundle.putIntArray("framesIdArray", ((WorkoutData) DayActivity.this.v.get(i)).getImageIdList());
                    bundle.putString("excName", ((WorkoutData) DayActivity.this.v.get(i)).getExcName());
                    DayActivity dayActivity = DayActivity.this;
                    bundle.putInt("excNameDescResId", ((Integer) dayActivity.r.get(((WorkoutData) dayActivity.v.get(i)).getExcName())).intValue());
                    bundle.putInt("excCycle", ((WorkoutData) DayActivity.this.v.get(i)).getExcCycles());
                    bundle.putInt("excPosition", i);
                    intent.putExtras(bundle);
                    DayActivity.this.startActivity(intent);
                }
            }
        }));
        this.l.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DayActivity dayActivity = DayActivity.this;
                dayActivity.y = new Intent(dayActivity, MainExcerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("workoutDataList", DayActivity.this.v);
                DayActivity.this.y.putExtras(bundle);
                DayActivity dayActivity2 = DayActivity.this;
                dayActivity2.y.putExtra("day", dayActivity2.o);
                DatabaseOperations databaseOperations = new DatabaseOperations(DayActivity.this);
                DayActivity dayActivity3 = DayActivity.this;
                dayActivity3.p = databaseOperations.getExcDayProgress(dayActivity3.o);
                DayActivity dayActivity4 = DayActivity.this;
                dayActivity4.y.putExtra("progress", dayActivity4.p);
                if (DayActivity.this.w.isLoaded()) {
                    DayActivity.this.w.show();
                    return;
                }
                DayActivity dayActivity5 = DayActivity.this;
                dayActivity5.startActivity(dayActivity5.y);
            }
        });
    }

    public void onDestroy() {
        this.k.setAdapter(null);
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    public void onResume() {
        super.onResume();
        this.v = d();
        this.n = new IndividualDayAdapter(this, this.o, this.v, 200);
        this.k.setAdapter(this.n);
        this.k.setLayoutManager(new LinearLayoutManager(this));
        this.n.notifyDataSetChanged();
    }
}
