package com.lq.ren.many.calendar.transformer1019;

import android.view.View;

public class CubeOutTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotY(position < 0f ? view.getWidth() : 0f);
        view.setPivotX(view.getHeight() * 0.5f);
        view.setRotationX(90f * position);
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
