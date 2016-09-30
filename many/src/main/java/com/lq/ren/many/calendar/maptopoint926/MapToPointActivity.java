package com.lq.ren.many.calendar.maptopoint926;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import com.lq.ren.many.R;
import com.lq.ren.many.calendar.progress0924.TimeTicker;

import java.util.List;
import java.util.Random;

/**
 * Author lqren on 16/9/26.
 */
public class MapToPointActivity extends Activity {

    DrawMapPathView view;
    TimeTicker timer;
    private List<PointF> pointF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_maptopoint);

        view = (DrawMapPathView) findViewById(R.id.maptopoint);
        initTimer();
    }

    private void initTimer() {
        timer = new TimeTicker();
        timer.setCallback(runnable);
        timer.start(1000);
    }

    private void addPoint(float x, float y) {
        Log.v("HEHE", "point :" + x + ", " + y);
        view.setPoint(x, y);
    }

    private void cancel() {
        timer.stop();
        timer = null;
        //runnable = null;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //addPoint(randomGenerated(), randomGenerated());
            translatePoint();
        }
    };

    private float randomGenerated() {
        return new Random().nextFloat() * 1000;
    }

    private void translatePoint() {
        float x = randomGenerated();
        float y = randomGenerated();


        addPoint(x, y);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancel();
        runnable = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
