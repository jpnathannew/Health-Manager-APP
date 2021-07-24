package com.homeworkouts.fitnessloseweight.shashikant.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.homeworkouts.fitnessloseweight.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    public static List<String> dayString;
    String TAG = getClass().getSimpleName();
    private int calMaxP;
    private String curentDateString;

    /* renamed from: df */
    DateFormat f2617df;
    private int firstDay;
    private ArrayList<String> items;
    private ArrayList<String> items1;
    private ArrayList<String> items2;
    private String itemvalue;
    private Context mContext;
    private int maxP;
    private int maxWeeknumber;
    private int mnthlength;
    private Calendar month;
    public GregorianCalendar pmonth;
    public GregorianCalendar pmonthmaxset;
    private View previousView;
    private GregorianCalendar selectedDate;

    public long getItemId(int i) {
        return 0;
    }

    public CalendarAdapter(Context context, GregorianCalendar gregorianCalendar) {
        this.mContext = context;
        initCalendarAdapter(gregorianCalendar, null);
    }

    public void initCalendarAdapter(GregorianCalendar gregorianCalendar, onSNPCalendarViewListener onsnpcalendarviewlistener) {
        dayString = new ArrayList();
        this.month = gregorianCalendar;
        this.selectedDate = (GregorianCalendar) gregorianCalendar.clone();
        this.month.set(5, 1);
        this.items = new ArrayList<>();
        this.items1 = new ArrayList<>();
        this.items2 = new ArrayList<>();
        adaptersetDate(this.selectedDate, onsnpcalendarviewlistener);
        refreshDays();
    }

    public void setItems(ArrayList<String> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i != arrayList.size(); i++) {
                if (((String) arrayList.get(i)).length() == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append((String) arrayList.get(i));
                    arrayList.set(i, sb.toString());
                }
            }
            this.items = arrayList;
        }
    }

    public void setItems1(ArrayList<String> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i != arrayList.size(); i++) {
                if (((String) arrayList.get(i)).length() == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append((String) arrayList.get(i));
                    arrayList.set(i, sb.toString());
                }
            }
            this.items1 = arrayList;
        }
    }

    public void setItems2(ArrayList<String> arrayList) {
        if (arrayList != null) {
            for (int i = 0; i != arrayList.size(); i++) {
                if (((String) arrayList.get(i)).length() == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append((String) arrayList.get(i));
                    arrayList.set(i, sb.toString());
                }
            }
            this.items2 = arrayList;
        }
    }

    public int getCount() {
        return dayString.size();
    }

    public Object getItem(int i) {
        return dayString.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.snp_calendar_item, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_date);
        ImageView imageView = (ImageView) view.findViewById(R.id.date_icon);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.date_icon1);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.date_icon2);
        String replaceFirst = ((String) dayString.get(i)).split("-")[2].replaceFirst("^0*", "");
        if (Integer.parseInt(replaceFirst) > 1 && i < this.firstDay) {
            textView.setTextColor(view.getResources().getColor(R.color.grey_calendar));
            textView.setClickable(false);
            textView.setFocusable(false);
        } else if (Integer.parseInt(replaceFirst) >= 15 || i <= 28) {
            textView.setTextColor(view.getResources().getColor(R.color.white));
        } else {
            textView.setTextColor(view.getResources().getColor(R.color.grey_calendar));
            textView.setClickable(false);
            textView.setFocusable(false);
        }
        if (((String) dayString.get(i)).equals(this.curentDateString)) {
            setSelected(view);
            this.previousView = view;
        } else {
            view.setBackgroundResource(R.drawable.list_item_background);
        }
        textView.setText(replaceFirst);
        String str = (String) dayString.get(i);
        if (str.length() == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("0");
            sb.append(str);
            str = sb.toString();
        }
        String str2 = this.TAG;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("view_height->");
        sb2.append(view.getHeight());
        Log.d(str2, sb2.toString());
        if (str.length() <= 0 || this.items == null || !this.items.contains(str)) {
            imageView.setVisibility(4);
        } else {
            imageView.setVisibility(0);
        }
        if (str.length() <= 0 || this.items1 == null || !this.items1.contains(str)) {
            imageView2.setVisibility(4);
        } else {
            imageView2.setVisibility(0);
            imageView.setVisibility(4);
        }
        if (str.length() <= 0 || this.items2 == null || !this.items2.contains(str)) {
            imageView3.setVisibility(4);
        } else {
            imageView3.setVisibility(0);
        }
        return view;
    }

    public View setSelected(View view) {
        if (this.previousView != null) {
            this.previousView.setBackgroundResource(R.drawable.list_item_background);
        }
        this.previousView = view;
        view.setBackgroundResource(R.color.selected_background_calendar);
        ((TextView) view.findViewById(R.id.tv_date)).setTextColor(this.mContext.getResources().getColor(R.color.selected_text_color));
        return view;
    }

    public void refreshDays() {
        this.items.clear();
        dayString.clear();
        this.pmonth = (GregorianCalendar) this.month.clone();
        this.firstDay = this.month.get(7);
        this.maxWeeknumber = this.month.getActualMaximum(4);
        this.mnthlength = this.maxWeeknumber * 7;
        this.maxP = getMaxP();
        this.calMaxP = this.maxP - (this.firstDay - 1);
        this.pmonthmaxset = (GregorianCalendar) this.pmonth.clone();
        this.pmonthmaxset.set(5, this.calMaxP + 1);
        for (int i = 0; i < this.mnthlength; i++) {
            this.itemvalue = this.f2617df.format(this.pmonthmaxset.getTime());
            this.pmonthmaxset.add(5, 1);
            dayString.add(this.itemvalue);
        }
    }

    private int getMaxP() {
        if (this.month.get(2) == this.month.getActualMinimum(2)) {
            this.pmonth.set(this.month.get(1) - 1, this.month.getActualMaximum(2), 1);
        } else {
            this.pmonth.set(2, this.month.get(2) - 1);
        }
        return this.pmonth.getActualMaximum(5);
    }

    public void adaptersetDate(GregorianCalendar gregorianCalendar, onSNPCalendarViewListener onsnpcalendarviewlistener) {
        this.f2617df = new SimpleDateFormat("yyyy-MM-dd", Util.getLocale());
        this.selectedDate = gregorianCalendar;
        this.curentDateString = this.f2617df.format(this.selectedDate.getTime());
    }
}
