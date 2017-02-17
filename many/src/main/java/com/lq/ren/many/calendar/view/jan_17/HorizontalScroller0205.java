package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * Author lqren on 17/2/5.
 */
public class HorizontalScroller0205 extends RelativeLayout {

    private int touchSlop;

    private float lastXIntercept = 0;
    private float lastYIntercept = 0;

    private float lastX = 0;
    private float lastY = 0;

    private int leftBorder;
    private int rightBorder;


    public HorizontalScroller0205(Context context) {
        super(context);
        init();
    }

    public HorizontalScroller0205(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScroller0205(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float xIntercept = ev.getX();
        float yIntercept = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xIntercept - lastXIntercept;
                float deltaY = yIntercept - lastYIntercept;
                //当水平方向的滑动距离 > 竖直方向的滑动距离, 且 手指拖动值 > touchSlop,则拦截事件
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > touchSlop) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }

        lastX = xIntercept;
        lastY = yIntercept;
        lastXIntercept = xIntercept;
        lastYIntercept = yIntercept;

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xTouch - lastX;
                float deltaY = yTouch - lastY;
                float scrollByStart = deltaX;
                if (getScrollX() - deltaX < leftBorder) {
                    scrollByStart = getScrollX() - leftBorder;
                } else if (getScrollX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart = rightBorder - getWidth() - getScrollX();
                }
                scrollBy((int) -scrollByStart, 0);
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时,根据当前的滚动值来判定应该滚动到那个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();
                scrollTo(getScrollX() + dx, 0);
                break;
        }
        lastX = xTouch;
        lastY = yTouch;
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                childView.layout(i * getMeasuredWidth(), 0, i * getMeasuredHeight() + childView.getMeasuredWidth()
                        + getPaddingLeft(), childView.getMeasuredHeight());
            }
            leftBorder = 0;
            rightBorder = getChildCount() * getMeasuredWidth();
        }
        super.onLayout(changed, l, t, r, b);
    }
}
