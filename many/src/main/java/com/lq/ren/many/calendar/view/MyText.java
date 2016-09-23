package com.lq.ren.many.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lqren on 16/8/2.
 */
public class MyText extends TextView{
    public MyText(Context context) {
        this(context, null);
    }

    public MyText(Context context, AttributeSet attr){
        this(context, attr, 0);
    }

    public MyText(Context context, AttributeSet attr, int styleTheme){
        super(context, attr, styleTheme);

    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);

    }
}
