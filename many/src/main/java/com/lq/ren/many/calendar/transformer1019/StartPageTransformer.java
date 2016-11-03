package com.lq.ren.many.calendar.transformer1019;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lq.ren.many.R;


/**
 * Author lqren on 16/9/24.
 */
public class StartPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.75f;
    private ViewPager viewPager;
    private int maxTranslateOffsetX;

    public StartPageTransformer(Context context) {
        this.maxTranslateOffsetX = dp2px(context, 180);
    }

    @Override
    public void transformPage(View view, float position) {
        if (viewPager == null) {
            viewPager = (ViewPager) view.getParent();
        }
        int topInScreen = view.getTop() - viewPager.getScrollY();
        int centerYInViewPager = topInScreen + view.getMeasuredHeight() / 2;
        int offsetY = centerYInViewPager - viewPager.getMeasuredHeight() / 2;
        float offsetRate = (float) offsetY * 0.38f / viewPager.getMeasuredHeight();
        float scaleFactor = 1 - Math.abs(offsetRate);
        if (scaleFactor > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationY(-maxTranslateOffsetX * offsetRate);
        }
//        LogUtil.d("HEHE", "pos: " + position );//+ ", scaleFactor: " + scaleFactor + ", offsetY:" +offsetY);
        View v = view.findViewById(R.id.vf_flipper);//这里使用background
        if ((position > -0.5 && position <= 0.5)
                //|| (position > 0 && position <= 0.6)
                || (position >= 1 && position <= 1.5)) {
            v.setAlpha(1f);
        } else {
            v.setAlpha(0f);
        }

    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m);
    }
    /**
     * int leftInScreen = view.getLeft() - viewPager.getScrollX();
     int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
     int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
     float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
     float scaleFactor = 1 - Math.abs(offsetRate);
     if (scaleFactor > 0) {
     view.setScaleX(scaleFactor);
     view.setScaleY(scaleFactor);
     view.setTranslationX(-maxTranslateOffsetX * offsetRate);
     }
     */
}
