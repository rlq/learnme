package com.lq.ren.many.learn.alarm.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.lq.ren.many.learn.alarm.CallAlarm;

import java.util.Calendar;

/**
 * Created by lqren on 16/7/26.
 */
public class AlarmUtils {

    public static void setAlarmTime(Context context, Calendar calendar){
        long timeInMIllis = calendar.getTimeInMillis();
        Intent intent = CallAlarm.createAlarmReceiverIntent(context, 10000);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pending = PendingIntent.getBroadcast(context,
                intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        if(Build.VERSION.SDK_INT>=19)
        {
            manager.setExact(AlarmManager.RTC_WAKEUP, timeInMIllis , pending);
        }else{
            manager.set(AlarmManager.RTC_WAKEUP, timeInMIllis , pending);
        }


    }

    public static void canelAlarmTime(Context context, String action,
                                      int id){
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pi);
    }

    public void codeStyle(){
    }

}
