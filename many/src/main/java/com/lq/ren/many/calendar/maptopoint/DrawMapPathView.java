package com.lq.ren.many.calendar.maptopoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 16/9/26.
 */
public class DrawMapPathView extends View {

    private Paint mPaint;
    private List<PointF> pointF;
    private int count = 0;

    public void setPoint(float x, float y) {
        pointF.add(++count, new PointF(x, y));
        invalidate();
    }

    public DrawMapPathView(Context context) {
        super(context);
        init();
    }

    public DrawMapPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawMapPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pointF = new ArrayList<>();
        pointF.add(count, new PointF(0, 0));
        mPaint = new Paint();

        Log.i("HEHE", "init");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(100, 300, 500, 900), mPaint);

        Path path = new Path();
        path.moveTo(0, 0);
        for (int i = 1; i < pointF.size(); i++) {
            path.lineTo(pointF.get(i).x, pointF.get(i).y);
        }
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

}
