package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/2/6.
 */
public class WorkingActionView extends RelativeLayout {

    public static final int ORIENTATION_LEFTRIGHT = 2;

    private View map;
//    private View pause;
    private WorkingSlide pause;
    private View action;

    private int touchSlop = 100;
    private float downX = 0;
    private float downY = 0;
    private int orientationId = 0;
    private Scroller mScroller;


    public WorkingActionView(Context context) {
        super(context);
        init();
    }

    public WorkingActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorkingActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_0206_work_action, this, true);

        map = findViewById(R.id.map);
//        pause = findViewById(R.id.pause);
        pause = (WorkingSlide) findViewById(R.id.pause);
        action = findViewById(R.id.action);

        pause.setVisibility(VISIBLE);
        map.setVisibility(VISIBLE);

        pause.setTranslationX(-1000);//right show
        map.setTranslationX(1000);
    }

    boolean showPause = false;
    boolean showMap = false;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float curX = event.getX();
        final float curY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = curX;
                downY = curY;
                break;
            case MotionEvent.ACTION_UP:
                if (curX == downX) {
                    Log.i("HEHE", "up map pasue invisible");
                    pause.setVisibility(INVISIBLE);
                    map.setVisibility(INVISIBLE);
                }

                if (showMap) {
                    Log.e("HEHE", "up showMap");
                    map.setTranslationX(0);
                    map.setVisibility(VISIBLE);
                } else {
                    Log.e("HEHE", "up !showMap");
                    map.setTranslationX(1000);
                    map.setVisibility(INVISIBLE);
                }

                if (showPause) {
                    Log.i("HEHE", "up showPause");
                    pause.setTranslationX(0);
                    pause.setVisibility(VISIBLE);
                } else {
                    Log.i("HEHE", "up !showPause");
                    pause.setTranslationX(-1000);
                    pause.setVisibility(INVISIBLE);
                }
                orientationId = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (orientationId == 0) {
                    if (Math.abs(curX - downX) > Math.abs(curY - downY)
                            && Math.abs(curX - downX) > touchSlop) {
                        if (pause.getVisibility() == VISIBLE) {
                            map.setVisibility(INVISIBLE);
                            showMap = false;
                            if (curX - downX >= touchSlop) {//right pause show,
                                Log.e("HEHE", "up pause visible");
                                pause.setTranslationX(-1000 + curX);
                                showPause = true;
                            } else {
                                Log.e("HEHE", "up pause invisible");
                                pause.setTranslationX(-1000 + curX);
                                showPause = false;
                            }
                        } else if (map.getVisibility() == VISIBLE) {
                            pause.setVisibility(INVISIBLE);
                            showPause = false;
                            if (curX - downX < touchSlop) {//left show map
                                Log.i("HEHE", "up map visible");
                                map.setTranslationX(curX);
                                showMap = true;
                            } else {
                                Log.i("HEHE", "up map invisible");
                                map.setTranslationX(curX);
                                showMap = false;
                            }
                        } else {
                            if (curX - downX >= touchSlop) {
                                Log.w("HEHE", "up pause visible");
                                pause.setVisibility(VISIBLE);
                                pause.setTranslationX( -1000 + curX);
                                showMap = true;
                            } else {
                                map.setVisibility(VISIBLE);
                                Log.w("HEHE", "up map invisible");
                                map.setTranslationX(curX);
                                showMap = false;
                            }
                        }

                    }
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
