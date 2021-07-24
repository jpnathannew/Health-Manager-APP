
package com.homeworkouts.fitnessloseweight.WalkandStep.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.UnitHelper;

import java.text.SimpleDateFormat;
import java.util.Date;



public class StepCount {


   private static double METRIC_RUNNING_FACTOR = 1.02784823;
    private static double METRIC_WALKING_FACTOR = 0.708;
    private static double METRIC_AVG_FACTOR = (METRIC_RUNNING_FACTOR + METRIC_WALKING_FACTOR) / 2;


    private int stepCount;
    private long startTime;
    private long endTime;
    private WalkingMode walkingMode;

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public WalkingMode getWalkingMode() {
        return walkingMode;
    }

    public void setWalkingMode(WalkingMode walkingMode) {
        this.walkingMode = walkingMode;
    }



    public double getDistance(){
        if(getWalkingMode() != null) {
            return getStepCount() * getWalkingMode().getStepLength();
        }else{
            return 0;
        }
    }



    public double getCalories(Context context){

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        float bodyWeight = Float.parseFloat(sharedPref.getString(context.getString(R.string.pref_weight),context.getString(R.string.pref_default_weight)));
        return bodyWeight * METRIC_AVG_FACTOR * UnitHelper.metersToKilometers(getDistance());
    }
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
        return "StepCount{" + format.format(new Date(startTime)) +
                " - " + format.format(new Date(endTime)) +
                ": " + stepCount + " @ " + ((walkingMode == null) ? -1 : walkingMode.getId())+
                '}';
    }
}
