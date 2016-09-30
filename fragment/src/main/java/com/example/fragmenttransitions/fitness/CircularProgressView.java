package com.example.fragmenttransitions.fitness;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.fragmenttransitions.R;


public class CircularProgressView extends View {

    private Paint mPaint;
    private int mSize = 0;
    private RectF mBounds;

    private static final float sMaxProgress = 360;
    private static final int sAnimSwoopDuration = 1000;
    private static final int sAnimSyncDuration = 300;
    private static final int sDividerAngle = 2;
    private float mCurrentProgress;
    private int mThickness, mColor;
    private int mBgColor = Color.parseColor("#12FFFFFF");

    private float mActualProgress;
    private ValueAnimator mProgressAnimator;

    public CircularProgressView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    protected void init(AttributeSet attrs, int defStyle) {
        Resources resources = getResources();
        mCurrentProgress = 0;
        mThickness = resources.getDimensionPixelOffset(R.dimen.circle_thickness);
        mColor = resources.getColor(R.color.accent_material_light);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        updatePaint();
        mBounds = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();
        int width = getMeasuredWidth() - xPad;
        int height = getMeasuredHeight() - yPad;
        mSize = (width < height) ? width : height;
        setMeasuredDimension(mSize + xPad, mSize + yPad);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSize = (w > h) ? w : h;
        updateBounds();
    }

    private void updateBounds() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int left = paddingLeft + mThickness;
        int top = paddingTop + mThickness;
        mBounds.set(left, top, mSize - left, mSize - top);
    }

    private void updatePaint() {
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mThickness);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(mBgColor);
        canvas.drawArc(mBounds, 270, 360, false, mPaint);
        mPaint.setColor(mColor);
        float sweep = mActualProgress / sMaxProgress * 360;
        canvas.drawArc(mBounds, 270, sweep, false, mPaint);
    }

    public int getThickness() {
        return mThickness;
    }

    public void setThickness(int thickness) {
        this.mThickness = thickness;
        updatePaint();
        updateBounds();
        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
        updatePaint();
        invalidate();
    }


    public float getProgress() {
        return mCurrentProgress;
    }

    public void setProgress(final float currentProgress) {
        if (mActualProgress == 0) {
            mActualProgress = mCurrentProgress = currentProgress;
            startAnimation();
            return;
        }
        this.mCurrentProgress = currentProgress;

        // Reset the determinate animation to approach the new currentProgress
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }
        mProgressAnimator = ValueAnimator.ofFloat(mActualProgress, currentProgress);
        mProgressAnimator.setDuration(sAnimSyncDuration);
        mProgressAnimator.setInterpolator(new LinearInterpolator());
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mActualProgress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mProgressAnimator.start();
        invalidate();
    }

    public void startAnimation() {
        resetAnimation();
    }

    public void resetAnimation() {
        // Cancel all the old animators
        if (mProgressAnimator != null && mProgressAnimator.isRunning())
            mProgressAnimator.cancel();

        // The linear animation shown when progress is updated
        mActualProgress = 0f;
        mProgressAnimator = ValueAnimator.ofFloat(mActualProgress, mCurrentProgress);
        mProgressAnimator.setDuration(sAnimSwoopDuration);
        mProgressAnimator.setInterpolator(new LinearInterpolator());
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mActualProgress = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mProgressAnimator.start();

    }

    Runnable action = new Runnable() {

        @Override
        public void run() {
            setProgress(getProgress() + sDividerAngle * 2);
            postDelayed(action, 1000);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();

        // postDelayed(action, 2000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mProgressAnimator != null) {
            mProgressAnimator.cancel();
            mProgressAnimator = null;
        }
    }
}