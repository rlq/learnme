package com.lq.ren.many.calendar.view.step19;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Author lqren on 16/9/19.
 */
public class LoadingView extends View {

    private final Context mContext;
    private Paint mPaint;
    private int mMeasureWidth;
    private int mMeastureHeight;

    private ValueAnimator valueAnimator;
    private float animatedValue;

    public LoadingView(Context context) {
        super(context, null);
        this.mContext = context;
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#6085dc"));//使用TypeStyle
        mPaint.setStyle(Paint.Style.STROKE);

        valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(8000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void startAnimation() {
        if (valueAnimator != null && !valueAnimator.isRunning()) {
            valueAnimator.start();
        }
    }

    public void stopAnimation() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == VISIBLE) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeastureHeight = getMeasuredHeight();
        mMeasureWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(dp2px(4));
        canvas.drawCircle(mMeasureWidth / 2, mMeastureHeight / 2, dp2px(20), mPaint);
        mPaint.setStrokeWidth(dp2px(3));
        canvas.save();

        mPaint.setStrokeWidth(dp2px(3));
        canvas.rotate(animatedValue * 4 * 360, mMeasureWidth / 2, mMeastureHeight / 2);
        canvas.drawLine(mMeasureWidth / 2, mMeastureHeight / 2, mMeasureWidth / 2, mMeastureHeight / 2 - dp2px(14), mPaint);
        canvas.restore();

        canvas.save();
        mPaint.setStrokeWidth(dp2px(3));
        canvas.rotate(animatedValue * 360, mMeasureWidth / 2, mMeastureHeight / 2);
        canvas.drawLine(mMeasureWidth / 2, mMeastureHeight / 2, mMeasureWidth / 2 + dp2px(11), mMeastureHeight / 2, mPaint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("HEHE", "onSizeChanged: " + w + ", " + h + ", " + oldw + ", " + oldh);
//        mMeastureHeight = getMeasuredHeight();
//        mMeasureWidth = getMeasuredWidth();
    }

    public float dp2px(float value) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return value * density;
    }
}
