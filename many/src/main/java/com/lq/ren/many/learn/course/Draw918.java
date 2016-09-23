package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Author lqren , 16/9/18.
 */
public class Draw918 extends View{

    private Paint mPaint;
    private ArrayList<PointF> mGraphics = new ArrayList<>();

    public Draw918(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PointF point : mGraphics) {
            canvas.drawPoint(point.x, point.y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGraphics.add(new PointF(event.getX(),event.getY()));
        invalidate();
        return true;
    }
}
