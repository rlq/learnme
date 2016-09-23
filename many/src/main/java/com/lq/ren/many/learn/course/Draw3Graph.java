package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by lqren on 16/8/13.
 */
public class Draw3Graph extends View {

    private Paint mPaint;
    public Draw3Graph(Context context) {
        super(context);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);{//空/实心几何图形

            Rect rect = new Rect();
            rect.left = 15;
            rect.top = 5;
            rect.bottom = 25;
            rect.right = 45;
            mPaint.setColor(Color.BLUE);
            canvas.drawRect(rect, mPaint);

            mPaint.setColor(Color.YELLOW);
            canvas.drawCircle(40, 70, 30, mPaint);//圆心x 圆心y 半径r p

            //椭圆
            RectF rectf = new RectF();
            rectf.left = 80;
            rectf.top = 30;
            rectf.right = 120;
            rectf.bottom = 70;
            mPaint.setColor(Color.LTGRAY);
            canvas.drawOval(rectf, mPaint);

            //多边形
            Path path = new Path();
            path.moveTo(150 + 5, 80 - 50);
            path.lineTo(150 + 45, 80 - 50);
            path.lineTo(150 + 30, 120 - 50);
            path.lineTo(150 + 20, 120 - 50);
            path.close();//使这些点构成一个封闭的多边形
            mPaint.setColor(Color.GRAY);
            canvas.drawPath(path, mPaint);

            //直线
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(5);
            canvas.drawLine(5, 110, 315, 110, mPaint);

            mPaint.setColor(Color.YELLOW);
            for(int i = 0; i < 4; i++){
                Point p = new Point(8, 10);
                int x = p.x + 100 ;
                if(i==1 || i==2){
                    x += 20;
                }
                int y = p.y + 120 + i*50;
                canvas.drawCircle(x, y, 20, mPaint);
            }

        }
    }
}
