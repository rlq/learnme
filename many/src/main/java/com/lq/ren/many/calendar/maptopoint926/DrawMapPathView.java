package com.lq.ren.many.calendar.maptopoint926;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
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
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE);
        Log.i("HEHE", "init");
    }

    //0926 code
    /*@Override
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
    } */

    private final Matrix wavePathScale = new Matrix();
    private int viewWidth = 0;
    private int viewHeight = 0;
    private Path drawingPath = new Path();

    // 0929 matrix code
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        wavePathScale.setScale(viewWidth, viewHeight * 1f);
        //wavePathScale.postTranslate(0, viewHeight / 4);
        Path path = generatePathForCurve(pointF);
        drawingPath.set(path);
        drawingPath.transform(wavePathScale);
        canvas.drawPath(drawingPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    //没有画出来 还是onLayout的作用
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewHeight = getHeight();
        viewWidth = getWidth();
    }

    private Path generatePathForCurve(List<PointF> points) {
        Path path = new Path();
        if (points.isEmpty()) {
            return path;
        }

        PointF prevPoint = points.get(0);// 第0点
        for (int i = 0; i < points.size(); i++) {
            PointF point = points.get(i);
            if (i == 0) {
                path.moveTo(point.x, point.y); //
            } else {
                float midx = (prevPoint.x + point.x) / 2;
                float midy = (prevPoint.x + point.y) / 2;
                if (i == 1) {
                    path.lineTo(midx, midy);
                } else {
                    path.quadTo(prevPoint.x, prevPoint.y, midx, midy);
                }
            }
            prevPoint = point;
            Log.e("HEHE", "point: " + point);
        }
        path.lineTo(prevPoint.x, prevPoint.y);
        return path;
    }

}
