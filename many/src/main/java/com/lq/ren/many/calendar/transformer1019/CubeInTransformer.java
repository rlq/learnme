package com.lq.ren.many.calendar.transformer1019;

import android.view.View;

public class CubeInTransformer extends BaseTransformer {

    private boolean mOnLoadAnimation;

    public CubeInTransformer(boolean animation) {
        mOnLoadAnimation = animation;
    }

    public void setOnLoadAnimation(boolean animation) {
        mOnLoadAnimation = animation;
    }

    @Override
    protected void onTransform(View view, float position) {

        if (mOnLoadAnimation && position < 0f) {
            view.setAlpha(0f);
            return;
        }
        view.setPivotY(position > 0 ? -view.getHeight() / 6 : view.getHeight() * 1.5f);
        view.setPivotX(view.getWidth() / 2);
        view.setRotationX(-90f * position);
        view.setAlpha(1 - Math.abs(position));
    }

    @Override
    protected void onPostTransform(View page, float position) {
        super.onPostTransform(page, position);

    }


    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
