package com.lq.ren.many.learn.alarm.utils;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by lqren on 16/7/30.
 */
public class AsyncHandler {
    public static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");
    public static final Handler sHandler ;

    static {
        sHandlerThread.start();
        sHandler = new Handler(sHandlerThread.getLooper());
    }

    public static void post(Runnable r){
        sHandler.post(r);
    }

    private AsyncHandler(){

    }
}
