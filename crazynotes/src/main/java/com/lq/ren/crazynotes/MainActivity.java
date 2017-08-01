package com.lq.ren.crazynotes;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.lq.ren.crazynotes.act.ExpandAct;
import com.lq.ren.crazynotes.tts0515.AudioTrackActivity;
import com.lq.ren.crazynotes.tts0515.AudioTrackPlayer;
import com.lq.ren.crazynotes.tts0515.TTSOffPlayer;

/**
 * 1.定义两个Activity的名称
 * 2.定义两个Activity对应的实现类,前者用于显示PerferenceActivity；后者用于显示ExpandableActivity
 * 3.装配ArrayAdapter适配器，将数据装配到对应的列表项视图中
 * 4.设置该窗口显示的列表所需的adapter
 * 5.重写Intent IntentForPosition(int position)方法：根据列表项返回的intent,用于启动不同的Activity
 */

public class MainActivity extends LauncherActivity implements SensorEventListener {

    private Class<?>[] classNames = {PreferAct.class, ExpandAct.class, AudioTrackActivity.class};//AudioTrackPlayer.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] launcherNames = new String[]{
                getString(R.string.prefer),
                getString(R.string.expandable),
                "tts"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, launcherNames);
        setListAdapter(adapter);

//        initGps();

        if (isAirplaneMode()) {
            setAirplaneMode(false);
        }

        //2017.06.09 sensor
        initSensor();

    }

    //17.05.15
    public void play() {
        new TTSOffPlayer(this);
    }

    @Override
    protected Intent intentForPosition(int position) {
//        play();
        return new Intent(MainActivity.this, classNames[position]);
    }

    public void initGps() {
        mLocationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        //没有打开gps 则跳转到gps设置界面
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0); //设置完成后返回到原来的界面
        }
    }

    private final ContentObserver mGpsMonitor = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            boolean enabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.e("HEHE", "gps enabled? " + enabled);
        }
    };

    private LocationManager mLocationManager;

    @Override
    protected void onStart() {
        super.onStart();
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure
                                .getUriFor(Settings.System.LOCATION_PROVIDERS_ALLOWED),
                        false, mGpsMonitor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getContentResolver().unregisterContentObserver(mGpsMonitor);
    }

    public boolean isAirplaneMode() {
        int isAirplaneMode = Settings.System.getInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        return (isAirplaneMode == 1) ? true : false;
    }

    public void setAirplaneMode(boolean enabling) {
        Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
//        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        intent.putExtra("state", enabling);
//        sendBroadcast(intent);
        Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
        startActivityForResult(intent, 0);
    }

    //2017.06.09 sensor
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor sensor2;
    private int stepCount;
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensor != null && sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
            Log.d("Sensor", "Detector :" + stepCount);
        }

        if (sensor2 != null && sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            Log.w("Sensor", "step counter :" + sensorEvent.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
