package com.lq.ren.many.calendar.progress0924;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.lq.ren.many.R;

import java.lang.ref.WeakReference;


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
        openDetailActivity();
    }


    //929 activity animation
    private void openDetailActivity() {


        findViewById(R.id.openDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void open() {
        View avatar = findViewById(R.id.item_icon);
        View title =findViewById(R.id.item_text);
        Intent intent = new Intent(ProgressActivity.this, DetailAniActivity929.class);
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        Pair.create(avatar, "shared_avatar"),
                        Pair.create(title, "shared_title"));

        startActivity(intent, transitionActivity.toBundle());
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
