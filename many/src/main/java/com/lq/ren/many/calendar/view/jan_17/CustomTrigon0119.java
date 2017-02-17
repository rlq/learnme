package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author lqren on 17/1/19.
 */
public class CustomTrigon0119 extends View {

    private Paint paint = new Paint();

    //控制好圆心,绘制三角形
    private Path path = new Path();
    private float triangleBottom = 50;
    //对应H点
    private Point topPointH = new Point(200, 600);
    private Point topPointH2 = new Point(400, 400);
    private Point pointO;


    public CustomTrigon0119(Context context) {
        super(context);
        init();
    }

    public CustomTrigon0119(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTrigon0119(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(30f);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        generatePath();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // canvas.drawPath(path, paint); //三角形应该只看点尖儿


        canvas.drawCircle(getWidth() / 2, getHeight() / 4, 200, paint);

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 4, 210, paint);

        canvas.drawPoint(topPointH.x, topPointH.y, paint);

        canvas.drawPath(path, paint);

    }

    //下面公式通过圆心点坐标和柱状体顶部中心点坐标，通过二元一次方程组计算出其余两个三角形的坐标点位置
    // x= H.x - triangleBottom * sin{ arctan[(O.y-H.y) / (O.x-H.x)]}
    // y= H.y + triangleBottom * cos{ arctan[(O.y-H.y) / (O.x-H.x)]]}

    private void generatePath() {
        pointO = new Point(getWidth() / 2, getHeight() / 4);
        float tan = (pointO.y - topPointH.y) / (pointO.x - topPointH.x);

        double x1 = topPointH.x - triangleBottom * Math.sin(Math.atan(tan));
        double y1 = topPointH.x + triangleBottom * Math.cos(Math.atan(tan));

        double x2 = topPointH.x - triangleBottom * Math.sin(Math.atan(tan));
        double y2 = topPointH.x - triangleBottom * Math.cos(Math.atan(tan));


        path.moveTo((float) x1, (float) y1);//p
        path.lineTo(pointO.x, pointO.y);// O点
        path.lineTo((float) x2, (float) y2);//p'
        path.close();
    }
}
