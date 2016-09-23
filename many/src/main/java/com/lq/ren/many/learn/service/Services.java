package com.lq.ren.many.learn.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import java.util.Calendar;

/**
 * Created by lqren on 16/8/25.
 */
public class Services extends Service implements GpsStatus.Listener,
        SensorEventListener {

    public static final String STEPS = "step";

    private SensorManager mManager;
    private Sensor mStepSensor;
    private Sensor mHeartSensor;

    private LocationManager mLocationManager;
    private GpsStatus mGpsStatus;


    public static void startService(Context context, int step, String id, boolean newSport) {
        Intent service = new Intent(context, Services.class);
        if (step > 0) {
            service.putExtra(STEPS, step);
        }
        context.startService(service);
    }

    public static void stopService(Context context) {
        Intent service = new Intent(context, Services.class);
        context.stopService(service);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int year = Calendar.getInstance().get(Calendar.YEAR);

        mManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepSensor = mManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (mStepSensor != null) {//以下两个参数是API 21 可以使用
            //mStepSensor = mManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER, false)
        }
        mHeartSensor = mManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        if (shouldOpenGps()) {
            if (hasGps() && mLocationManager == null) {
                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.addGpsStatusListener(this);
            }
        }
    }

    private boolean hasGps() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    private boolean shouldOpenGps() {
        // Walk Run_outdoor Bicycle
        return true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int mCurrentGpsEvent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            /**一系列所用的参数都可以从Intent中获取 如step heartRate**/
            boolean newSport = extras.getBoolean("NEW_SPORT");
            if (shouldOpenGps()) {
                if (newSport) {
                    if (hasGps()) {
                        onGpsStatusChanged(mCurrentGpsEvent);
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        if (event == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            return;
        }
        mCurrentGpsEvent = event;
        //gps changed to EventBus post
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        getContentResolver().notifyChange(Uri.parse(""), null);
        switch (sensorEvent.accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                switch (sensorEvent.sensor.getType()) {
                    case Sensor.TYPE_HEART_RATE:
                        heartRateChanged((int) sensorEvent.values[0]);
                        break;
                    case Sensor.TYPE_STEP_COUNTER:
                        stepCounterChanged((int) sensorEvent.values[0]);
                        break;
                }
                break;
        }
    }

    private void heartRateChanged(int value) {
        boolean fast = isHeartRateIsFast(value);
        boolean same = 120 == value; //Cal.getLastHeartRate()
        long now = SystemClock.elapsedRealtime();
        boolean filter = now - mLastHeartTime < 2000; // use default var
        if (filter) {
            return;
        }
        mLastHeartTime = now;
        //Cal.setLastHeartRate(now);
        if (!same) {// && Running

        }
        if(fast){
            heartRateVibrate();
        }
        //Event post
    }

    private long mLastHeartTime;
    private static final int mMaxRate = 120;
    private int mHeartTime;

    private boolean isHeartRateIsFast(int value) {
        if (value > mMaxRate) {
            mHeartTime++;
        } else {
            mHeartTime = 0;
        }
        return mHeartTime >= 5;
    }

    private void heartRateVibrate() {
        vibrate(this, null);
    }

    private void vibrate(Context context, long[] pattern) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if(pattern == null){
            pattern = new long[] {100, 400};
        }
        vibrator.vibrate(pattern, -1);
    }

    //step
    private void stepCounterChanged(int value) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
