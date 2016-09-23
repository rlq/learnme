package com.lq.ren.many.learn.alarm.utils;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by lqren on 16/7/30.
 */
public class AlarmWakeLock {

    public static PowerManager.WakeLock createPartoalWakeLock(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
    }

    public static void acquireFullWakeUp(Context context, long ms){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
         PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "");
        wl.acquire();

    }
}
