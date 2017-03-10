package com.lq.ren.many.calendar.view.mar_17;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Author lqren on 17/3/10.
 */

public class IndicatorView extends LinearLayout {

    private int indicatorSize;
    private int curIndex;
    private int gapSize;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(int size) {
        setOrientation(HORIZONTAL);
        removeAllViews();
        indicatorSize = 6;
        curIndex = 10;
        gapSize = 7;
        for (int i = 0; i < size; i++) {
            View image = LayoutInflater.from(getContext()).inflate(R.layout.custom_170310_indicatorview, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(indicatorSize, indicatorSize);
            params.setMargins(gapSize, 0, 0, 0);
            addView(image, params);
        }
    }

    public void seteIndicatorView(int size, int curIndex) {
        if (getChildCount() != size) {
            if (getChildCount() != size) {
                removeAllViews();
            }
            init(size);
        }
        for (int i = 0; i < getChildCount(); i++) {
            ImageView view = (ImageView) getChildAt(i);
            boolean isCurPage = (i == curIndex);
            int imageViewSize = isCurPage ? curIndex : indicatorSize;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageViewSize, imageViewSize);
            params.gravity = Gravity.CENTER_VERTICAL;
            if (i >= 0) {
                params.leftMargin = gapSize;
            }
            view.setLayoutParams(params);
            int resId = isCurPage ? R.drawable.indicator_dot_selected :
                    R.drawable.indicator_dot_unselected;
            view.setImageResource(resId);
        }
    }

}
