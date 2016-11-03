package com.lq.ren.many.calendar.transformer1019;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lq.ren.many.R;


/**
 * Author lqren on 16/9/24.
 */
public class His3PageTransformer implements ViewPager.PageTransformer {

    float parallaxCoefficient;
    float distanceCoefficient;

    public His3PageTransformer(float parallaxCoefficient, float distanceCoefficient) {
        this.parallaxCoefficient = parallaxCoefficient;
        this.distanceCoefficient = distanceCoefficient;
    }

    @Override
    public void transformPage(View view, float position) {
        float scrollXOffset = view.getWidth() * parallaxCoefficient;

        // ...
        // layer is the id collection of views in this page
            View v = view.findViewById(R.id.imageView);//这里使用ViewPager
            if (v != null) {
                v.setTranslationX(scrollXOffset * position);
            }
            scrollXOffset *= distanceCoefficient;
    }

    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m);
    }
}
