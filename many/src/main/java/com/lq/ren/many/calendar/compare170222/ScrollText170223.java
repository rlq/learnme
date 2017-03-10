package com.lq.ren.many.calendar.compare170222;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lq.ren.many.R;

/**
 * Author lqren on 17/2/23.
 */
public class ScrollText170223 extends RelativeLayout {
    public ScrollText170223(Context context) {
        super(context);
        init();
    }

    public ScrollText170223(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollText170223(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.custom_170223_scrolltext, this, true);
        ((TextView) findViewById(R.id.scolltext)).setMovementMethod(new ScrollingMovementMethod());
    }
}
