
package com.homeworkouts.fitnessloseweight.WalkandStep.services;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import com.homeworkouts.fitnessloseweight.R;



public class MovementSpeedService extends IntentService implements LocationListener, SharedPreferences.OnSharedPreferenceChangeListener {


    public static final String BROADCAST_ACTION_SPEED_CHANGED = "com.homeworkouts.fitnessloseweight.SPEED_CHANGED";


    public static final String EXTENDED_DATA_CURRENT_SPEED = "com.homeworkouts.fitnessloseweight.CURRENT_SPEED";

    private static final String LOG_TAG = MovementSpeedService.class.getName();
    private final IBinder mBinder = new MovementSpeedBinder();
    private LocationManager mLocationManager;
    private Float speed;
    private double curTime = 0;
    private double oldLat = 0.0;
    private double oldLon = 0.0;



    public MovementSpeedService(String name) {
        super(name);
    }

    public MovementSpeedService() {
        this("");
        // required empty constructor
    }

    @Override
    public void onLocationChanged(final Location location) {
        //your code here
        Log.i(LOG_TAG, "Location changed");
        calculateSpeed(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "Destroying MovementSpeedService.");
        this.mLocationManager.removeUpdates(this);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "Starting MovementSpeedService.");
        String providerName = getProviderName();
        Log.i(LOG_TAG, "Using " + providerName + " as location provider");
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                providerName != null) {
            mLocationManager.requestLocationUpdates(providerName, 0, 0, this);
        }else{

        }
        return START_STICKY;
    }

    @Override
    public void onHandleIntent(Intent intent) {
        // currently doing nothing here.
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Detect changes on preferences and update our internal variable
        if (key.equals(getString(R.string.pref_daily_step_goal))) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    /**
     * @return Name of location provider
     */
    private String getProviderName() {
        Criteria criteria = new Criteria();
        criteria.setSpeedRequired(true);
        return mLocationManager.getBestProvider(criteria, true);
    }

    /**
     * Calculates the speed from current location object.
     * If location has speed, this one will be used else the speed will be calculated from current
     * and last position.
     */
    private void calculateSpeed(Location location){
        double newTime= System.currentTimeMillis();
        double newLat = location.getLatitude();
        double newLon = location.getLongitude();
        if(location.hasSpeed()){
            Log.i(LOG_TAG, "Location has speed");
            speed = location.getSpeed();
        } else {
            Log.i(LOG_TAG, "Location has no speed");
            double distance = calculationBydistance(newLat,newLon,oldLat,oldLon);
            double timeDifferent = (newTime - curTime) / 1000; // seconds
            speed = (float) (distance / timeDifferent);
            curTime = newTime;
            oldLat = newLat;
            oldLon = newLon;
        }

        Intent localIntent = new Intent(BROADCAST_ACTION_SPEED_CHANGED)
                // Add new step count
                .putExtra(EXTENDED_DATA_CURRENT_SPEED, speed);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        Log.i(LOG_TAG, "New speed is " + speed + "m/sec " + speed * 3.6 + "km/h" );
    }

    /**
     * Calculates the distance between two lat/lon-pairs.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return Distance in meters
     */
    private double calculationBydistance(double lat1, double lon1, double lat2, double lon2){
        double radius = 6371000.785;//EARTH_RADIUS;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return radius * c;
    }

    /**
     * Class used for the client Binder.
     *
     * @author Tobias Neidig
     * @version 20170530
     */
    public class MovementSpeedBinder extends Binder {
        public Float getSpeed() { return MovementSpeedService.this.speed; } // TODO
        public MovementSpeedService getService() {
            return MovementSpeedService.this;
        }
    }
}
