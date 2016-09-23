package com.lq.ren.many.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/11.
 */
public class ListDeleteView extends ListView implements View.OnTouchListener{

    private View mDeleteView;
    private ViewGroup mItemLayout;

    private GestureDetector mGestureDetector;

    private OnListDeleteListener mListener;

    private int mSelectedItem;

    private boolean mIsDeleteShown;

    public ListDeleteView(Context context) {
        this(context, null);
    }

    public ListDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("HEHE", "view");
        setOnTouchListener(this);
        mGestureDetector = new GestureDetector(context,gListener);
    }

    GestureDetector.SimpleOnGestureListener gListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent e) {
            if(!mIsDeleteShown){
                mSelectedItem = pointToPosition((int) e.getX(), (int) e.getY());
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(!mIsDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)){
                mDeleteView = LayoutInflater.from(getContext())
                        .inflate(R.layout.custom_listdeleteview, null);
                mDeleteView.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        delete();
                        mListener.onDelete(mSelectedItem);
                    }
                });

                mItemLayout = (ViewGroup) getChildAt(mSelectedItem - getFirstVisiblePosition());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT | RelativeLayout.CENTER_VERTICAL);
                mItemLayout.addView(mDeleteView, params);
                mIsDeleteShown = true;
            }
            return false;
        }
    };

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(mIsDeleteShown){
            delete();
            return false;
        }else{
            return mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    private void delete(){
        mItemLayout.removeView(mDeleteView);
        mDeleteView = null;
        mIsDeleteShown = false;
    }

    public void setOnDeleteListener(OnListDeleteListener listener){
        mListener = listener;
    }


    public interface OnListDeleteListener{
        void onDelete(int index);
    }
}
