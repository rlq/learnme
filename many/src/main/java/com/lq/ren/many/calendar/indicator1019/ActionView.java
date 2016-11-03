package com.lq.ren.many.calendar.indicator1019;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lq.ren.many.R;

/**
 * Author lqren on 16/10/20.
 */
public class ActionView extends RelativeLayout implements View.OnClickListener {

    private View mMaskLayout;
    private GestureDetector mGestureDetector;

    public ActionView(Context context) {
        super(context);
        init();
    }

    public ActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_flipper1016, this, true);


        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("HEHE", "long");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                super.onFling(e1, e2, velocityX, velocityY);
                Log.e("HEHE", "  onFling e1 : " + e1.getX() + ", e2x:" + e2.getX());
                if (e1.getX() - e2.getX() > 80) {//left
                    Log.e("HEHE", "  > 120");

                }
                if (e1.getX() - e2.getX() < -80) {
                        Log.e("HEHE", " < -120");
                }
                return false;
            }

        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        Log.e("HEHE", "onClick");
    }



}
