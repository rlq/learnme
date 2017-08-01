package com.lq.ren.many.learn.alarm.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.Calendar;

/**
 * Created by lqren on 16/7/26.
 */
public class TimeSetting extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Calendar curCalendar= Calendar.getInstance();
        Calendar alarmCalendar = null;//MainActivity.calendar;
        if(curCalendar.after(alarmCalendar)){
            alarmCalendar.set(Calendar.DAY_OF_YEAR, curCalendar.get(Calendar.DAY_OF_YEAR) + 1);
        }else{
            alarmCalendar.set(Calendar.DAY_OF_YEAR, curCalendar.get(Calendar.DAY_OF_YEAR));
        }
        AlarmUtils.setAlarmTime(context, alarmCalendar);
    }
}
