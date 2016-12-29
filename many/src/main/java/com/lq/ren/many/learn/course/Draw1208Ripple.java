package com.lq.ren.many.learn.course;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.lq.ren.many.R;

import java.util.ArrayList;

/**
 * Author lqren on 16/12/8.
 * 通过属性动画，把多个圆依次进行缩放，并且在缩放的过程中对透明度进行改变。
 */
public class Draw1208Ripple extends RelativeLayout {

    private static final int RIPPLE_COUNT = 2;//水波的数量
    private static final int DURATION = 3000;//水波动画持续时间
    private Paint paint = new Paint();//水波的画笔
    private boolean animationRunning = false;
    private AnimatorSet animatorSet;
    private ArrayList<Animator> animatorList;
    private RelativeLayout.LayoutParams rippleParams;
    private ArrayList<RippleView> rippleViewList = new ArrayList<>();

    public Draw1208Ripple(Context context) {
        super(context);
        init();
    }

    public Draw1208Ripple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Draw1208Ripple(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        VectorDrawable vectorDrawable = (VectorDrawable) getResources().getDrawable(R.drawable.noti_hide_1208);
        VectorDrawable vectorDrawable1 = (VectorDrawable) getResources().getDrawable(R.drawable.noti_3gsignal_1208);
        VectorDrawable vectorDrawable2 = (VectorDrawable) getResources().getDrawable(R.drawable.ic_add_normal_1212);
        VectorDrawable vectorDrawable3 = (VectorDrawable) getResources().getDrawable(R.drawable.ic_add_update_1212);

        int radius = 60;//水波的半径
        rippleParams = new RelativeLayout.LayoutParams(2 * radius, 2 * radius);//水波初始占用区域的大小
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);//水波居中显示
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        LinearGradient shader = new LinearGradient(radius, 0, radius, 2 * radius, 0x80D656FF, 0x8000ACD0,
                Shader.TileMode.REPEAT);
        paint.setShader(shader);//设置颜色渲染
        int rippleDelay = DURATION / RIPPLE_COUNT;//设置每个水波的延迟时间
        //水波的显示动画
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(DURATION);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorList = new ArrayList<>();
        for (int i = 0; i < RIPPLE_COUNT; i++) {
            RippleView rippleView = new RippleView(getContext());
            addView(rippleView, rippleParams);
            rippleViewList.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, 6.0f);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限次数
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);//每个水波的延迟时间
            animatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, 6.0f);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(scaleYAnimator);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 0.5f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(alphaAnimator);
        }
        animatorSet.playTogether(animatorList);

        startRippleAnimation();
    }

    public void startRippleAnimation() {
        if (!isRippleAnimationRunning()) {
            for (RippleView rippleView : rippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }
    }

    public void stopRippleAnimation() {
        if (isRippleAnimationRunning()) {
            animatorSet.end();
            animationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return animationRunning;
    }


    /**
     * 水波类
     */
    private class RippleView extends View {
        public RippleView(Context context) {
            super(context);
            this.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius = getWidth() / 2;
            canvas.drawCircle(radius, radius, radius, paint);
        }
    }
}
