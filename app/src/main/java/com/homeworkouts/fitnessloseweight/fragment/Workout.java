package com.homeworkouts.fitnessloseweight.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.homeworkouts.fitnessloseweight.DayActivity;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.RestDayActivity;
import com.homeworkouts.fitnessloseweight.adapters.AllDayAdapter;
import com.homeworkouts.fitnessloseweight.adapters.WorkoutData;
import com.homeworkouts.fitnessloseweight.d;
import com.homeworkouts.fitnessloseweight.database.DatabaseOperations;
import com.homeworkouts.fitnessloseweight.e;
import com.homeworkouts.fitnessloseweight.listners.RecyclerItemClickListener;
import com.homeworkouts.fitnessloseweight.listners.RecyclerItemClickListener.onItemClickListener;

import com.homeworkouts.fitnessloseweight.utils.AppUtilss;
import com.homeworkouts.fitnessloseweight.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Workout extends Fragment {
    public AllDayAdapter adapter;
    public ArrayList<String> arr;
    public DatabaseOperations databaseOperations;
    public TextView dayleft;
    public int daysCompletionConter = 0;
    public int height;
    public double k = 0.0d;
    public SharedPreferences launchDataPreferences;
    public GridLayoutManager manager;

    public TextView percentScore1;
    public ProgressBar progressBar;
    public BroadcastReceiver progressReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            double doubleExtra = intent.getDoubleExtra(AppUtilss.KEY_PROGRESS, 0.0d);
            try {
                ((WorkoutData) Workout.this.workoutDataList.get(Workout.this.workoutPosition)).setProgress((float) doubleExtra);
                Workout Workout = Workout.this;
                Workout.k = 0.0d;
                Workout.daysCompletionConter = 0;
                for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
                    Workout Workout2 = Workout.this;
                    double d = Workout2.k;
                    double progress = (double) ((WorkoutData) Workout2.workoutDataList.get(i)).getProgress();
                    Double.isNaN(progress);
                    Workout2.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
                    if (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                        Workout.this.daysCompletionConter = Workout.this.daysCompletionConter + 1;
                    }
                }
                Workout Workout3 = Workout.this;
                Workout3.daysCompletionConter = Workout3.daysCompletionConter + (Workout.this.daysCompletionConter / 3);
                Workout.this.progressBar.setProgress((int) Workout.this.k);
                TextView g = Workout.this.percentScore1;
                StringBuilder sb = new StringBuilder();
                sb.append((int) Workout.this.k);
                sb.append("%");
                g.setText(sb.toString());
                TextView h = Workout.this.dayleft;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Constants.TOTAL_DAYS - Workout.this.daysCompletionConter);
                sb2.append(" Days left");
                h.setText(sb2.toString());
                Workout.this.adapter.notifyDataSetChanged();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(doubleExtra);
                Log.i("progress broadcast", sb3.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    public RecyclerView recyclerView;
    public int squareSize;
    public int width;
    public List<WorkoutData> workoutDataList;
    public int workoutPosition = -1;

    public /* synthetic */ void a(int i, MaterialDialog materialDialog, DialogAction dialogAction) {
        TextView textView = null;
        String str = null;
        try {
            materialDialog.dismiss();
            String str2 = (String) this.arr.get(i);
            if (this.workoutDataList != null) {
                this.workoutDataList.clear();
            }
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
            this.workoutDataList = this.databaseOperations.getAllDaysProgress();
            this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
            this.recyclerView.getRecycledViewPool().clear();
            this.recyclerView.setAdapter(this.adapter);
            this.daysCompletionConter--;
            TextView textView2 = this.dayleft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
            sb.append(" Days left");
            textView2.setText(sb.toString());
            if (this.daysCompletionConter > 0) {
                this.progressBar.setProgress((int) (this.k - 4.348d));
                textView = this.percentScore1;
                StringBuilder sb2 = new StringBuilder();
                sb2.append((int) (this.k - 4.348d));
                sb2.append("%");
                str = sb2.toString();
            } else {
                if (this.daysCompletionConter == 0) {
                    this.progressBar.setProgress(0);
                    textView = this.percentScore1;
                    str = "0%";
                }
                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("day", str2);
                intent.putExtra("day_num", i);
                intent.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
                startActivity(intent);
            }
            textView.setText(str);
            Intent intent2 = new Intent(getActivity(), DayActivity.class);
            intent2.putExtra("day", str2);
            intent2.putExtra("day_num", i);
            intent2.putExtra("progress", ((WorkoutData) this.workoutDataList.get(i)).getProgress());
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void b(int i) {
        new Builder(getActivity()).title((CharSequence) "Confirm!").titleColor(ContextCompat.getColor(getActivity(), R.color.black)).content((CharSequence) "Would you like to repeat this workout?").contentColor(ContextCompat.getColor
                (getActivity(), R.color.textColor)).positiveText((CharSequence) "Yes").positiveColor
                (ContextCompat.getColor(getActivity(), R.color.colorAccent))
                .onPositive(new d(this, i)).negativeText((CharSequence) "No")
                .negativeColor(ContextCompat.getColor(getActivity(), R.color.textColor))
                .onNegative(e.f4a).show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0) {
            this.databaseOperations.deleteTable();
            Editor edit = this.launchDataPreferences.edit();
            String str = "daysInserted";
            edit.putBoolean(str, false);
            edit.apply();
            edit.putBoolean(str, true);
            edit.apply();
            List<WorkoutData> list = this.workoutDataList;
            if (list != null) {
                list.clear();
            }
            this.workoutDataList = this.databaseOperations.getAllDaysProgress();
            this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
            this.recyclerView.getRecycledViewPool().clear();
            this.recyclerView.setAdapter(this.adapter);
            this.progressBar.setProgress(0);
            this.percentScore1.setText("0%");
            TextView textView = this.dayleft;
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.TOTAL_DAYS);
            sb.append(" Days left");
            textView.setText(sb.toString());
        }
        super.onActivityResult(i, i2, intent);
    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_workout, viewGroup, false);

//        public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);

//        getActivity().requestWindowFeature(1);
//        getActivity().getWindow().setFlags(1024, 1024);
//        setContentView((int) R.layout.activity_workout);


//        getActivity().setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.percentScore1 = (TextView) inflate.findViewById(R.id.percentScore);
        this.dayleft = (TextView) inflate.findViewById(R.id.daysLeft);
        this.launchDataPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        this.databaseOperations = new DatabaseOperations(getActivity());
        String str = "thirtyday";
        boolean z = this.launchDataPreferences.getBoolean(str, false);
        String str2 = "daysInserted";
        boolean z2 = this.launchDataPreferences.getBoolean(str2, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
        if (!z2 && this.databaseOperations.CheckDBEmpty() == 0) {
            this.databaseOperations.insertExcALLDayData();
            Editor edit = this.launchDataPreferences.edit();
            edit.putBoolean(str2, true);
            edit.apply();
        }
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.progress);
        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));
        for (int i = 0; i < Constants.TOTAL_DAYS; i++) {
            double d = this.k;
            double progress = (double) ((WorkoutData) this.workoutDataList.get(i)).getProgress();
            Double.isNaN(progress);
            this.k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
            if (((WorkoutData) this.workoutDataList.get(i)).getProgress() >= 99.0f) {
                this.daysCompletionConter++;
            }
        }
        int i2 = this.daysCompletionConter;
        this.daysCompletionConter = i2 + (i2 / 3);
        this.progressBar.setProgress((int) this.k);
        TextView textView = this.percentScore1;
        StringBuilder sb = new StringBuilder();
        sb.append((int) this.k);
        sb.append("%");
        textView.setText(sb.toString());
        TextView textView2 = this.dayleft;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Constants.TOTAL_DAYS - this.daysCompletionConter);
        sb2.append(" Days left");
        textView2.setText(sb2.toString());

        this.recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//        this.manager = new GridLayoutManager(getActivity(), 3);

        this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
        this.arr = new ArrayList<>();
        this.recyclerView.getRecycledViewPool().clear();
        for (int i3 = 1; i3 <= Constants.TOTAL_DAYS; i3++) {
            ArrayList<String> arrayList = this.arr;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Day ");
            sb3.append(i3);
            arrayList.add(sb3.toString());
        }
        if (z) {
            Editor edit2 = this.launchDataPreferences.edit();
            edit2.putBoolean(str, false);
            edit2.apply();
            restartExcercise();
            this.daysCompletionConter = 0;
        }
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new onItemClickListener() {
            public void OnItem(View view, int i) {
                Intent intent;
                Workout.this.workoutPosition = i;
                if ((Workout.this.workoutPosition + 1) % 4 == 0) {
                    intent = new Intent(getContext(), RestDayActivity.class);
                } else if (((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress() < 99.0f) {
                    intent = new Intent(getContext(), DayActivity.class);
                    intent.putExtra("day", (String) Workout.this.arr.get(i));
                    intent.putExtra("day_num", i);
                    intent.putExtra("progress", ((WorkoutData) Workout.this.workoutDataList.get(i)).getProgress());
                } else {
                    Workout.this.b(i);
                    return;
                }
                Workout.this.startActivity(intent);
            }
        }));
        getActivity().registerReceiver(this.progressReceiver, new IntentFilter(AppUtilss.WORKOUT_BROADCAST_FILTER));
        if (this.daysCompletionConter > 4) {


        }
//        setHasOptionsMenu(true);
        return inflate;
    }

//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        menu.clear();
//    }

    public void onDestroy() {
        super.onDestroy();
        BroadcastReceiver broadcastReceiver = this.progressReceiver;
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void restartExcercise() {
        Editor edit = this.launchDataPreferences.edit();
        String str = "daysInserted";
        edit.putBoolean(str, false);
        edit.apply();
        for (int i = 0; i < 30; i++) {
            String str2 = (String) this.arr.get(i);
            this.databaseOperations.insertExcDayData(str2, 0.0f);
            this.databaseOperations.insertExcCounter(str2, 0);
        }
        edit.putBoolean(str, true);
        edit.apply();
        List<WorkoutData> list = this.workoutDataList;
        if (list != null) {
            list.clear();
        }
        this.workoutDataList = this.databaseOperations.getAllDaysProgress();
        this.adapter = new AllDayAdapter(getActivity(), this.workoutDataList);
        this.recyclerView.getRecycledViewPool().clear();
        this.recyclerView.setAdapter(this.adapter);
        this.progressBar.setProgress(0);
        this.percentScore1.setText("0%");
        TextView textView = this.dayleft;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.TOTAL_DAYS);
        sb.append(" Days left");
        textView.setText(sb.toString());
    }



}
