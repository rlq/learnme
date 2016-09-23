package com.lq.ren.many.calendar.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;


import com.lq.ren.many.R;


import java.util.ArrayList;
import java.util.List;

public class CircleIndicatorView extends View {

    private static final float GAP_ANGLE = 4.5f;

    private int mPageCount = 0;
    private int mCurrentPageIndex = 0;
    private List<Point> mPointList = new ArrayList<Point>();
    private int mCirclePointNormalSize = 20;
    private int mCirclePointSelectedSize = 40;
    private int mCirclePointNormalColor = 0xFF666666;
    private int mCirclePointSelectedColor = 0xFFFFFFFF;
    private Paint mPaint = null;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributesArray = context
                .obtainStyledAttributes(attrs, R.styleable.CircleIndicator, defStyleAttr, 0);
        mCirclePointNormalSize = attributesArray.getDimensionPixelSize(R.styleable.CircleIndicator_pointMinSize,
                mCirclePointNormalSize);
        mCirclePointSelectedSize = attributesArray.getDimensionPixelSize(R.styleable.CircleIndicator_pointMaxSize,
                mCirclePointSelectedSize);
        mCirclePointNormalColor = attributesArray.getColor(R.styleable.CircleIndicator_pointNormalColor,
                mCirclePointNormalColor);
        mCirclePointSelectedColor = attributesArray.getColor(R.styleable.CircleIndicator_pointSelectedColor,
                mCirclePointSelectedColor);
        if (attributesArray != null) {
            attributesArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        updatePoints(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updatePoints(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = getPaint();
        for (int i = 0; i < mPointList.size(); i++) {
            Point point = mPointList.get(i);
            paint.setColor(mCurrentPageIndex == i ? mCirclePointSelectedColor : mCirclePointNormalColor);
            int size = mCurrentPageIndex == i ? mCirclePointSelectedSize : mCirclePointNormalSize;
            int radius = size / 2;
            int cx = point.x - mCirclePointSelectedSize / 2 - getPaddingRight();
            int cy = point.y;
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    public void setPageCount(int count) {
        if (count != mPageCount) {
            mPageCount = count;
        }
        if (getWidth() != 0) {
            updatePoints(getWidth(), getHeight());
        }
    }

    public void setCurrentPage(int page) {
        if (mCurrentPageIndex != page) {
            mCurrentPageIndex = page;
        }
        invalidate();
    }

    public void setCirclePointNormalSize(int size) {
        mCirclePointNormalSize = size;
        invalidate();
    }

    public void setCirclePointSelectedSize(int size) {
        mCirclePointSelectedSize = size;
        invalidate();
    }

    public void setCirclePointNormalColor(int color) {
        mCirclePointNormalColor = color;
        invalidate();
    }

    public void setCirclePointSelectedColor(int color) {
        mCirclePointSelectedColor = color;
        invalidate();
    }

    private void updatePoints(int width, int height) {
        mPointList.clear();
        if (mPageCount == 1) {
            mPointList.add(new Point(width, height / 2));
        } else {
            int radius = width / 2;
            float startAngle = (mPageCount - 1) * GAP_ANGLE / 2;
            for (int i = 0; i < mPageCount; i++) {
                float angle = startAngle - GAP_ANGLE * i;
                float x = (float) (radius * Math.cos(Math.toRadians(angle)));
                float y = (float) (radius * Math.sin(Math.toRadians(angle)));
                Point point = new Point();
                point.set((int) x + radius, radius - (int) y);
                mPointList.add(point);
            }
        }
        invalidate();
    }

    private Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL_AND_STROKE);
        }
        return mPaint;
    }
}

