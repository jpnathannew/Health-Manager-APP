
package com.homeworkouts.fitnessloseweight.WalkandStep.persistence;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.homeworkouts.fitnessloseweight.WalkandStep.models.StepCount;
import com.homeworkouts.fitnessloseweight.WalkandStep.models.WalkingMode;
import com.homeworkouts.fitnessloseweight.WalkandStep.services.AbstractStepDetectorService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class StepCountPersistenceHelper {


    public static final String BROADCAST_ACTION_STEPS_SAVED = "com.homeworkouts.fitnessloseweight.STEPS_SAVED";
    public static final String BROADCAST_ACTION_STEPS_UPDATED = "com.homeworkouts.fitnessloseweight.STEPS_UPDATED";
    public static final String BROADCAST_ACTION_STEPS_INSERTED = "com.homeworkouts.fitnessloseweight.STEPS_INSERTED";
    public static String LOG_CLASS = StepCountPersistenceHelper.class.getName();
    private static SQLiteDatabase db = null;


    public static boolean storeStepCounts(IBinder serviceBinder, Context context, WalkingMode walkingMode) {
        if (serviceBinder == null) {
            Log.e(LOG_CLASS, "Cannot store step count because service binder is null.");
            return false;
        }
        AbstractStepDetectorService.StepDetectorBinder myBinder = (AbstractStepDetectorService.StepDetectorBinder) serviceBinder;
        StepCountDbHelper stepCountDbHelper = new StepCountDbHelper(context);

        // Get the steps since last save
        int stepCountSinceLastSave = myBinder.stepsSinceLastSave();

        StepCount lastStoredStepCount = stepCountDbHelper.getLatestStepCount();
        Calendar calendarOneHourAgo = Calendar.getInstance();
        calendarOneHourAgo.add(Calendar.HOUR, -1);
        if(lastStoredStepCount == null || lastStoredStepCount.getEndTime() < calendarOneHourAgo.getTime().getTime() ||
                lastStoredStepCount.getWalkingMode() != null && walkingMode != null && walkingMode.getId() != lastStoredStepCount.getWalkingMode().getId()) {
            // create new step count if non is stored or last stored step count is older than an hour
            StepCount stepCount = new StepCount();
            stepCount.setWalkingMode(walkingMode);
            stepCount.setStepCount(stepCountSinceLastSave);
            stepCount.setEndTime(Calendar.getInstance().getTime().getTime());
            stepCountDbHelper.addStepCount(stepCount);
        }else{
            lastStoredStepCount.setStepCount(lastStoredStepCount.getStepCount() + stepCountSinceLastSave);
            long oldEndTime = lastStoredStepCount.getEndTime();
            lastStoredStepCount.setEndTime(Calendar.getInstance().getTime().getTime());
            stepCountDbHelper.updateStepCount(lastStoredStepCount, oldEndTime);
            Log.i(LOG_CLASS, "Updating last stored step count - not creating a new one");
        }
        // reset step count
        myBinder.resetStepCount();
        Log.i(LOG_CLASS, "Stored " + stepCountSinceLastSave + " steps");

        // broadcast the event
        Intent localIntent = new Intent(BROADCAST_ACTION_STEPS_SAVED);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);

        return true;
    }

    /**
     * Stores the given step count to database and sends broadcast-event
     *
     * @param stepCount The step count to save
     * @param context       The application context
     * @return true if save was successful else false.
     */
    public static boolean storeStepCount(StepCount stepCount, Context context) {
        new StepCountDbHelper(context).addStepCount(stepCount);

        // broadcast the event
        Intent localIntent = new Intent(BROADCAST_ACTION_STEPS_INSERTED);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        return true;
    }

    /**
     * Updates the given step count in database based on end timestamp
     *
     * @param stepCount The step count to save
     * @param context       The application context
     * @return true if save was successful else false.
     */
    public static boolean updateStepCount(StepCount stepCount, Context context) {
        new StepCountDbHelper(context).updateStepCount(stepCount);
        // broadcast the event
        Intent localIntent = new Intent(BROADCAST_ACTION_STEPS_UPDATED);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent);
        return true;
    }

    /**
     * Get the number of steps for the given day
     *
     * @param calendar The day (in user's timezone)
     * @param context  The application context
     * @return the number of steps
     */
    public static int getStepCountForDay(Calendar calendar, Context context) {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long start_time = calendar.getTimeInMillis();
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        long end_time = calendar.getTimeInMillis();
        return StepCountPersistenceHelper.getStepCountForInterval(start_time, end_time, context);
    }

    /**
     * Get the step count models for the given day
     *
     * @param calendar The day (in user's timezone)
     * @param context  The application context
     * @return the step count models for the day
     */
    public static List<StepCount> getStepCountsForDay(Calendar calendar, Context context) {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        long start_time = calendar.getTimeInMillis();
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        long end_time = calendar.getTimeInMillis();
        return StepCountPersistenceHelper.getStepCountsForInterval(start_time, end_time, context);
    }

    /**
     * @deprecated Use {@link StepCountDbHelper#getStepCountsForInterval(long, long)} instead.
     *
     * Returns the stepCount models for the steps walked in the given interval.
     *
     * @param start_time The start time in users timezone
     * @param end_time   The end time in users timezone
     * @param context    The application context
     * @return The @{see StepCount}-Models between start and end time
     */
    public static List<StepCount> getStepCountsForInterval(long start_time, long end_time, Context context) {
        if (context == null) {
            Log.e(LOG_CLASS, "Cannot get step count - context is null");
            return new ArrayList<>();
        }
        return new StepCountDbHelper(context).getStepCountsForInterval(start_time, end_time);
    }

    /**
     * Returns the number of steps walked in the given time interval
     *
     * @param start_time The start time
     * @param end_time   The end time
     * @param context    The application context
     * @return Number of steps between start and end time
     */
    public static int getStepCountForInterval(long start_time, long end_time, Context context) {
        int steps = 0;
        for (StepCount s : getStepCountsForInterval(start_time, end_time, context)) {
            steps += s.getStepCount();
        }
        return steps;
    }

    /**
     * Returns the date of first entry in database
     * @param context Application context
     * @return Date of first entry or default today
     */
    public static Date getDateOfFirstEntry(Context context){
        StepCount s = new StepCountDbHelper(context).getFirstStepCount();
        Date date = Calendar.getInstance().getTime(); // fallback is today
        if(s != null){
            date.setTime(s.getEndTime());
        }
        return date;
    }

    /**
     * Returns the last step count entry for given day
     * @param day the day
     * @param context application context
     * @return the last step count entry for given day
     */
    public static StepCount getLastStepCountEntryForDay(Calendar day, Context context){
        List<StepCount> stepCounts = getStepCountsForDay(day, context);
        if(stepCounts.size() == 0){
            return null;
        }else{
            return stepCounts.get(stepCounts.size() - 1);
        }
    }

    /**
     * Returns the writable database
     *
     * @param context The application context
     * @return a writable database object
     */
    protected static SQLiteDatabase getDB(Context context) {
        if (StepCountPersistenceHelper.db == null) {
            StepCountDbHelper dbHelper = new StepCountDbHelper(context);
            StepCountPersistenceHelper.db = dbHelper.getWritableDatabase();
        }
        return StepCountPersistenceHelper.db;
    }
}
