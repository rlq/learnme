package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/2/6.
 */
public class WorkingSlideView0206 extends RelativeLayout {

    public static final int ORIENTATION_LEFTRIGHT = 2;

    private View map;
    private View pause;
    private View action;

    private int touchSlop = 100;
    private float downX = 0;
    private float downY = 0;
    private int orientationId = 0;
    private Scroller mScroller;


    public WorkingSlideView0206(Context context) {
        super(context);
        init();
    }

    public WorkingSlideView0206(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkingSlideView0206(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_0206_slide, this, true);

        map = findViewById(R.id.map);
        pause = findViewById(R.id.pause);
        action = findViewById(R.id.action);

        pause.setVisibility(VISIBLE);
        map.setVisibility(VISIBLE);

        pause.setTranslationX(-800);//right show
        map.setTranslationX(800);
    }

    boolean showPause = false;
    boolean showMap = false;

    float moveLastX = 0;
    float moveLastY = 0;


}
