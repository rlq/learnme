package com.lq.ren.many.calendar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/11.
 * View有了三个构造方法需要我们重写，这里介绍下三个方法会被调用的场景，

 第一个方法，一般我们这样使用时会被调用，View view = new View(context);
 第二个方法，当我们在xml布局文件中使用View时，会在inflate布局时被调用，
 <View layout_width="match_parent" layout_height="match_parent"/>。
 第三个方法，跟第二种类似，但是增加style属性设置，这时inflater布局时会调用第三个构造方法。
 <View style="@styles/MyCustomStyle" layout_width="match_parent"
 layout_height="match_parent"/>
 */
public class RainBarView extends View{

    //progress bar color
    int barColor = Color.parseColor("#1E88E5");
    //every bar segment width
    int hSpace = 80;
    //every bar segment height
    int vSpace = 4;
    //space among bars
    int space = 10;
    float startX = 0;
    float delta =  5f;
    Paint mPaint;

    public RainBarView(Context context) {
        this(context, null);
    }

    public RainBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RainBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //read custom attrs
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.rainbowbar, 0, 0);
        hSpace = typedArray.getDimensionPixelSize(R.styleable.rainbowbar_rainbowbar_hspace, hSpace);
        vSpace = typedArray.getDimensionPixelOffset(R.styleable.rainbowbar_rainbowbar_vspace, vSpace);
        barColor = typedArray.getColor(R.styleable.rainbowbar_rainbowbar_color, barColor);
        typedArray.recycle();   // we should always recycle after used

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(barColor);
        mPaint.setStrokeWidth(vSpace);
    }

    /**
     * 其实就是调用canvas的drawLine方法，然后每次将draw的起点向前推进，
     * 在方法的结尾，我们调用了invalidate方法， ，
     * invalidate View重新调用一次draw过程
     * 这个方法会让View重新调用onDraw方法，所以就达到我们的进度条一直在向前绘制的效果。
     */
    //draw be invoke numbers.
    int index = 0;
    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //get screen width
        float sw = this.getMeasuredWidth();
        //canvas.drawLine(0, 5, 100 , 5, mPaint);
        if (startX >= sw + (hSpace + space) - (sw % (hSpace + space))) {
            startX = 0;
        } else {
            startX += delta;
        }
        float start = startX;
        // draw latter parse
        while (start < sw) {
            canvas.drawLine(start + 50 , 5, start + hSpace, 5, mPaint);
            start += (hSpace + space);
        }

        start = startX - space - hSpace;

        // draw front parse
        while (start >= -hSpace) {
            canvas.drawLine(start+ 50, 5, start + hSpace, 5, mPaint);
            start -= (hSpace + space);
        }
        if (index >= 700000) {
            index = 0;
        }
        invalidate();
    }*/

    /************************** 点击按钮 ********************/

    /**
     * 最主要的逻辑当然就是写在onDraw()方法里的了,
     * 调用Canvas的drawRect()方法绘制了一个矩形，
     * 这个矩形也就可以当作是CounterView的背景图吧。
     * 接着将画笔设置为黄色，准备在背景上面绘制当前的计数，
     * 注意这里先是调用了getTextBounds()方法来获取到文字的宽度和高度，
     * 然后调用了drawText()方法去进行绘制就可以了。
     */
    private Rect mRect = new Rect();
    private int mCount = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth() /2  , getHeight()/2  , mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(100);

        String text = String.valueOf(mCount);
        mPaint.getTextBounds(text, 0, text.length() , mRect);
        float textWidth = mRect.width();
        float textHeight = mRect.height();
        canvas.drawText(text,  (getWidth() + textWidth ) /  2  ,
                getHeight() /2, mPaint);
    }




}
