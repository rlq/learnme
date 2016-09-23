package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/13.
 */
public class Draw4Shader extends View{

    private Bitmap mBitQQ = null;
    private int mBitQQWidth = 0;
    private int mBitQQHeight = 0;
    private Paint mPaint = null;
    //bitmap shader
    private Shader mBitmapShader = null;
    private Shader mLinearGradient = null;//线性渐变
    private Shader mComposeShader = null;//混合渐变
    private Shader mRadialGrandient = null;//唤醒渐变
    private Shader mSweepGrandient = null;//梯度
    private ShapeDrawable mShapeDrawableQQ = null;

    public Draw4Shader(Context context) {
        super(context);

        //装载资源  qq图片本来是矩形,先渲染出圆形
        mBitQQ = ((BitmapDrawable)getResources().getDrawable(R.drawable.qq)).getBitmap();
        mBitQQWidth = mBitQQ.getWidth();
        mBitQQHeight = mBitQQ.getHeight();
        //
        mBitmapShader = new BitmapShader(mBitQQ, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);

//        mLinearGradient = new LinearGradient(0, 0, 100, 100, new int[]{
//                Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
//        }, null, Shader.TileMode.REPEAT);

        mLinearGradient = new LinearGradient(0, 0, 0, 200, Color.RED,
                Color.WHITE, Shader.TileMode.MIRROR);

        //compose 混合渲染模式有很多种
        mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.DARKEN);

        //RadialGradient 设置半径属性,
        mRadialGrandient = new RadialGradient(50, 200, 50, new int[]{
          Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
        }, null, Shader.TileMode.REPEAT);


        mSweepGrandient = new SweepGradient(30, 30, new int[]{
           Color.GREEN, Color.RED, Color.BLUE, Color.WHITE
        }, null);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        //将图片裁剪成椭圆形
        //并将shapeDrawable 创建为椭圆形
        mShapeDrawableQQ = new ShapeDrawable(new OvalShape());
        //set shader
        mShapeDrawableQQ.getPaint().setShader(mBitmapShader);
        //设置显示区域
        mShapeDrawableQQ.setBounds(0, 0, mBitQQWidth, mBitQQHeight);
        //draw
        mShapeDrawableQQ.draw(canvas);

        //linear
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(mBitQQWidth, 0 , 320, 156, mPaint);



        //circle
        mPaint.setShader(mRadialGrandient);
        canvas.drawCircle(50, 200, 50, mPaint);

        //sweep
        mPaint.setShader(mSweepGrandient);
        canvas.drawRect(150, 160, 300, 300, mPaint);

        //compose
        mPaint.setShader(mComposeShader);
        canvas.drawRect(0, 300, mBitQQHeight, 300 + mBitQQHeight, mPaint);

        drawHourLine(canvas);
        drawBox(canvas);
        drawCurves(canvas);
    }

    /** 9.18 健康中心 */
    private Paint textPaint = new Paint();

    private void drawBox(Canvas canvas) {
        Path path = new Path();
        //int det =
        textPaint.setColor(Color.RED);
        path.addRect(0, 100, 200, 400, Path.Direction.CCW);
        canvas.drawPath(path, textPaint);
    }

    private void drawCurves(Canvas canvas) {
        int interval = canvas.getWidth() / 7;
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        /**折线 */
        path.lineTo(100,100);
        path.lineTo(200, 250);
        path.lineTo(350, 450);


        path.moveTo(100, 400);
        path.cubicTo(0, 100, 200, 400, 500, 800);
        path.cubicTo(500, 800, 0, 100, 200, 400);
        path.cubicTo(0, 100, 200, 200, 400, 400);

        path.lineTo(-interval, 400);

//        Rect rect = new Rect(100, 100, 300, 250);
//        canvas.drawRect(rect, textPaint);
//        canvas.drawOval(new RectF(rect), paint);
//        canvas.drawLine(50, 175, 350, 175, textPaint);
//        canvas.drawLine(200, 50, 200, 300, textPaint);
//        path.reset();
//        path.addArc(new RectF(rect), 30, 60);

        Rect rect = new Rect(100, 100, 300, 250);
        canvas.drawRect(rect, textPaint);
        canvas.drawLine(50, 175, 350, 175, textPaint);
        canvas.drawLine(200, 50, 200, 300, textPaint);

        path.moveTo(0, 0);
        path.arcTo(new RectF(rect), 90, 145);
        path.addArc(new RectF(rect), 0, 60);

        canvas.drawPath(path, textPaint);
        path.addPath(path, 500, 0);
        path.cubicTo(100, 400, 300, 100, 400, 400);
        canvas.drawPath(path, textPaint);

    }

    /** draw 24 interval  */
    private void drawHourLine(Canvas canvas) {
        int interval = canvas.getWidth() / 24;
        canvas.drawLine(0, 10, canvas.getWidth(), 10, mPaint);
        mPaint.setColor(Color.BLUE);
        for (int i = 0; i < 24; i++) {
            int x = i * interval + interval / 2;
            canvas.drawLine(x, 10, x, 400, mPaint);
            if (i == 6 || i == 12 || i == 18) {
                textPaint.setColor(Color.RED);
                textPaint.setTextSize(30);
                String hourStr = i + ":00";
                int xPos = x - getStringLength(hourStr);
                int yPos = 440;
                canvas.drawText(hourStr, xPos, yPos, textPaint);
            }
            textPaint.setColor(Color.YELLOW);
            Rect rect = new Rect(x - interval / 4, 400, x + interval / 4, 420);
            canvas.drawRect(rect, textPaint);
        }
    }

    private int getStringLength(String text) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

}
