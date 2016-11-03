package com.lq.ren.many.calendar.transformer1019;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Author lqren on 16/9/24. 很多美女图
 */
public class HisPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private ViewPager viewPager;
    private int maxTranslateOffsetX;

    public HisPageTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 180);
    }

    @Override
    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }
        int leftInScreen = view.getLeft() - viewPager.getScrollX();
        int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
        int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
        float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-maxTranslateOffsetX * offsetRate);
        }
        if (position < -1) {
            view.setAlpha(0);
        }
    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m);
    }
}
