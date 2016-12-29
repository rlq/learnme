package com.he.learnme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Author lqren on 16/12/2.
 */
public class RelaMeasure extends View {

    private Paint mPaint = new Paint();

    public RelaMeasure(Context context) {
        super(context);
        init();
    }

    public RelaMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RelaMeasure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.GREEN);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        Log.e("HEHE", "width: " + MeasureSpec.getSize(widthMeasureSpec) + ", height: " + MeasureSpec.getSize(heightMeasureSpec));
        getMode(MeasureSpec.getMode(widthMeasureSpec));
        getMode(MeasureSpec.getMode(heightMeasureSpec));

    }

    private void getMode(int specMode) {
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                // Parent says we can be as big as we want. Just don't be larger
                // than max size imposed on ourselves.
                Log.i("HEHE", "UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize, and don't be larger than
                // the max size imposed on ourselves.
                Log.i("HEHE", "AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                Log.i("HEHE", "EXACTLY");
                break;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("HEHE", "init width: " + getWidth() + ", height: " + getHeight());
    }
}
