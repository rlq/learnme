package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by lqren on 16/8/13.
 */
public class Draw2Canvas extends View implements Runnable{

    private Paint mPaint ;

    public Draw2Canvas(Context context) {
        super(context);
        mPaint = new Paint();
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        //clip filed
        canvas.clipRect(100, 100, 380, 360);

        //锁定画布
        canvas.save();
        //旋转画布 只会对解除之前的draw做处理
        canvas.rotate(45.0f);

        mPaint.setColor(Color.RED);
        canvas.drawRect(new Rect(15 , 15 , 340, 170), mPaint);

        //解除画布的锁定
        canvas.restore();

        mPaint.setColor(Color.GREEN);
        canvas.drawRect(new Rect(150, 75, 260, 120), mPaint);

    }

    @Override
    public void run() {

    }
}
