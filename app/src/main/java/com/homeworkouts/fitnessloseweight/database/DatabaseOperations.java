package com.homeworkouts.fitnessloseweight.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//
//import com.crazytrends.fitnessloseweight.R;
//import com.crazytrends.fitnessloseweight.adapters.WorkoutData;

import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.adapters.WorkoutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {


    public Context f1485a;
    public int[] b;
    public int[] c = {R.array.day1_cycles, R.array.day2_cycles, R.array.day3_cycles, R.array.day4_cycles, R.array.day5_cycles, R.array.day6_cycles, R.array.day7_cycles, R.array.day8_cycles, R.array.day9_cycles, R.array.day10_cycles, R.array.day11_cycles, R.array.day12_cycles, R.array.day13_cycles, R.array.day14_cycles, R.array.day15_cycles, R.array.day16_cycles, R.array.day17_cycles, R.array.day18_cycles, R.array.day19_cycles, R.array.day20_cycles, R.array.day21_cycles, R.array.day22_cycles, R.array.day23_cycles, R.array.day24_cycles, R.array.day25_cycles, R.array.day26_cycles, R.array.day27_cycles, R.array.day28_cycles, R.array.day29_cycles, R.array.day30_cycles};
    public DatabaseHelper dbHelper;
    public SQLiteDatabase sqlite;

    public DatabaseOperations(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.f1485a = context;
    }

    public int CheckDBEmpty() {
        this.sqlite = this.dbHelper.getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM ");
        sb.append(DatabaseHelper.c);
        Cursor rawQuery = this.sqlite.rawQuery(sb.toString(), null);
        rawQuery.moveToFirst();
        return rawQuery.getInt(0) > 0 ? 1 : 0;
    }

    public int deleteTable() {
        this.sqlite = this.dbHelper.getWritableDatabase();
        int delete = this.sqlite.delete(DatabaseHelper.c, null, null);
        this.sqlite.close();
        return delete;
    }

    public List<WorkoutData> getAllDaysProgress() {
        ArrayList arrayList = new ArrayList();
        this.sqlite = this.dbHelper.getReadableDatabase();
        try {
            if (this.sqlite != null) {
                SQLiteDatabase sQLiteDatabase = this.sqlite;
                StringBuilder sb = new StringBuilder();
                sb.append("select * from ");
                sb.append(DatabaseHelper.c);
                Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
                if (rawQuery.moveToFirst()) {
                    while (!rawQuery.isAfterLast()) {
                        WorkoutData workoutData = new WorkoutData();
                        workoutData.setDay(rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.d)));
                        workoutData.setProgress(rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.e)));
                        rawQuery.moveToNext();
                        arrayList.add(workoutData);
                    }
                }
                rawQuery.close();
                this.sqlite.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public int getDayExcCounter(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        int i = 0;
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.c);
            sb.append(" where ");
            sb.append(DatabaseHelper.d);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getInt(rawQuery.getColumnIndex(DatabaseHelper.f));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return i;
    }

    public String getDayExcCycles(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        String str2 = "";
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.c);
            sb.append(" where ");
            sb.append(DatabaseHelper.d);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                str2 = rawQuery.getString(rawQuery.getColumnIndex(DatabaseHelper.g));
            }
            this.sqlite.close();
        }
        return str2;
    }

    public float getExcDayProgress(String str) {
        this.sqlite = this.dbHelper.getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = this.sqlite;
        float f = 0.0f;
        if (sQLiteDatabase != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from ");
            sb.append(DatabaseHelper.c);
            sb.append(" where ");
            sb.append(DatabaseHelper.d);
            sb.append(" = '");
            sb.append(str);
            sb.append("'");
            Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
            if (rawQuery.moveToFirst()) {
                f = rawQuery.getFloat(rawQuery.getColumnIndex(DatabaseHelper.e));
            }
            rawQuery.close();
            this.sqlite.close();
        }
        return f;
    }

    public long insertExcALLDayData() {
        long j = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            for (int i = 1; i <= 30; i++) {
                JSONObject jSONObject = new JSONObject();
                this.b = this.f1485a.getResources().getIntArray(this.c[i - 1]);
                int i2 = 0;
                for (int put : this.b) {
                    try {
                        jSONObject.put(String.valueOf(i2), put);
                        i2++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append("json str databs: ");
                sb.append(jSONObject.toString());
                Log.e("TAG", sb.toString());
                ContentValues contentValues = new ContentValues();
                String str = DatabaseHelper.d;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Day ");
                sb2.append(i);
                contentValues.put(str, sb2.toString());
                contentValues.put(DatabaseHelper.e, Double.valueOf(0.0d));
                contentValues.put(DatabaseHelper.f, Integer.valueOf(0));
                contentValues.put(DatabaseHelper.g, jSONObject.toString());
                if (this.sqlite != null) {
                    try {
                        j = this.sqlite.insert(DatabaseHelper.c, null, contentValues);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
            this.sqlite.close();
        } catch (Exception e3) {
            e3.printStackTrace();
            this.sqlite.close();
        }
        return j;
    }

    public int insertExcCounter(String str, int i) {
        String str2 = "'";
        int i2 = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.f, Integer.valueOf(i));
            if (this.sqlite != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE ");
                    sb.append(DatabaseHelper.c);
                    sb.append(" SET ");
                    sb.append(DatabaseHelper.f);
                    sb.append(" = ");
                    sb.append(i);
                    sb.append(" WHERE ");
                    sb.append(DatabaseHelper.d);
                    sb.append(" = '");
                    sb.append(str);
                    sb.append(str2);
                    sb.toString();
                    SQLiteDatabase sQLiteDatabase = this.sqlite;
                    String str3 = DatabaseHelper.c;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DatabaseHelper.d);
                    sb2.append("='");
                    sb2.append(str);
                    sb2.append(str2);
                    i2 = sQLiteDatabase.update(str3, contentValues, sb2.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqlite.close();
        }
        return i2;
    }

    public int insertExcCycles(String str, String str2) {
        String str3 = "'";
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.g, str2);
            if (this.sqlite != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE ");
                    sb.append(DatabaseHelper.c);
                    sb.append(" SET ");
                    sb.append(DatabaseHelper.g);
                    sb.append(" = ");
                    sb.append(str2);
                    sb.append(" WHERE ");
                    sb.append(DatabaseHelper.d);
                    sb.append(" = '");
                    sb.append(str);
                    sb.append(str3);
                    sb.toString();
                    SQLiteDatabase sQLiteDatabase = this.sqlite;
                    String str4 = DatabaseHelper.c;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(DatabaseHelper.d);
                    sb2.append("='");
                    sb2.append(str);
                    sb2.append(str3);
                    i = sQLiteDatabase.update(str4, contentValues, sb2.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public int insertExcDayData(String str, float f) {
        int i = 0;
        try {
            this.sqlite = this.dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.e, Float.valueOf(f));
            if (this.sqlite != null) {
                try {
                    SQLiteDatabase sQLiteDatabase = this.sqlite;
                    String str2 = DatabaseHelper.c;
                    StringBuilder sb = new StringBuilder();
                    sb.append(DatabaseHelper.d);
                    sb.append("='");
                    sb.append(str);
                    sb.append("'");
                    i = sQLiteDatabase.update(str2, contentValues, sb.toString(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.sqlite.close();
        } catch (Exception e2) {
            e2.printStackTrace();
            this.sqlite.close();
        }
        return i;
    }

    public boolean tableExists(String str) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        boolean z = false;
        if (!(str == null || writableDatabase == null || !writableDatabase.isOpen())) {
            Cursor rawQuery = writableDatabase.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", str});
            if (!rawQuery.moveToFirst()) {
                rawQuery.close();
                return false;
            }
            int i = rawQuery.getInt(0);
            rawQuery.close();
            if (i > 0) {
                z = true;
            }
        }
        return z;
    }
}
