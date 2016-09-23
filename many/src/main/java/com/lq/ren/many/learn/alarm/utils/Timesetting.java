package com.lq.ren.many.learn.alarm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.Calendar;

/**
 * Created by lqren on 16/7/26.
 */
public class Timesetting extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("HEHE", "time set onReceive");

        Calendar curCalendar= Calendar.getInstance();
        Calendar alarmCalendar = null;//MainActivity.calendar;
        Log.i("HEHE", "cur "+ curCalendar + " \n alarm "+ alarmCalendar);
        if(curCalendar.after(alarmCalendar)){
            Log.d("HEHE", "after");
            alarmCalendar.set(Calendar.DAY_OF_YEAR,
                    curCalendar.get(Calendar.DAY_OF_YEAR) + 1);
        }else{
            Log.d("HEHE", "before");

            alarmCalendar.set(Calendar.DAY_OF_YEAR,
                    curCalendar.get(Calendar.DAY_OF_YEAR));
        }
        Log.i("HEHE", " new alarm "+ alarmCalendar);
        AlarmUtils.setAlarmTime(context, alarmCalendar);
    }
}
