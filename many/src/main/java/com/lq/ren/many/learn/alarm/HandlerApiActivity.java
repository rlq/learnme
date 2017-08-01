package com.lq.ren.many.learn.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by lqren on 16/7/30.
 * click alarm, handle a lot of cases.
 */
public class HandlerApiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getAction().equals("0")){
                handleAlarmSetting(intent);
            }else if(intent.getAction().equals("1")){
                handleShowAlarm();
            }else{
                handleSetTimer(intent);
            }
        }
    }

    private void handleAlarmSetting(Intent intent){
        final int hour = intent.getIntExtra("hour", -1);
        final int minutes ;
        if(intent.hasExtra("minutes")){
            minutes = intent.getIntExtra("minutes", -1);
        }else{
            minutes = 0;
        }

        if(hour < 0 || hour > 23 || minutes < 0 || minutes >59){
            Intent createAlarm = new Intent(this, AlarmActivity.class);
            createAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(createAlarm);
            finish();
        }
    }

    private void handleShowAlarm(){
    }

    private void handleSetTimer(Intent intent){

    }
}
