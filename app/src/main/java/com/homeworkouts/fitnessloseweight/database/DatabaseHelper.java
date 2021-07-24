package com.homeworkouts.fitnessloseweight.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.homeworkouts.fitnessloseweight.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static int f1484a = 3;
    public static String b = "FitDB";
    public static String c = "exc_day";
    public static String d = "day";
    public static String e = "progress";
    public static String f = "counter";
    public static String g = "cycles";
    public Context h;
    public int[] i = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public String j;

    public DatabaseHelper(Context context) {
        super(context, b, null, f1484a);
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(c);
        sb.append(" (");
        sb.append(d);
        sb.append(" TEXT, ");
        sb.append(e);
        sb.append(" REAL, ");
        sb.append(f);
        sb.append(" INTEGER, ");
        sb.append(g);
        sb.append(" TEXT)");
        this.j = sb.toString();
        this.h = context;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(this.j);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        String str = "TAG";
        if (i2 == 2 || i2 == 3) {
            StringBuilder sb = new StringBuilder();
            sb.append("ALTER TABLE ");
            sb.append(c);
            sb.append(" ADD COLUMN ");
            sb.append(g);
            sb.append(" TEXT");
            sQLiteDatabase.execSQL(sb.toString());
            try {
                JSONObject jSONObject = new JSONObject();
                for (int i4 = 1; i4 <= 30; i4++) {
                    int i5 = 0;
                    for (int put : this.h.getResources().getIntArray(this.i[i4 - 1])) {
                        try {
                            jSONObject.put(String.valueOf(i5), put);
                            i5++;
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("json str databs: ");
                    sb2.append(jSONObject.toString());
                    Log.e(str, sb2.toString());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(g, jSONObject.toString());
                    if (sQLiteDatabase != null) {
                        try {
                            String str2 = c;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(d);
                            sb3.append("='Day ");
                            sb3.append(i4);
                            sb3.append("'");
                            long update = (long) sQLiteDatabase.update(str2, contentValues, sb3.toString(), null);
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("res: ");
                            sb4.append(update);
                            Log.e(str, sb4.toString());
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            Log.e(str, "Case 3 db");
        }
    }
}
