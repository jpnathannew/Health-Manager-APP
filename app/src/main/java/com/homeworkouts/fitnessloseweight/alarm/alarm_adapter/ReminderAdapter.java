package com.homeworkouts.fitnessloseweight.alarm.alarm_adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.google.gson.Gson;
import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.alarm.alarm_pojo_classes.Reminder_custom;
import com.homeworkouts.fitnessloseweight.alarm.alarmmanagerdemo.AlarmHelper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderAdapter extends Adapter<ReminderAdapter.ProductViewHolder> {


    public SimpleDateFormat f1469a;
    public AlarmHelper alarmHelper;
    public Gson gson;
    public Context mCtx;
    public long mLastClickTime = 0;
    public SharedPreferences mSharedPreferences;
    public Editor prefsEditor;
    public List<Reminder_custom> productList;
    public Reminder_custom reminderproduct;

    class ProductViewHolder extends ViewHolder {


        public TextView f1477a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView e;
        public TextView f;
        public TextView g;
        public TextView h;
        public ImageView i;
        public Switch j;

        public ProductViewHolder(View view) {
            super(view);
            this.f1477a = (TextView) view.findViewById(R.id.time);
            this.b = (TextView) view.findViewById(R.id.day1);
            this.c = (TextView) view.findViewById(R.id.day2);
            this.d = (TextView) view.findViewById(R.id.day3);
            this.e = (TextView) view.findViewById(R.id.day4);
            this.f = (TextView) view.findViewById(R.id.day5);
            this.g = (TextView) view.findViewById(R.id.day6);
            this.h = (TextView) view.findViewById(R.id.day7);
            this.i = (ImageView) view.findViewById(R.id.timedelete);
            this.j = (Switch) view.findViewById(R.id.timeswitch);
        }
    }

    public ReminderAdapter(Context context, List<Reminder_custom> list, Gson gson2, SharedPreferences sharedPreferences, Editor editor, AlarmHelper alarmHelper2) {
        this.mCtx = context;
        this.productList = list;
        this.mSharedPreferences = sharedPreferences;
        this.prefsEditor = editor;
        this.gson = gson2;
        this.alarmHelper = alarmHelper2;
    }


    public void showTimePickerDialog(final Reminder_custom reminder_custom, final int i) {
        Calendar instance = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.mCtx, new OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int i, int i2) {
                if (timePicker.isShown()) {
                    Calendar instance = Calendar.getInstance();
                    instance.set(11, i);
                    instance.set(12, i2);
                    ReminderAdapter.this.showDialog(instance, reminder_custom, i);
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
        this.f1469a = simpleDateFormat;
        return simpleDateFormat;
    }

    public int getItemCount() {
        return this.productList.size();
    }

    public SimpleDateFormat getMinuteFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm");
        this.f1469a = simpleDateFormat;
        return simpleDateFormat;
    }

    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(final ProductViewHolder productViewHolder, int i) {
        this.reminderproduct = (Reminder_custom) this.productList.get(i);
        productViewHolder.f1477a.setText(this.reminderproduct.gettime());
        productViewHolder.f1477a.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ReminderAdapter reminderAdapter = ReminderAdapter.this;
                reminderAdapter.reminderproduct = (Reminder_custom) reminderAdapter.productList.get(productViewHolder.getAdapterPosition());
                ReminderAdapter reminderAdapter2 = ReminderAdapter.this;
                reminderAdapter2.showTimePickerDialog(reminderAdapter2.reminderproduct, productViewHolder.getAdapterPosition());
            }
        });
        productViewHolder.b.setVisibility(0);
        productViewHolder.c.setVisibility(0);
        productViewHolder.d.setVisibility(0);
        productViewHolder.e.setVisibility(0);
        productViewHolder.f.setVisibility(0);
        productViewHolder.g.setVisibility(0);
        productViewHolder.h.setVisibility(0);
        if (this.reminderproduct.getMon()) {
            productViewHolder.b.setText("Mon");
        } else {
            productViewHolder.b.setVisibility(8);
        }
        if (this.reminderproduct.getTue()) {
            productViewHolder.c.setText("Tue");
        } else {
            productViewHolder.c.setVisibility(8);
        }
        if (this.reminderproduct.getWen()) {
            productViewHolder.d.setText("Wed");
        } else {
            productViewHolder.d.setVisibility(8);
        }
        if (this.reminderproduct.getThr()) {
            productViewHolder.e.setText("Thu");
        } else {
            productViewHolder.e.setVisibility(8);
        }
        if (this.reminderproduct.getFri()) {
            productViewHolder.f.setText("Fri");
        } else {
            productViewHolder.f.setVisibility(8);
        }
        if (this.reminderproduct.getSat()) {
            productViewHolder.g.setText("Sat");
        } else {
            productViewHolder.g.setVisibility(8);
        }
        if (this.reminderproduct.getSun()) {
            productViewHolder.h.setText("Sun");
        } else {
            productViewHolder.h.setVisibility(8);
        }
        productViewHolder.j.setChecked(this.reminderproduct.getOnoff());
        productViewHolder.j.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ReminderAdapter reminderAdapter = ReminderAdapter.this;
                reminderAdapter.reminderproduct = (Reminder_custom) reminderAdapter.productList.get(productViewHolder.getAdapterPosition());
                ReminderAdapter.this.reminderproduct.setOnoff(z);
                String json = ReminderAdapter.this.gson.toJson((Object) ReminderAdapter.this.productList);
                ReminderAdapter reminderAdapter2 = ReminderAdapter.this;
                reminderAdapter2.prefsEditor = reminderAdapter2.mSharedPreferences.edit();
                ReminderAdapter.this.prefsEditor.putString("Reminder_customObjectList", json);
                ReminderAdapter.this.prefsEditor.apply();
            }
        });
        productViewHolder.i.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - ReminderAdapter.this.mLastClickTime >= 1000) {
                    ReminderAdapter.this.mLastClickTime = SystemClock.elapsedRealtime();
                    ReminderAdapter.this.removeAt(productViewHolder.getAdapterPosition());
                }
            }
        });
    }

    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ProductViewHolder(LayoutInflater.from(this.mCtx).inflate(R.layout.layout_remindercustom_row, null));
    }

    public void removeAt(int i) {
        this.productList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, this.productList.size());
        String json = this.gson.toJson((Object) this.productList);
        this.prefsEditor = this.mSharedPreferences.edit();
        this.prefsEditor.putString("Reminder_customObjectList", json);
        this.prefsEditor.apply();
    }

    public void showDialog(Calendar calendar, Reminder_custom reminder_custom, int i) {
        Builder builder = new Builder(this.mCtx);
        builder.setTitle("Days");
        final ArrayList arrayList = new ArrayList();
        builder.setMultiChoiceItems(this.mCtx.getResources().getStringArray(R.array.day_list), null, new OnMultiChoiceClickListener() {
            public void onClick(DialogInterface dialogInterface, int i, boolean z) {
                if (z) {
                    arrayList.add(Integer.valueOf(i));
                } else if (arrayList.contains(Integer.valueOf(i))) {
                    arrayList.remove(Integer.valueOf(i));
                }
            }
        });
        String string = this.mCtx.getString(17039370);
        final Reminder_custom reminder_custom2 = reminder_custom;
        final Calendar calendar2 = calendar;
        final int i2 = i;
        DialogInterface.OnClickListener r2 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (arrayList.size() > 0) {
                    dialogInterface.dismiss();
                    reminder_custom2.settime(ReminderAdapter.this.startTimeFormat().format(calendar2.getTime()));
                    reminder_custom2.setMon(false);
                    reminder_custom2.setTue(false);
                    reminder_custom2.setWen(false);
                    reminder_custom2.setThr(false);
                    reminder_custom2.setFri(false);
                    reminder_custom2.setSat(false);
                    reminder_custom2.setSun(false);
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(0))) {
                            reminder_custom2.setMon(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(1))) {
                            reminder_custom2.setTue(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(2))) {
                            reminder_custom2.setWen(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(3))) {
                            reminder_custom2.setThr(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(4))) {
                            reminder_custom2.setFri(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(5))) {
                            reminder_custom2.setSat(true);
                        } else if (((Integer) arrayList.get(i2)).equals(Integer.valueOf(6))) {
                            reminder_custom2.setSun(true);
                        }
                    }
                    reminder_custom2.setOnoff(true);
                    ReminderAdapter reminderAdapter = ReminderAdapter.this;
                    reminderAdapter.a(reminderAdapter.alarmHelper, calendar2);
                    String json = ReminderAdapter.this.gson.toJson((Object) ReminderAdapter.this.productList);
                    ReminderAdapter reminderAdapter2 = ReminderAdapter.this;
                    reminderAdapter2.prefsEditor = reminderAdapter2.mSharedPreferences.edit();
                    ReminderAdapter.this.prefsEditor.putString("Reminder_customObjectList", json);
                    ReminderAdapter.this.prefsEditor.apply();
                    ReminderAdapter.this.notifyItemChanged(i2);
                    ReminderAdapter reminderAdapter3 = ReminderAdapter.this;
                    reminderAdapter3.notifyItemRangeChanged(i2, reminderAdapter3.productList.size());
                    return;
                }
                Toast.makeText(ReminderAdapter.this.mCtx, "Please select at-least one day", 0).show();
            }
        };
        builder.setPositiveButton(string, r2);
        builder.setNegativeButton(this.mCtx.getString(17039360), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public SimpleDateFormat startTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        this.f1469a = simpleDateFormat;
        return simpleDateFormat;
    }
}
