package com.lq.ren.fitness;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import com.lq.ren.transitions.R;


/**
 * Author lqren on 16/9/29.
 */
public class ProgressActivity extends Activity {

    private CircularProgressView progressView;
    private TimeTicker timer;
    private int count = 1;

    private View icon;
    private TextView gps;
    private View next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_progress);
        progressView = (CircularProgressView) findViewById(R.id.circle_progress);
        initTimer();
        initView();
    }

    //929 activity animation
    private void initView() {
        icon = findViewById(R.id.item_icon);
        gps = (TextView) findViewById(R.id.item_gps);
        next = findViewById(R.id.item_next);
        gps.setText("gps定位失败\n请重新连接");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetail();
            }
        });
    }

    private void openDetail() {

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra(getString(R.string.transition_shared_avatar), 1);
        intent.putExtra(getString(R.string.transition_shared_title), "gps定位失败\n" +
                "请重新连接");
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        Pair.create(next, getString(R.string.transition_shared_avatar)),
                        Pair.create((View)gps, getString(R.string.transition_shared_title)));

        startActivity(intent, transitionActivity.toBundle());
        //finish();
    }

    private void initTimer() {
        timer = new TimeTicker();
        timer.setCallback(runnable);
        timer.start(1000);
    }

    private void updateProgress(float cur) {
        if (cur >= 60) {
            gps.setText("gps定位失败\n请重新连接");
        }
        Log.i("HEHE", "update cur " + cur);
        progressView.setProgress(cur / 60 * 360f); //这里需* 360f
    }

    private void cancel() {
        if (timer != null)
            timer.stop();
        timer = null;
        runnable = null;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (timer != null) {
                if (count < 63) {
                    updateProgress(++count);
                } else {
                    cancel();
                    openDetail();
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancel();
        finish();
    }
}

