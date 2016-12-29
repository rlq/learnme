package com.lq.ren.many.calendar.path1205;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Author lqren on 16/12/5.
 */
public class PathView extends RelativeLayout {

    private Paint mPaint = new Paint();
    private Path drawingPath = new Path();
    private int mPaintColor = Color.WHITE;
    private int mLastRaidus = 0;
    private int mCurRaidus = 20;
    private PathItemView mItemView;

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mItemView = new PathItemView(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("HEHE", " onDraw");
        canvas.drawColor(Color.BLACK);
        mPaint.setStrokeWidth(4f);
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.STROKE);
        drawingPath.moveTo(10, 10);
        drawingPath.lineTo(200, 140);
        canvas.drawPath(drawingPath, mPaint);
//        setCircle(canvas);
//        mItemView.setCircle();
        if (mItemView.getParent() != null) {
            ((ViewGroup) mItemView.getParent()).removeView(mItemView);
        }
        addView(mItemView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        Log.w("HEHE", "onMeasure: " + w +", h: " + h);
        setPadding(20, 20, 20, 20);
        setMeasuredDimension(w - 40, h - 40);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void setCircle(Canvas canvas) {
        if (mCurRaidus >= mLastRaidus && mCurRaidus < 20) {
            mCurRaidus++;
        } else if (mCurRaidus <= mLastRaidus && mCurRaidus > 11) {
            mCurRaidus--;
        } else {
            mLastRaidus = mCurRaidus;
        }
        mPaint.setStrokeWidth(3f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setAlpha(255);
        canvas.drawCircle(200, 200, mCurRaidus, mPaint);
        invalidate();
    }

}
