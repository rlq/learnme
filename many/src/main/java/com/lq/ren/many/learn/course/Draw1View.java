package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lqren on 16/8/13.
 */
public class Draw1View extends View implements Runnable{

    private Paint mPaint = null;


    public Draw1View(Context context) {
        super(context);
        mPaint = new Paint();
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);//juchi
        mPaint.setColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        mPaint.setColor(Color.YELLOW);
        mPaint.setColor(Color.GREEN);
        mPaint.setColor(Color.rgb(255, 0, 0));

        //get color
//        Color.red(0xcccccc);
//        Color.green(0xcccccc);

        //alpha or AGRB(a, r, g, b)
        mPaint.setARGB(255, 255, 0, 0);
        mPaint.setAlpha(220);

        mPaint.set(new Paint());//没有设置颜色 就是黑色
//
//        //font size style
        mPaint.setTextSize(24);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);//空心的外框
        //空心矩形
        canvas.drawRect(0,20, 140, 60, mPaint);
       // canvas.drawRect((320 -80)/2 , 20, (320 - 80)/2 + 80, 20 + 40, mPaint);

        //绿色实心矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(100, 20, 150, 20 + 40, mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return true;
    }


    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try{
                Thread.sleep(100);

            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            postInvalidate();//该方法可以在线程中更新界面
        }
    }
}
