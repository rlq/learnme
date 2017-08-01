package com.lq.ren.many.calendar.view.mar_17;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageButton;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/3/23.
 */

public class FloatingButton extends ImageButton {

    Drawable rippleDrawable;

    public FloatingButton(Context context) {
        this(context, null);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FloatingButton, defStyleAttr, defStyleAttr);
        int rippleColor = a.getColor(R.styleable.FloatingButton_tic_rippleColor, 0);
        a.recycle();

        ShapeDrawable shape = new ShapeDrawable();
        shape.setShape(new OvalShape());
        shape.getPaint().setColor(Color.WHITE);

         rippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor),
                shape, null);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundDrawable(rippleDrawable);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(null);
                break;
        }
        return super.onTouchEvent(event);
    }

}
