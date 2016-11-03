package com.lq.ren.many.calendar.transformer1019;

import android.support.v4.view.ViewPager;
import android.view.View;

//旋转
public class FlipPageViewTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        float percentage = 1 - Math.abs(position);
        page.setCameraDistance(12000);
        setVisibility(page, position);
        setTranslation(page);
        setSize(page, position, percentage);
        setRotation(page, position, percentage);
    }

    private void setVisibility(View page, float position) {
        if (position < 0.5 && position > -0.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void setTranslation(View page) {
        ViewPager viewPager = (ViewPager) page.getParent();
        int scroll = viewPager.getScrollX() - page.getLeft();
        page.setTranslationX(scroll);
    }

    private void setSize(View page, float position, float percentage) {
        page.setScaleY((position != 0 && position != 1) ? percentage : 1);
        page.setScaleX((position != 0 && position != 1) ? percentage : 1);
    }

    private void setRotation(View page, float position, float percentage) {
        if (position > 0) {
            page.setRotationX(-180 * (percentage + 1));
        } else {
            page.setRotationX(180 * (percentage + 1));
        }
    }
}
