package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/2/6.
 */
public class WorkingSlide extends RelativeLayout {

    private Paint paint = new Paint();
    private Scroller scroller;

    public WorkingSlide(Context context) {
        super(context);
        init();
    }

    public WorkingSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkingSlide(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(50f);
        scroller = new Scroller(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_0207_work_pause, this, true);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(Color.BLUE);
//        canvas.drawText("pause view", getWidth() / 2, getHeight() / 2, paint);
//    }

    public void scrollRight(int delta) {
        Log.i("HEHE", "workingSlide right : " + getScrollX());
        scrollBy(-20, 0);
//        scroller.startScroll(getScrollX(), 0, -10, 0, 100);
        invalidate();
    }

    public void scrollLeft(int delta) {
        Log.i("HEHE", "workingSlide left");
        scrollBy(20, 0);
//        scroller.startScroll(getScrollX(), 0, 10, 0, 100);

        invalidate();
    }

    /**
     * 理清一个逻辑
     * right show pause view , call scrollRight; pause 显示完全,则不再调用
     * left close pause view , call scrollLeft; pause 完全不显示,也不再调用
     *
     * up, right & 显示不完全, scrollOrigin,
     * up, left & 显示不完全, scrollFinsih
     *
     * 不存在visible
     */

    public void scrollOrigin() {
        scrollTo(-1000, 0);
    }

    public void scrollFinish() {
        scrollTo(1000, 0);
    }

}
