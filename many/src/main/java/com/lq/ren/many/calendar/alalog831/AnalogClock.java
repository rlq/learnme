package com.lq.ren.many.calendar.alalog831;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;


import com.lq.ren.many.R;

import java.sql.Time;

/**
 * Created by lqren on 16/8/31.
 */
public class AnalogClock extends View {

    /** record current time*/
    private Time mCalendar;

    /** store 3 png */
    private Drawable mHourHand;
    private Drawable mMinuteHand;
    private Drawable mDial;

    /** dial plate width height, onMeasure use */
    private int mDialWidth;
    private int mDialHeight;

    /** record Whether Window attached View, listen time changed to draw */
    private boolean mAttached;

    /** time*/
    private float mMinutes;
    private float mHour;

    /** track view self size to draw*/
    private boolean mChanged;

    public AnalogClock(Context context) {
        this(context, null);
    }

    public AnalogClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Resources resources = context.getResources();
        if (mDial == null) {
            mDial = resources.getDrawable(R.drawable.clock_dial);
        }
        if (mHourHand == null) {
            mHourHand = resources.getDrawable(R.drawable.clock_hand_hour);
        }
        if (mMinuteHand == null) {
            mMinuteHand = resources.getDrawable(R.drawable.clock_hand_minute);
        }

        mCalendar = new Time(0);

        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
    }

    //Api > 18
    /**
     * 请注意，以上为自定义View设置的构造方法是适用性最广的一种写法，
     * 这样写，可以确保我们的自定义View能够被最大多数的开发者使用，是一种最佳实践。
     */
//    public AnalogClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    /**
     * 我们的View想要的尺寸当然就是与表盘一样大的尺寸，这样可以保证我们的View有最佳的展示，
     * 可是如果ViewGroup给的尺寸比较小，我们就根据表盘图片的尺寸，进行适当的按比例缩放。
     * 注意，这里我们没有直接使用ViewGroup给我们的较小的尺寸，
     * 而是对我们的表盘图片的宽高进行相同比例的缩放后，设置的尺寸，
     * 这样的好处是，可以防止表盘图片绘制时的拉伸或者挤压变形。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float hScale = 1.0f;
        float vScale = 1.0f;

        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {
            hScale = (float) widthSize / mDialWidth ;
        }
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {
            vScale = (float) heightSize / mDialHeight;
        }
        float scale = Math.min(hScale, vScale);

        setMeasuredDimension(
                resolveSizeAndState((int) (mDialWidth * scale), widthMeasureSpec, 0),
                resolveSizeAndState((int) (mDialHeight * scale), heightMeasureSpec, 0));
    }

    /**
     * 确定了大小，是不是就可以绘制了，先不着急，我们先要处理两件事，
     * 一件就是让我们的自定义View能够感知自己尺寸的变化，这样每次绘制时，
     * 可以先判断下尺寸是否发生了变化，如果有变化，就及时调整我们的绘制策略。
     * mChanged 会在onDraw中使用
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChanged = true;
    }

    /**
     * 第二件事就是让我们的View能够监听时间变化，
     * 并及时更新该View中的mCalendar变量，然后根据它来更新自身的绘制。
     */
    private void onTimeChanged() {
        //mCalendar.set

        int hour = mCalendar.getHours();
        int minute = mCalendar.getMinutes();
        int second = mCalendar.getSeconds();
        /**
         * 这里我们为什么不直接getMinutes 赋值给 mMinutes,而是要加上 second/60.0f,这个值不应该是0?
         * 这里有涉及到一个Calendar的一个知识点,也就是他可以是Linient模式
         * 此模式下,second 和 minute 是可能超过60 和 24的,可以看google文档
         */
        mMinutes = minute + second / 60.0f;
        mHour = hour + mMinutes / 60.0f;
        mChanged = true;
    }

    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * 主要是用来判断时区发生变化时,更新mCalendar的时区,
             * 这样我们自定义的View就可以在全球范围内使用了
             */
            if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
                String tz = intent.getStringExtra("time_zone");
                //mCalendar = new Time(TimeZone.getTimeZone(tz).getID());
            }
            onTimeChanged();
            //重绘
            invalidate();
        }
    };

    /**
     * 现在我们将会动态注册广播接收器
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;
            IntentFilter filter = new IntentFilter();
            //监听3种系统广播
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            getContext().registerReceiver(mIntentReceiver, filter);
        }
        mCalendar = new Time(0);
        onTimeChanged();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            getContext().unregisterReceiver(mIntentReceiver);
            mAttached = false;
        }
    }

    /**
     * Last 万事俱备,我们要开始绘制啦
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**View size changed , changed 记录,
         * at the time, mChanged = false, to after listen view size changed */
         boolean changed = mChanged;
        if (changed) {
            mChanged = false;
        }

        /**这里的availableWidth和availableHeight,每次绘制时是可能变化的，
         * 我们从MChanged = true,得到View size changed,那么需要重新为时针,分针设置bounds
         * 因为我们需要时针和分针始终在View的中心
         */
        int availableWidth = getRight() - getLeft();
        int availableHeight = getBottom() - getTop();

        /**这里的x y就是View的中心点的坐标, 是以View的左上角为0点,右 x,下 y的坐标系计算的
         * 这个坐标系主要用来为View中的每一个Drawable确定位置
         * 就像ViewGroup通过自己左上角为0,为View确定位置一样
         */
        int x = availableWidth / 2;
        int y = availableHeight / 2;

        final Drawable dial = mDial;
        int w = dial.getIntrinsicWidth();
        int h = dial.getIntrinsicHeight();
        boolean scaled = false;

        /**如果可用的width, height < dial patch png 的实际宽高,进行缩放
         * 我们通过坐标系的缩放来实现,这样会影响全局,即表盘,时针,分针都会影响
         */
        if (availableWidth < w || availableHeight < h) {
            scaled = true;
            float scale = Math.min((float) availableWidth / w,
                    (float) availableHeight / h);
            canvas.save();
            canvas.scale(scale, scale, x, y);
        }
        /**View size changed, reset 表盘 bounds.
         * 这个bound相当于是为Drawable在View中确定位置,
         * 只是确定方式更直接,直接在View中绘制一个与Drawable大小一样的矩形
         * Drawable就在这里绘制自己,这样框出的矩形是以(x,y)为中心,size与Drawable一样
         * 不用担心dial patch size 会很大,因为我们已经提前进行缩放了
         */
        if (changed) {
            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
        }
        dial.draw(canvas);


        canvas.save();
        /**
         * 根据小数点,以(x,y)为中心旋转坐标系 时针旋转
         */
        canvas.rotate(mHour / 12.0f * 360.0f, x, y);

        final Drawable hourHand = mHourHand;
        if (changed) {
            resetBounds(hourHand, x, y);
        }
        hourHand.draw(canvas);
        canvas.restore();


        canvas.save();
        /**
         * 分针旋转
         */
        canvas.rotate(mMinutes / 60.f * 360.0f, x, y);
        final  Drawable minuteHand = mMinuteHand;
        if (changed) {
            resetBounds(minuteHand, x, y);
        }
        canvas.restore();


        canvas.save();
        //最后我们把缩放的坐标系复原
        if (scaled) {
            canvas.restore();
        }
    }

    private void resetBounds(Drawable drawable, int x, int y) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        /** 仔细体会这里设置的Bounds，我们所画出的矩形，
        同样是以(x,y)为中心的
        矩形，时针图片放入该矩形后，时针的根部刚好在点(x,y)处，
        因为我们之前做时针图片时，
        已经让图片中的时针根部在图片的中心位置了，
        虽然，看起来浪费了一部分图片空间（就是时针下半部分是空白的），
        但却换来了建模的简单性，还是很值的。*/

        drawable.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));
    }
 }

