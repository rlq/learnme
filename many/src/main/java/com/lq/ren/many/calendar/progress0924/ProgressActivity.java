package com.lq.ren.many.calendar.progress0924;

import android.app.Activity;
import android.os.Bundle;

import com.lq.ren.many.R;

import java.util.TimerTask;

/**
 * Author lqren on 16/9/24.
 */
public class ProgressActivity extends Activity {

    private CircularProgressView progressView;
    private TimeTicker timer;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressView = (CircularProgressView) findViewById(R.id.circle_progress);

    }

    private void initTimer() {
        timer = new TimeTicker();
        timer.setCallback(runnable);
        timer.start(count);
    }

    private void updateProgress(float cur) {
        progressView.setProgress(cur / 60);
        progressView.setColor(getResources().getColor(R.color.colorAccent));
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateProgress(++count);
        }
    };
}
