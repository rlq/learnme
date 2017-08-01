package com.lq.ren.many.learn.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lq.ren.many.R;
import com.lq.ren.many.learn.alarm.utils.AlarmUtils;

/**
 * Created by lqren on 16/7/26.
 */
public class RingAlarmActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_ringalarm);
    }

    public void snooze(View view){
        //TODO snooze 10m
    }

    public void close(View view){
        //TODO canel alarm
        AlarmUtils.cancelAlarmTime(this,"", 100000);
        finish();
    }
}
