
package com.homeworkouts.fitnessloseweight.WalkandStep;

import android.content.pm.PackageManager;

import com.homeworkouts.fitnessloseweight.WalkandStep.services.AbstractStepDetectorService;
import com.homeworkouts.fitnessloseweight.WalkandStep.services.AccelerometerStepDetectorService;
import com.homeworkouts.fitnessloseweight.WalkandStep.services.HardwareStepDetectorService;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.AndroidVersionHelper;




public class Factory {



    public static Class<? extends AbstractStepDetectorService> getStepDetectorServiceClass(PackageManager pm){
        if(pm != null && AndroidVersionHelper.supportsStepDetector(pm)) {
            return HardwareStepDetectorService.class;
        }else{
            return AccelerometerStepDetectorService.class;
        }
    }
}
