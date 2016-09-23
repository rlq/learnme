package com.lq.ren.many.calendar.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lq.ren.many.R;



/**
 * Created by lqren on 16/8/11.
 */
public class CombintionView extends FrameLayout {

    TextView mTitleTv;
    TextView mBackButton;

    public CombintionView(Context context) {
        this(context, null);
    }

    public CombintionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombintionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(
                R.layout.custom_combinationview, this);

        mTitleTv = (TextView) findViewById(R.id.title_text);
        mBackButton = (TextView) findViewById(R.id.button_left);
        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)getContext()).finish();
            }
        });

    }

    public void setTitleText(String str){
        mTitleTv.setText(str);
    }

    public void setBackButtonText(String str){
        mBackButton.setText(str);
    }

    public void setBackButtonListener(OnClickListener listener){
        mBackButton.setOnClickListener(listener);
    }


}
