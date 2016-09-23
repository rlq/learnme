package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

/**
 * Author lqren , 16/9/19.
 */
public class DrawPath919 extends View {

    public static final int TYPE_HOUR = 1;
    public static final int TYPE_DAY = 2;
    public static final int TYPE_WEEK = 3;
    public static final int TYPE_MONTH = 4;
    public static final int DISPLAY_NUMBER = 7;

    private List<StepItem> mList;
    private int drawType = TYPE_DAY;
    private SelectListener mSelectListener;
    private Handler mHandler = new MyHandler();

    public float speed = 0f;
    public int duration = 20;
    private int TEXT_MARGIN_TOP = 20;
    private int STEP_MARGIN_BOTTOM = 20;
    private int START_INDEX = -1;
    private int mIndex = START_INDEX;
    private int mSelectIndex = 0;
    private int mXInterval = 100;
    private int mXTimeInterval = 100;
    private int mWidth = 1000;
    private int mHeight = 500;
    private int mTotalHeight = 500;
    private int mStartX = 0;
    private float mGoalDisplayValue = 0;

    private Paint mCurvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGoalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mCurvePath;
    private Bitmap mBitmap;
    private int colorGrey;
    private int colorWhite;
    private int startColor;
    private int endColor;
    private int textSize28;
    private int textSize24;
    private int textSize20;

    private Rect mDrawRect = new Rect();

    public interface SelectListener {
        void onSelect(StepItem item, int drawType);
    }


    public DrawPath919(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private void shift(float distanceX) {
        int lastX = mStartX;
        mStartX = (int) (mStartX - (distanceX));
        int index = START_INDEX - mStartX / mXInterval;
        if (index >= 0 && index <= START_INDEX) {
            if (mStartX < 0) {
                mStartX = 0;
            }
            mIndex = index;
            invalidate();
        } else {
            mStartX = lastX;
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            speed = speed - 0.01f * Math.signum(speed);
            if (Math.abs(speed) > 0.2f) {
                this.sendEmptyMessageDelayed(0, duration);
                shift(speed);
            } else {
                speed = 0f;
            }
        }
    }

    private GestureDetector mGestureDetector = new GestureDetector(getContext(),
            new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    speed = 0;
                    mHandler.removeMessages(0);
                    shift(distanceX);
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    float x = e.getX();
                    int det = mStartX % mXInterval;
                    int i = 0;
                    if (x > det) {
                        i = (int) ((x - det) / mXInterval) + 1;
                    }
                    mSelectIndex = mIndex + i;
                    if (mSelectIndex >= 0 && mSelectIndex < mList.size()) {
                        StepItem item = mList.get(mSelectIndex);
                        if (mSelectListener != null) {
                            mSelectListener.onSelect(item, drawType);
                        }
                    }
                    invalidate();
                    return true;
                }
            });
}
