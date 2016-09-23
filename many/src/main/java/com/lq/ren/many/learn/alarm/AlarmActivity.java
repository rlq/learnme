package com.lq.ren.many.learn.alarm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by lqren on 16/7/22.
 */
public class AlarmActivity extends AppCompatActivity {

    private Handler mUiHandler;
    private Runnable mFinishRunnable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建一个对话框
        new AlertDialog.Builder(this).setTitle("闹钟")
                .setMessage("闹钟响了，起床啦，懒虫！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 结束该Activity
                        AlarmActivity.this.finish();
                    }
                }).show();

        setHandler();
    }

    private void setHandler(){
        mUiHandler = new Handler();
        mFinishRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e("HEHE", "finish after 3 seconds, snooze alarm ");
                finish();
            }
        };

        mUiHandler.postDelayed(mFinishRunnable, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUiHandler.removeCallbacks(mFinishRunnable);
    }
}

