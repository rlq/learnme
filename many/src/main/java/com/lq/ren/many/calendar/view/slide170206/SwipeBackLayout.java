package com.lq.ren.many.calendar.view.slide170206;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.lq.ren.many.R;


public class SwipeBackLayout extends FrameLayout {
    private static final String TAG = SwipeBackLayout.class.getSimpleName();
    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSilding;
    private boolean isFinish;
    private Drawable mShadowDrawable;
    private Activity mActivity;
    private List<ViewPager> mViewPagers = new LinkedList<ViewPager>();

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);

        mShadowDrawable = getResources().getDrawable(R.drawable.shadow_left);
    }


    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.windowBackground });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(View decorChild) {
        mContentView = (View) decorChild.getParent();
    }

    /**
     * 事件拦截操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //处理ViewPager冲突问题
        ViewPager mViewPager = getTouchViewPager(mViewPagers, ev);
        Log.i(TAG, "mViewPager = " + mViewPager);

        if(mViewPager != null && mViewPager.getCurrentItem() != 0){
            return super.onInterceptTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                // 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) ev.getRawY() - downY) < mTouchSlop) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                tempX = moveX;
                if (moveX - downX > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop) {
                    isSilding = true;
                }

                if (moveX - downX >= 0 && isSilding) {
                    mContentView.scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if (mContentView.getScrollX() <= -viewWidth / 2) {
                    isFinish = true;
                    scrollRight();
                } else {
                    scrollOrigin();
                    isFinish = false;
                }
                break;
        }

        return true;
    }

    /**
     * 获取SwipeBackLayout里面的ViewPager的集合
     * @param mViewPagers
     * @param parent
     */
    private void getAlLViewPager(List<ViewPager> mViewPagers, ViewGroup parent){
        int childCount = parent.getChildCount();
        for(int i=0; i<childCount; i++){
            View child = parent.getChildAt(i);
            if(child instanceof ViewPager){
                mViewPagers.add((ViewPager)child);
            }else if(child instanceof ViewGroup){
                getAlLViewPager(mViewPagers, (ViewGroup)child);
            }
        }
    }


    /**
     * 返回我们touch的ViewPager
     * @param mViewPagers
     * @param ev
     * @return
     */
    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers, MotionEvent ev){
        if(mViewPagers == null || mViewPagers.size() == 0){
            return null;
        }
        Rect mRect = new Rect();
        for(ViewPager v : mViewPagers){
            v.getHitRect(mRect);

            if(mRect.contains((int)ev.getX(), (int)ev.getY())){
                return v;
            }
        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            viewWidth = this.getWidth();

            getAlLViewPager(mViewPagers, this);
            Log.i(TAG, "ViewPager size = " + mViewPagers.size());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mShadowDrawable != null && mContentView != null) {

            int left = mContentView.getLeft()
                    - mShadowDrawable.getIntrinsicWidth();
            int right = left + mShadowDrawable.getIntrinsicWidth();
            int top = mContentView.getTop();
            int bottom = mContentView.getBottom();

            mShadowDrawable.setBounds(left, top, right, bottom);
            mShadowDrawable.draw(canvas);
        }

    }


    /**
     * 滚动出界面
     */
    private void scrollRight() {
        final int delta = (viewWidth + mContentView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0,
                Math.abs(delta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished() && isFinish) {
                mActivity.finish();
            }
        }
    }

    /**
    我们在onLayout()方法中利用getParent()方法获取该布局的父布局和获取其控件的宽度，主要是为之后的实现做准备工作。
我们的滑动逻辑主要是利用View的scrollBy() 方法, scrollTo()方法和Scroller类来实现的，当手指拖动视图的时候，
     我们监听手指在屏幕上滑动的距离利用View的scrollBy() 方法使得View随着手指的滑动而滑动，而当手指离开屏幕，
     我们在根据逻辑使用Scroller类startScroll()方法设置滑动的参数，然后再根据View的scrollTo进行滚动。
对于View的滑动，存在一些Touch事件消费的处理等问题，因此我们需要对View的整个Touch事件很熟悉 ,
     最主要的就是Activity里面有一些ListView、 GridView、ScrollView等控件了， 假如我们Activity里面存在ListView、GridView等控件的话，
     我们对Activity的最外层布局进行滚动根本就无效果，因为Touch事件被ListView、GridView等控件消费了，
     所以Activity的最外层布局根本得不到Touch事件，也就实现不了Touch逻辑了，所以为了解决此Touch事件问题我提供了setTouchView(View touchView) 方法，
     这个方法是将Touch事件动态的设置到到View上面，所以针对上面的问题，我们将OnTouchListener直接设置到ListView、GridView上面，
     这样子就避免了Activity的最外层接受不到Touch事件的问题了

接下来看onTouch()方法
首先我们在ACTION_DOWN记录按下点的X,Y坐标
然后在ACTION_MOVE中判断，如果我们在水平方向滑动的距离大于mTouchSlop并且在竖直方向滑动的距离小于mTouchSlop，表示Activity处于滑动状态，
     我们判断如果touchView是ListView、GridView或者其子类的时候，因为我们手指在ListView、GridView上面，伴随着item的点击事件的发生，
     所以我们对touchView设置ACTION_CANCEL来取消item的点击事件，然后对该布局的父布局调用scrollBy()进行滚动，
     并且如果TouchView是AbsListView或者ScrollView直接返回true,来取消AbsListView或者ScrollView本身的ACTION_MOVE事件，
     最直观的感受就是我们在滑动Activity的时候，禁止AbsListView或者ScrollView的上下滑动
最后在ACTION_UP中判断如果手指滑动的距离大于控件长度的二分之一，表示将Activity滑出界面，否则滑动到起始位置，
     我们利用Scroller类的startScroll()方法设置好开始位置，滑动距离和时间，然后调用postInvalidate()刷新界面，
     之后就到computeScroll()方法中，我们利用scrollTo()方法对该布局的父布局进行滚动，滚动结束之后，我们判断界面是否滑出界面，
     如果是就调用OnSildingFinishListener接口的onSildingFinish（）方法，所以只要在onSildingFinish()方法中finish界面就行了
整个滑动布局的代码就是这个样子，接下来我们就来使用了，主界面Activity只有三个按钮，分别跳转到普通布局的Activity,有ListView的Activity和有ScrollView的Activity中
     */

}
