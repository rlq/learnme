package com.lq.ren.many.calendar.progress0924;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lq.ren.many.R;


/**
 * Author lqren on 16/9/24.
 * 但9.26才搞定
 */
public class ProgressActivity extends Activity {

    private CircularProgressView progressView;
    private TimeTicker timer;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress);
        progressView = (CircularProgressView) findViewById(R.id.circle_progress);
        initTimer();
    }

    private void initTimer() {
        timer = new TimeTicker();
        timer.setCallback(runnable);
        timer.start(1000);
    }

    private void updateProgress(float cur) {
        if (cur > 60) {
            cancel();
            return;
        }
        Log.i("HEHE", "update cur " + cur);
        progressView.setProgress(cur / 60 * 360f); //这里需* 360f
        progressView.setColor(getResources().getColor(R.color.colorAccent));
    }

    private void cancel() {
        timer.stop();
        timer = null;
        runnable = null;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateProgress(++count);
        }
    };
}
