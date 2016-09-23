package com.lq.ren.many.learn.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lqren on 16/7/22.
 */
public class CallAlarm extends BroadcastReceiver {
    public static final String ALARM_RECEIVE_ACTION = "alarm_receive";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.i("HEHE", "call alarm");



        if(action != null && action.equals(ALARM_RECEIVE_ACTION)){
            Intent i = new Intent(context, RingAlarmActivity.class);
            i.setData(intent.getData()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        Intent i= new Intent(context,AlarmActivity.class);
        //默认的跳转类型,将Activity放到一个新的Task中
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static Intent createAlarmReceiverIntent(Context context, long alarmId){
        Intent intent = new Intent(context, CallAlarm.class);
        intent.setAction(ALARM_RECEIVE_ACTION);
        intent.putExtra("id", alarmId);
        return intent;
    }
}
