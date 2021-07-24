
package com.homeworkouts.fitnessloseweight.WalkandStep.utils;

import android.content.pm.PackageManager;
import android.os.Build;


public class AndroidVersionHelper {

    public static boolean supportsStepDetector(PackageManager pm) {

        return Build.VERSION.SDK_INT >= 21

                && pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
                && pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
    }

    public static boolean isHardwareStepCounterEnabled(PackageManager pm){
        return supportsStepDetector(pm);
    }
}
