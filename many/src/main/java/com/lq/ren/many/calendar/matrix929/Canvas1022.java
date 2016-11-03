package com.lq.ren.many.calendar.matrix929;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author lqren on 16/10/22.
 */
public class Canvas1022 extends View {

    private Paint mPaint;
    private int viewWidth = 0;
    private int viewHeight = 0;

    public Canvas1022(Context context) {
        super(context);
        init();
    }

    public Canvas1022(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Canvas1022(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(100, 100, 400,400, mPaint);
        mPaint.setColor(Color.RED);
        canvas.translate(-200, -200);
        canvas.drawRect(viewWidth / 2, viewHeight / 2, viewWidth / 2 + 20, viewHeight/ 2 + 20, mPaint);
        canvas.drawRect(viewWidth , viewHeight , viewWidth  + 20, viewHeight+ 20, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec) - 20;
        viewHeight = MeasureSpec.getSize(heightMeasureSpec) - 20;
    }
}
