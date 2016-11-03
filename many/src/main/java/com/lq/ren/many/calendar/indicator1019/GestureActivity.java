package com.lq.ren.many.calendar.indicator1019;


import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

import com.lq.ren.many.R;

/**
 * Author lqren on 16/10/16.
 */
public class GestureActivity extends Activity implements GestureDetector.OnGestureListener {

    ViewFlipper flipper;//一次显示一个子view
    GestureDetector detector; //手势监视器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detector = new GestureDetector(this);
        setContentView(R.layout.custom_flipper1016);
        flipper = (ViewFlipper) findViewById(R.id.vf_flipper);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getX() - e2.getX() > 120) {//向左滑，右边显示
            Log.e("HEHE", "左滑");
            //this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
        }
        if (e1.getX() - e2.getX() < -120) {//向右滑，左边显示
            Log.e("HEHE", "右滑");
            this.flipper.showPrevious();
        }
        return false;
    }

}
