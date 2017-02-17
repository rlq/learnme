package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author lqren on 17/1/24.
 */
public class HeartRateView extends View {

    // text
    private Paint textPaint = new Paint();

    // --------
    private Paint dashPaint = new Paint();

    private Rect rect = new Rect();

    private int redColor = Color.RED;
    private int whiteColor = Color.WHITE;


    public HeartRateView(Context context) {
        super(context);
        init();
    }

    public HeartRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textPaint.setTextSize(10);
        textPaint.setColor(whiteColor);
        textPaint.setAlpha(128);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);

        dashPaint.setStrokeWidth(2);
        dashPaint.setColor(whiteColor);
        dashPaint.setAlpha(70);
        dashPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[] {4, 4}, 0));
    }

    float left = 10;
    float top = 10;
    float contentHeight = 300;
    float contentWidth = 300;
    int cicrleSize = 4;

    private void drawBottomTextLines(Canvas canvas) {
        int textHeight = 8;
//        textPaint.getTextBounds(min, 0, min.length(), rect);
        canvas.drawLine(left, top + contentHeight - textHeight, left + contentWidth, top + contentHeight, textPaint); //先画一行

        for (int i = 0; i < 4; i++) {
            canvas.drawCircle(left + contentWidth * i / cicrleSize,
                    top + contentHeight - textHeight, 5, textPaint);
            String min = i + "分钟";
            //canvas.drawText(min, left, top + contentHeight - textHeight/2, textPaint);

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        drawBottomTextLines(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }
}
