package com.homeworkouts.fitnessloseweight.shashikant.calendar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.homeworkouts.fitnessloseweight.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SNPCalendarView extends LinearLayout {
    private static final String TODAY = "today";
    onSNPCalendarViewListener calendarListener;
    public Runnable calendarUpdater = new Runnable() {
        public void run() {
            SNPCalendarView.this.gridview.setExpanded(true);
            StringBuilder sb = new StringBuilder();
            sb.append("month:");
            sb.append(SNPCalendarView.this.month.get(2) + 1);
            sb.append(" year:");
            sb.append(SNPCalendarView.this.month.get(1));
            Log.d("", sb.toString());
            SNPCalendarView.this.calendaradapter.notifyDataSetChanged();
        }
    };

    public CalendarAdapter calendaradapter;

    public String currentSelectedDate;

    public ExpandableHeightGridView gridview;
    private Handler handler;
    ImageView imageview_next;
    ImageView imageview_previous;
    private String initialDate;
    private Locale locale;

    public GregorianCalendar month;
    TextView textview_month_title;
    private View view;

    public SNPCalendarView(Context context) {
        super(context);
        init(context);
    }

    @TargetApi(11)
    public SNPCalendarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public SNPCalendarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        this.view = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.snp_calendarview, null, false);
        this.month = (GregorianCalendar) GregorianCalendar.getInstance();
        this.month.setTimeInMillis(Util.dateToLong(getInitialDate()));
        this.currentSelectedDate = new SimpleDateFormat("yyyy-MM-dd", Util.getLocale()).format(this.month.getTime());
        this.calendaradapter = new CalendarAdapter(context, this.month);
        this.imageview_previous = (ImageView) this.view.findViewById(R.id.imageview_previous);
        this.imageview_next = (ImageView) this.view.findViewById(R.id.imageview_next);
        this.textview_month_title = (TextView) this.view.findViewById(R.id.textview_month_title);
        this.gridview = (ExpandableHeightGridView) this.view.findViewById(R.id.gridview);
        this.gridview.setAdapter(this.calendaradapter);
        this.handler = new Handler();
        this.handler.post(this.calendarUpdater);
        this.textview_month_title.setText(DateFormat.format("MMMM yyyy", this.month));
        this.imageview_previous.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SNPCalendarView.this.setPreviousMonth();
                SNPCalendarView.this.refreshCalendar();
            }
        });
        this.imageview_next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                SNPCalendarView.this.setNextMonth();
                SNPCalendarView.this.refreshCalendar();
            }
        });
        this.gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ((CalendarAdapter) adapterView.getAdapter()).setSelected(view);
                SNPCalendarView.this.currentSelectedDate = (String) CalendarAdapter.dayString.get(i);
                int parseInt = Integer.parseInt(SNPCalendarView.this.currentSelectedDate.split("-")[2].replaceFirst("^0*", ""));
                if (parseInt > 10 && i < 8) {
                    SNPCalendarView.this.setPreviousMonth();
                    SNPCalendarView.this.refreshCalendar();
                } else if (parseInt < 15 && i > 28) {
                    SNPCalendarView.this.setNextMonth();
                    SNPCalendarView.this.refreshCalendar();
                }
                ((CalendarAdapter) adapterView.getAdapter()).setSelected(view);
                SNPCalendarView.this.month.setTimeInMillis(Util.dateToLong(SNPCalendarView.this.currentSelectedDate));
                SNPCalendarView.this.calendaradapter.initCalendarAdapter(SNPCalendarView.this.month, SNPCalendarView.this.calendarListener);
                if (SNPCalendarView.this.calendarListener != null) {
                    SNPCalendarView.this.refreshCalendar();
                }
                SNPCalendarView.this.calendarListener.onDateChanged(SNPCalendarView.this.currentSelectedDate);
            }
        });
        addView(this.view);
    }


    public void setNextMonth() {
        if (this.month.get(2) == this.month.getActualMaximum(2)) {
            this.month.set(this.month.get(1) + 1, this.month.getActualMinimum(2), 1);
        } else {
            this.month.set(2, this.month.get(2) + 1);
        }
    }


    public void setPreviousMonth() {
        if (this.month.get(2) == this.month.getActualMinimum(2)) {
            this.month.set(this.month.get(1) - 1, this.month.getActualMaximum(2), 1);
        } else {
            this.month.set(2, this.month.get(2) - 1);
        }
    }


    public void showToast(String str) {
        Toast.makeText(getContext(), str, 0).show();
    }

    public int getSelectedMonth() {
        return this.month.get(2) + 1;
    }

    public int getSelectedYear() {
        return this.month.get(1);
    }

    public void refreshCalendar() {
        this.calendaradapter.refreshDays();
        this.handler.post(this.calendarUpdater);
        this.textview_month_title.setText(DateFormat.format("MMMM yyyy", this.month));
        if (this.calendarListener != null) {
            this.calendarListener.onDisplayedMonthChanged(this.month.get(2) + 1, this.month.get(1), (String) DateFormat.format("MMMM", this.month));
        }
    }

    public void setOnCalendarViewListener(onSNPCalendarViewListener onsnpcalendarviewlistener) {
        this.calendarListener = onsnpcalendarviewlistener;
    }

    public String getInitialDate() {
        if (this.initialDate == null) {
            return Util.getCurrentDate();
        }
        return this.initialDate;
    }

    public void setDate(String str) {
        if (str.equals(TODAY)) {
            this.initialDate = Util.getCurrentDate();
        } else {
            this.initialDate = str;
        }
        this.initialDate = str;
        this.currentSelectedDate = str;
        this.month.setTimeInMillis(Util.dateToLong(str));
        this.calendaradapter.initCalendarAdapter(this.month, this.calendarListener);
    }

    public String getSelectedDate() {
        return this.currentSelectedDate;
    }

    public void setEvents(ArrayList<String> arrayList) {
        this.calendaradapter.setItems(arrayList);
        this.handler.post(this.calendarUpdater);
    }

    public void setEvents1(ArrayList<String> arrayList) {
        this.calendaradapter.setItems1(arrayList);
        this.handler.post(this.calendarUpdater);
    }

    public void setEvents2(ArrayList<String> arrayList) {
        this.calendaradapter.setItems2(arrayList);
        this.handler.post(this.calendarUpdater);
    }
}
