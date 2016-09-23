package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by lqren on 16/8/13.
 * 把一张张图片连续播放产生动画效果
 */
public class Draw5AnimatorFrame extends View {

    private AnimationDrawable frameAni = null;
    private Context mContext;
    private Drawable mBitAni = null;

    public Draw5AnimatorFrame(Context context) {
        super(context);
        mContext = context;
        frameAni = new AnimationDrawable();

        //装载资源 这里用一个循环装载所有名字类似的资源 用处很大
        for (int i = 0; i < 16; i++) {
            int id = getResources().getIdentifier("a" + i, "drawable", mContext.getPackageName());
            mBitAni = getResources().getDrawable(id);

            /** 为动画添加一帧*/
            frameAni.addFrame(mBitAni, 500);
        }

        frameAni.setOneShot(false);//播放是否循环, false 是循环
        setBackgroundDrawable(frameAni);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        frameAni.start();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.d("HEHE", "start");
                frameAni.start();
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("HEHE", "onKeyDown");
        return true;
    }
}
