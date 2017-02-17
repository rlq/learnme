package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/1/6.
 */
public class PercentView0106 extends View {

    private RectF arcRectF = new RectF();
    private Rect bounds = new Rect();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int RADIUS = 20;
    private static final int INTERVAL = 10;

    private int percent = 20;
    private String text = "finish";
    private LinearGradient linearGradient;
    private float x, y;
    private Drawable typeIcon = getResources().getDrawable(R.drawable.qq);

    public PercentView0106(Context context) {
        super(context);
        init();
    }

    public PercentView0106(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentView0106(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        linearGradient = new LinearGradient(getPaddingLeft(), getPaddingTop(),
                getPaddingLeft() + getMeasuredWidth(), getPaddingTop() + getMeasuredHeight(),
                Color.BLUE,
                Color.GREEN,
                Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = 300;
        float height = 60;
        arcRectF.set(0, height / 2, width, height);
        canvas.drawArc(arcRectF, -180, 180, false, mPaint);
        int percentTemp = percent >= 100 ? 100 : percent;
        x = width * percentTemp / 100f;
        y = height - height / 2 * (float) Math.sqrt(1 - Math.pow((0.5 - percentTemp / 100f), 2));

        if (percent >= 100) {
            x = x - RADIUS - INTERVAL / 2;
            y = y + INTERVAL;
        } else if (percent == 0) {
            x = x + RADIUS + INTERVAL / 2;
            y = y + INTERVAL;
        }

        canvas.drawCircle(x, y, RADIUS, mPaint);
        typeIcon.setBounds((int) x - RADIUS, (int) y - RADIUS, (int) x + RADIUS, (int) y + RADIUS);
        typeIcon.draw(canvas);
        canvas.drawText(text, x - bounds.width() / 2, y - RADIUS - INTERVAL, textPaint);

    }
}
