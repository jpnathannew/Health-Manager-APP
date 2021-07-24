package com.homeworkouts.fitnessloseweight.alarm.alarm_fragments;

import android.app.AlertDialog.Builder;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.alarm.alarm_adapter.ReminderAdapter;
import com.homeworkouts.fitnessloseweight.alarm.alarm_pojo_classes.Reminder_custom;
import com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo.AlarmHelper;
//import com.crazytrends.fitnessloseweight.R;
//import com.crazytrends.fitnessloseweight.alarm.alarm_adapter.ReminderAdapter;
//import com.crazytrends.fitnessloseweight.alarm.alarm_pojo_classes.Reminder_custom;
//import com.crazytrends.fitnessloseweight.alarm.alarmmanagerdemo.AlarmHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderFragment extends Fragment {
    public static final String TAG = "ReminderFragment";


    public SimpleDateFormat f1478a;
    public AlarmHelper alarmHelper;
    public Gson gson;
    public ReminderAdapter mAdapter;
    public List<Reminder_custom> mReCu;
    public RecyclerView mRecyclerView;
    public SharedPreferences mSharedPreferences;
    public TextView noreminders;
    public Editor prefsEditor;


    public void showTimePickerDialog() {
        Calendar instance = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                if (timePicker.isShown()) {
                    Calendar instance = Calendar.getInstance();
                    instance.set(11, i);
                    instance.set(12, i2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("onTimeSet: ");
                    sb.append(ReminderFragment.this.startTimeFormat().format(instance.getTime()));
                    Log.d(ReminderFragment.TAG, sb.toString());
                    ReminderFragment.this.showDialog(instance);
                }
            }
        }, instance.get(11), instance.get(12), false);
        timePickerDialog.show();
    }

    public void a(AlarmHelper alarmHelper2, Calendar calendar) {
        int i;
        int i2;
        int i3;
        if (startTimeFormat().format(calendar.getTime()).endsWith("AM")) {
            i3 = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            i = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i2 = 0;
        } else {
            i3 = Integer.parseInt(getHourFormat().format(calendar.getTime()));
            i = Integer.parseInt(getMinuteFormat().format(calendar.getTime()));
            i2 = 1;
        }
        alarmHelper2.schedulePendingIntent(i3, i, i2);
    }

    public SimpleDateFormat getHourFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh");
        this.f1478a = simpleDateFormat;
        return simpleDateFormat;
    }

    public SimpleDateFormat getMinuteFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        this.f1478a = simpleDateFormat;
        return simpleDateFormat;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_reminderfragment, viewGroup, false);
        inflate.setTag(TAG);
        Toolbar toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.getNavigationIcon().mutate().setColorFilter(getResources().getColor(R.color.black), Mode.SRC_IN);
        setHasOptionsMenu(true);
        this.alarmHelper = new AlarmHelper(getActivity());
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        this.mRecyclerView = (RecyclerView) inflate.findViewById(R.id.reminderlist);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.noreminders = (TextView) inflate.findViewById(R.id.noreminder);
        this.gson = new Gson();
        this.mReCu = (List) this.gson.fromJson(this.mSharedPreferences.getString("Reminder_customObjectList", null), new TypeToken<List<Reminder_custom>>() {
        }.getType());
        List<Reminder_custom> list = this.mReCu;
        if (list == null || list.size() <= 0) {
            this.mRecyclerView.setVisibility(8);
            this.noreminders.setVisibility(0);
        } else {
            this.mRecyclerView.setVisibility(0);
            ReminderAdapter reminderAdapter = new ReminderAdapter(getActivity(), this.mReCu, this.gson, this.mSharedPreferences, this.prefsEditor, this.alarmHelper);
            this.mAdapter = reminderAdapter;
            this.mRecyclerView.setAdapter(this.mAdapter);
            this.noreminders.setVisibility(8);
        }
        ((FloatingActionButton) inflate.findViewById(R.id.addreminder)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReminderFragment.this.showTimePickerDialog();
            }
        });
        return inflate;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void showDialog(final Calendar calendar) {
        Builder builder = new Builder(getActivity());
        builder.setTitle("Days");
        final ArrayList arrayList = new ArrayList();
        builder.setMultiChoiceItems(getResources().getStringArray(R.array.day_list), null, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                if (z) {
                    arrayList.add(Integer.valueOf(i));
                } else if (arrayList.contains(Integer.valueOf(i))) {
                    arrayList.remove(Integer.valueOf(i));
                }
            }
        });
        builder.setPositiveButton(getString(17039370), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (arrayList.size() > 0) {
                    dialogInterface.dismiss();
                    Reminder_custom reminder_custom = new Reminder_custom();
                    reminder_custom.settime(ReminderFragment.this.startTimeFormat().format(calendar.getTime()));
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(0))) {
                            reminder_custom.setMon(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(1))) {
                            reminder_custom.setTue(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(2))) {
                            reminder_custom.setWen(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(3))) {
                            reminder_custom.setThr(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(4))) {
                            reminder_custom.setFri(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(5))) {
                            reminder_custom.setSat(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(6))) {
                            reminder_custom.setSun(true);
                        }
                    }
                    ReminderFragment reminderFragment = ReminderFragment.this;
                    reminderFragment.a(reminderFragment.alarmHelper, calendar);
                    reminder_custom.setOnoff(true);
                    if (ReminderFragment.this.mReCu == null || ReminderFragment.this.mReCu.size() <= 0) {
                        ReminderFragment.this.mReCu = new ArrayList();
                    }
                    ReminderFragment.this.mReCu.add(reminder_custom);
                    String json = ReminderFragment.this.gson.toJson((Object) ReminderFragment.this.mReCu);
                    ReminderFragment reminderFragment2 = ReminderFragment.this;
                    reminderFragment2.prefsEditor = reminderFragment2.mSharedPreferences.edit();
                    ReminderFragment.this.prefsEditor.putString("Reminder_customObjectList", json);
                    ReminderFragment.this.prefsEditor.apply();
                    ReminderFragment.this.mRecyclerView.setVisibility(0);
                    ReminderFragment reminderFragment3 = ReminderFragment.this;
                    ReminderAdapter reminderAdapter = new ReminderAdapter(reminderFragment3.getActivity(), ReminderFragment.this.mReCu, ReminderFragment.this.gson, ReminderFragment.this.mSharedPreferences, ReminderFragment.this.prefsEditor, ReminderFragment.this.alarmHelper);
                    reminderFragment3.mAdapter = reminderAdapter;
                    ReminderFragment.this.mRecyclerView.setAdapter(ReminderFragment.this.mAdapter);
                    ReminderFragment.this.noreminders.setVisibility(8);
                    return;
                }
                Toast.makeText(ReminderFragment.this.getActivity(), "Please select at-least one day", 0).show();
            }
        });
        builder.setNegativeButton(getString(17039360), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public SimpleDateFormat startTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        this.f1478a = simpleDateFormat;
        return simpleDateFormat;
    }
}
