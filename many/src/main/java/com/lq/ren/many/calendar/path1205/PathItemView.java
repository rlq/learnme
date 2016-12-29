package com.lq.ren.many.calendar.path1205;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Author lqren on 16/12/5.
 */
public class PathItemView extends View {

    private Paint mPaint = new Paint();
    private int mLastRaidus = 0;
    private int mCurRaidus = 20;

    public PathItemView(Context context) {
        super(context);
    }

    public PathItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCircle() {
        Log.e("HEHE", "item setCircle");
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        Log.d("HEHE", "item onDraw");
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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        Log.w("HEHE", "item onMeasure: " + w +", h: " + h);
        setMeasuredDimension(w, h);
    }
}
