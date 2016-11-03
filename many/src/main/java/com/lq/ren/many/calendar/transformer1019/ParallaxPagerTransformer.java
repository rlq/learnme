package com.lq.ren.many.calendar.transformer1019;

import android.support.v4.view.ViewPager;
import android.view.View;


/**
 * Author lqren on 16/9/24.
 */
public class ParallaxPagerTransformer implements ViewPager.PageTransformer {

    private int id;
    private int border = 0;
    private float speed = 0.2f;

    public ParallaxPagerTransformer(int id) {
        this.id = id;
    }

    @Override
    public void transformPage(View view, float position) {

        View parallaxView = view.findViewById(id);

        if (parallaxView != null) {
            if (position > -1 && position < 1) {
                float width = parallaxView.getWidth();
                parallaxView.setTranslationX(-(position * width * speed));
                float sc = ((float)view.getWidth() - border)/ view.getWidth();
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else {
                    view.setScaleX(sc);
                    view.setScaleY(sc);
                }
            }
        }
    }
}
