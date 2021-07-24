
package com.homeworkouts.fitnessloseweight.WalkandStep.receivers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.homeworkouts.fitnessloseweight.WalkandStep.Factory;
import com.homeworkouts.fitnessloseweight.WalkandStep.models.WalkingMode;
import com.homeworkouts.fitnessloseweight.WalkandStep.persistence.StepCountPersistenceHelper;
import com.homeworkouts.fitnessloseweight.WalkandStep.persistence.WalkingModePersistenceHelper;
import com.homeworkouts.fitnessloseweight.WalkandStep.utils.StepDetectionServiceHelper;




public class StepCountPersistenceReceiver extends WakefulBroadcastReceiver {
    private static final String LOG_CLASS = StepCountPersistenceReceiver.class.getName();
    private WalkingMode oldWalkingMode;

    private Context context;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepCountPersistenceHelper.storeStepCounts(service, context, oldWalkingMode);
            context.getApplicationContext().unbindService(mServiceConnection);
            StepDetectionServiceHelper.stopAllIfNotRequired(false, context);
            WidgetReceiver.forceWidgetUpdate(context);
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG_CLASS, "Storing the steps");
        this.context = context.getApplicationContext();
        if (intent.hasExtra(WalkingModePersistenceHelper.BROADCAST_EXTRA_OLD_WALKING_MODE)) {
            oldWalkingMode = WalkingModePersistenceHelper.getItem(intent.getLongExtra(WalkingModePersistenceHelper.BROADCAST_EXTRA_OLD_WALKING_MODE, (long) -1), context);
        }
        if(oldWalkingMode == null){
            oldWalkingMode = WalkingModePersistenceHelper.getActiveMode(context);
        }
        // bind to service
        Intent serviceIntent = new Intent(context, Factory.getStepDetectorServiceClass(context.getPackageManager()));
        context.getApplicationContext().bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }
}
