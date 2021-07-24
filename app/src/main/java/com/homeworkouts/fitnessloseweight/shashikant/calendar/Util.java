package com.homeworkouts.fitnessloseweight.shashikant.calendar;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"DefaultLocale"})
public class Util {
    public static String makePrettyDate(String str, String str2) {
        Date date;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            date = null;
        }
        Calendar.getInstance().setTime(date);
        return String.format(new Locale("TR"), "%02d.%02d.%02d %02d:%02d", new Object[]{Integer.valueOf(date.getDate()), Integer.valueOf(date.getMonth() + 1), Integer.valueOf(date.getYear() + 1900), Integer.valueOf(date.getHours()), Integer.valueOf(date.getMinutes())});
    }

    public static String makePrettyDate(String str) {
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(str);
            Calendar.getInstance().setTime(parse);
            return String.format(new Locale("TR"), "%02d.%02d.%02d", new Object[]{Integer.valueOf(parse.getDate()), Integer.valueOf(parse.getMonth() + 1), Integer.valueOf(parse.getYear() + 1900)});
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static long dateToLong(String str) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", new Locale("TR")).parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            date = null;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        return instance.getTimeInMillis();
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd", new Locale("TR")).format(Calendar.getInstance().getTime());
    }

    public static String getCurrentDate(String str) {
        return new SimpleDateFormat(str, new Locale("TR")).format(Calendar.getInstance().getTime());
    }

    public static String getTomorrow() {
        long dateToLong = dateToLong(getCurrentDate()) + 86400000;
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(dateToLong));
        return new SimpleDateFormat("yyyy-MM-dd", getLocale()).format(instance.getTime());
    }

    public static Locale getLocale() {
        return Locale.getDefault();
    }
}
