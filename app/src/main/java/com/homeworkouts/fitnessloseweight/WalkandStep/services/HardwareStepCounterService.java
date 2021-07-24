
package com.homeworkouts.fitnessloseweight.WalkandStep.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.TriggerEventListener;
import android.preference.PreferenceManager;

import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.homeworkouts.fitnessloseweight.R;
import com.homeworkouts.fitnessloseweight.WalkandStep.models.StepCount;
import com.homeworkouts.fitnessloseweight.WalkandStep.persistence.StepCountDbHelper;
import com.homeworkouts.fitnessloseweight.WalkandStep.persistence.WalkingModeDbHelper;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.AndroidVersionHelper;

import java.util.Calendar;

import static com.homeworkouts.fitnessloseweight.WalkandStep.persistence.StepCountPersistenceHelper.BROADCAST_ACTION_STEPS_SAVED;




public class HardwareStepCounterService extends AbstractStepDetectorService{
    private static final String LOG_TAG = HardwareStepCounterService.class.getName();
    protected TriggerEventListener listener;

    public HardwareStepCounterService(){
        super("");
    }

    public HardwareStepCounterService(String name) {
        super(name);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(LOG_TAG, "Received onSensorChanged");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(HardwareStepCounterService.this);
        float numberOfHWStepsSinceLastReboot = event.values[0];
        float numberOfHWStepsOnLastSave = sharedPref.getFloat(HardwareStepCounterService.this.getString(R.string.pref_hw_steps_on_last_save), 0);
        float numberOfNewSteps = numberOfHWStepsSinceLastReboot - numberOfHWStepsOnLastSave;
        Log.i(LOG_TAG, numberOfHWStepsSinceLastReboot + " - " + numberOfHWStepsOnLastSave + " = " + numberOfNewSteps);
        // Store new steps
        onStepDetected((int) numberOfNewSteps);
        // store steps since last reboot
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(getString(R.string.pref_hw_steps_on_last_save), numberOfHWStepsSinceLastReboot);
        editor.apply();
    }

    /**
     * Notifies any subscriber about the detected amount of steps
     *
     * @param count The number of detected steps (greater zero)
     */
    @Override
    protected void onStepDetected(int count) {
        if (count <= 0) {
            return;
        }
        Log.i(LOG_TAG, count + " Step(s) detected");
        // broadcast the new steps
        Intent localIntent = new Intent(BROADCAST_ACTION_STEPS_DETECTED)
                // Add new step count
                .putExtra(EXTENDED_DATA_NEW_STEPS, count)
                .putExtra(EXTENDED_DATA_TOTAL_STEPS, count);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        // Save new steps
        StepCount stepCount = new StepCount();
        stepCount.setStepCount((int) count);
        stepCount.setWalkingMode(new WalkingModeDbHelper(this).getActiveWalkingMode());
        stepCount.setEndTime(Calendar.getInstance().getTime().getTime());
        new StepCountDbHelper(this).addStepCount(stepCount);
        Log.i(LOG_TAG, "Stored " + count + " steps");

        // broadcast the event
        localIntent = new Intent(BROADCAST_ACTION_STEPS_SAVED);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        updateNotification();
        stopSelf();
    }

    @Override
    public int getSensorType() {
        Log.i(LOG_TAG, "getSensorType STEP_COUNTER");
        if (AndroidVersionHelper.isHardwareStepCounterEnabled(this.getPackageManager())) {
            return Sensor.TYPE_STEP_COUNTER;
        } else {
            return 0;
        }
    }

    @Override
    protected boolean cancelNotificationOnDestroy(){
        return false;
    }
}
