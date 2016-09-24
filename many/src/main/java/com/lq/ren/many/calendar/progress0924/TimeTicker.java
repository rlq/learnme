
package com.lq.ren.many.calendar.progress0924;

import android.os.Handler;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Tick the Time
 */
public class TimeTicker {

    private Handler mHandler;

    private Timer mTimer;
    private TimerTask mTask;

    private Runnable mCallback;

    public TimeTicker() {
        mHandler = new Handler();
    }

    public void setCallback(Runnable callback) {
        this.mCallback = callback;
    }

    public void start(long period) {
        stop();

        mTimer = new Timer();
        mTask = new TimerTask() {

            @Override
            public void run() {
                if (mCallback != null) {
                    mHandler.post(mCallback);
                }
            }
        };
        mTimer.scheduleAtFixedRate(mTask, 1, period);
    }

    public void stop() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
