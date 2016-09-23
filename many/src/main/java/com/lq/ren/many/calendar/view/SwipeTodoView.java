package com.lq.ren.many.calendar.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/10.
 */
public class SwipeTodoView extends RelativeLayout {
    private static final String TAG = "SwipeTodoView";
    private static final int ANIMATION_TIME = 400;
    private static final float ICON_SCALE_DEFAULT = 1.0f;
    private static final float ICON_SCALE_DOWN = 0.6f;
    private Context mContext;
    private AttributeSet mAttributeSet;
    private float mCenterIvInitX;

    private TextView mContentTv;
    private ImageView mOuterCircleIv;
    private ImageView mMiddleCircleIv;
    private ImageView mInnerCircleIv;
    private ImageView mCenterIv;

    private ImageView mLeftIv;
    private ImageView mRightIv;

    private TextView mSubContentTv;
    private TextView mTipTv;

    private Drawable mLeftBgDrawable;
    private Drawable mRightBgDrawable;

    private ObjectAnimator mIconAnimator;
    private ObjectAnimator mLeftBtnShowAnimator;
    private ObjectAnimator mRightBtnShowAnimator;
    private ObjectAnimator mLeftBtnHideAnimator;
    private ObjectAnimator mRightBtnHideAnimator;

    private AnimatorSet mOuterAnimator = null;
    private AnimatorSet mMiddleAnimator = null;
    private AnimatorSet mInnerAnimator = null;

    private boolean mIsShowLeftButton = false;
    private boolean mIsShowRightButton = false;
    private boolean mHasCenterIcon = false;
    private boolean mIsStopAnimation = false;

    private OnSelectedChangedListener mLeftListener;
    private OnSelectedChangedListener mRightListener;

    private Handler mHandler = new Handler();

    public SwipeTodoView(Context context) {
        this(context, null);
    }

    public SwipeTodoView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.mContext = context;
        inflate(context, R.layout.custom_swipe_todo_view_ticwear, this);
        initView();
        initButtonAnimator();
        initRotationAnimation();
        initAndStartRippleAnimation();
    }

    public void initView(){
        TypedArray typedArray = mContext.obtainStyledAttributes(mAttributeSet,
                R.styleable.SwipeTodoView);
        Drawable centerIconBg = typedArray.getDrawable(R.styleable.SwipeTodoView_tic_centerBtnBg);
        int centerIconResId = typedArray.getResourceId(R.styleable.SwipeTodoView_tic_centerBtnImage, 0);
        int leftIconResId = typedArray.getResourceId(R.styleable.SwipeTodoView_tic_leftBtnImage, 0);
        int rightIconResId = typedArray.getResourceId(R.styleable.SwipeTodoView_tic_rightBtnImage, 0);

        mIsShowLeftButton = (0 != leftIconResId);
        mIsShowRightButton = (0 != rightIconResId);

        ColorStateList defaultColorList = ColorStateList.valueOf(Color.BLUE);
        ColorStateList leftColorStateList = typedArray.getColorStateList(R.styleable.SwipeTodoView_tic_leftBtnColor);
        if(leftColorStateList == null){
            leftColorStateList = defaultColorList;
        }
        ColorStateList rightColorStateList = typedArray.getColorStateList(R.styleable.SwipeTodoView_tic_rightBtnColor);
        if(rightColorStateList == null){
            rightColorStateList = defaultColorList;
        }

        ColorStateList leftBgColor = typedArray.getColorStateList(R.styleable.SwipeTodoView_tic_rightBtnBgColor);
        ColorStateList rightBgColor = typedArray.getColorStateList(R.styleable.SwipeTodoView_tic_rightBtnBgColor);

       // mLeftBgDrawable = new Drawable(Color.WHITE);
//        mLeftBgDrawable.setTintList(leftBgColor);
//        mLeftBgDrawable.

        String content = typedArray.getString(R.styleable.SwipeTodoView_tic_content);
        String subContent = typedArray.getString(R.styleable.SwipeTodoView_tic_subContent);
        typedArray.recycle();

        mOuterCircleIv = (ImageView) findViewById(R.id.outer_circle_iv);
        mMiddleCircleIv = (ImageView) findViewById(R.id.middle_circle_iv);
        mInnerCircleIv = (ImageView) findViewById(R.id.inner_circle_iv);
        mContentTv = (TextView) findViewById(R.id.content_tv);
        mSubContentTv = (TextView) findViewById(R.id.sub_content_tv);
        mTipTv = (TextView) findViewById(R.id.tip_tv);
        mCenterIv = (ImageView) findViewById(R.id.center_iv);
        mLeftIv = (ImageView) findViewById(R.id.left_iv);
        mRightIv = (ImageView) findViewById(R.id.right_iv);

        mContentTv.setText(content);
        mSubContentTv.setText(subContent);
        mCenterIv.setBackground(centerIconBg);
        if(centerIconResId != 0){
            mHasCenterIcon = true;
            mCenterIv.setImageResource(centerIconResId);
            //mCenterIv.getDrawable().getAlpha(255);
        }

        mLeftIv.setImageResource(leftIconResId);
      //  mLeftIv.setImageTintList(leftColorStateList);
        mLeftIv.setBackground(mLeftBgDrawable);
        mRightIv.setImageResource(rightIconResId);
       // mRightIv.setImageTintList(rightColorStateList);
        mRightIv.setBackground(mRightBgDrawable);
    }

    //detached
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        endAnimation();
    }

    /*************************************** Left right Button begin  *************/

    private void initButtonAnimator(){
        mLeftBtnShowAnimator = getButtonShowAnimator(mLeftIv);
        mRightBtnShowAnimator = getButtonShowAnimator(mRightIv);
        mLeftBtnHideAnimator = getButtonHideAnimator(mLeftIv);
        mRightBtnHideAnimator = getButtonHideAnimator(mRightIv);
    }

    private ObjectAnimator getButtonShowAnimator(ImageView iv){
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv,
                PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0, 1.0f));
        animator.setDuration(320);
        return animator;
    }

    private ObjectAnimator getButtonHideAnimator(ImageView iv){
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv,
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.3f),
                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));
        animator.setDuration(320);
        return animator;
    }

    //Left right animator
    private void showButton(){
        if(mIsShowLeftButton){
            mLeftBtnShowAnimator.start();
        }
        if(mIsShowRightButton){
            mRightBtnShowAnimator.start();
        }
    }

    private void hideButton(){
        if(mIsShowLeftButton){
            mLeftBtnHideAnimator.start();
        }
        if(mIsShowRightButton){
            mRightBtnShowAnimator.start();
        }
    }

    /*************************************** Left right Button end  *************/


    private void addTouchListener(){
        //mCenterIv 是alarmSinger 可以拖动
        mCenterIv.setOnTouchListener(new OnTouchListener() {
            private int mRawX;
            private float mDeltaX;
            private float mInitX;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setScaleX(ICON_SCALE_DOWN);
                        v.setScaleY(ICON_SCALE_DOWN);
                        mInitX = v.getX();
                        mCenterIvInitX = mInitX ;
                        mDeltaX = mInitX - event.getRawX();
                        showButton();
                        pauseAnimation();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mRawX = (int) (event.getRawX());
                        if(mRawX < mCenterIv.getWidth() && mIsShowLeftButton){
                            mLeftIv.setSelected(true);
                            mLeftBgDrawable.setAlpha(255);// show bg ,为嘛不用visible
                        }else if(mRawX > (getWidth() - mCenterIv.getWidth()) && mIsShowRightButton){
                            mRightIv.setSelected(true);
                            mRightBgDrawable.setAlpha(255);
                        }else{
                            mLeftIv.setSelected(false);
                            mRightIv.setSelected(false);
                            mLeftBgDrawable.setAlpha(0);
                            mRightBgDrawable.setAlpha(0);
                        }
                        v.setX(event.getRawX() + mDeltaX);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mRawX = (int) event.getRawX();
                        if(mRawX < mCenterIv.getWidth() && mIsShowLeftButton){
                            if(mLeftListener != null){
                                mLeftListener.onSelected();
                            }
                        }else if(mRawX > (getWidth() - mCenterIv.getWidth()) && mIsShowRightButton){
                            if(mRightListener != null){
                                mRightListener.onSelected();
                            }
                        }else{
                            resetState(v);
                        }
                        break;
                }
                return true;
            }

            private void resetState(View v){
                v.animate().scaleX(ICON_SCALE_DEFAULT)
                        .scaleY(ICON_SCALE_DEFAULT)
                        .x(mInitX)
                        .setDuration(0)
                        .start();
                if(mHasCenterIcon){
                    mCenterIv.getDrawable().setAlpha(255);
                }
                hideButton(); //隐藏左右按钮
                resumeAnimation();//继续 响铃的动画
            }

        });
    }

    /*************************************** center animator Begin  *************/

    private void initAndStartRippleAnimation(){
        mOuterAnimator = getRippleAnimation(mOuterCircleIv);
        mMiddleAnimator = getRippleAnimation(mMiddleCircleIv);
        mInnerAnimator = getRippleAnimation(mInnerCircleIv);
        mIsStopAnimation = false;
        mOuterAnimator.start();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMiddleAnimator.start();
            }
        }, ANIMATION_TIME);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mInnerAnimator.start();
            }
        }, ANIMATION_TIME);

    }

    public AnimatorSet getRippleAnimation(ImageView iv){
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv,
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2.5f),
                PropertyValuesHolder.ofFloat("scaleY", 1.0f, 2.5f),
                PropertyValuesHolder.ofFloat("alpha", 0, 1.0f));
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(iv,
                PropertyValuesHolder.ofFloat("scaleX", 2.5f, 4.0f),
                PropertyValuesHolder.ofFloat("scaleY", 2.5f, 4.0f),
                PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));
        animator1.setDuration(500);
        animator1.setInterpolator(new DecelerateInterpolator());

        final AnimatorSet set = new AnimatorSet();
        set.play(animator).before(animator1);
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!mIsStopAnimation){
                    set.start();
                }
            }
        });
        return set;
    }

    public void pauseAnimation(){
        if(mIconAnimator != null){
          //  mIconAnimator.pause();
        }
        mOuterCircleIv.setVisibility(View.GONE);
        mMiddleCircleIv.setVisibility(View.GONE);
        mInnerCircleIv.setVisibility(View.GONE);
//        mOuterAnimator.pause();
//        mMiddleAnimator.pause();
//        mInnerAnimator.pause();
    }

    public void resumeAnimation(){
        if(mIconAnimator != null){
           // mIconAnimator.resume();
        }
        mOuterCircleIv.setVisibility(View.VISIBLE);
        mMiddleCircleIv.setVisibility(View.VISIBLE);
        mInnerCircleIv.setVisibility(View.VISIBLE);
//        mOuterAnimator.resume();
//        mMiddleAnimator.resume();
//        mInnerAnimator.resume();
    }

    public void endAnimation(){
        if(mIconAnimator != null){
            mIconAnimator.end();
        }
        mIsStopAnimation = true;
        mOuterAnimator.end();
        mInnerAnimator.end();
        mMiddleAnimator.end();
    }

    /*************************************** center animator end  *************/


    private void initRotationAnimation(){
        if(!mHasCenterIcon){
            return;
        }
        mIconAnimator = ObjectAnimator.ofFloat(mCenterIv, "rotation", 0f, 15f);
        mIconAnimator.setRepeatCount(Animation.INFINITE);
        mIconAnimator.setInterpolator(new CycleInterpolator(1));
        mIconAnimator.setDuration(2000);
        mIconAnimator.start();
    }


    /*************************************** Listener  *************/

    public interface OnSelectedChangedListener{
        void onSelected();
    }

    public void setLeftIconListener(OnSelectedChangedListener listener){
        this.mLeftListener = listener;
    }

    public void setRightIconListener(OnSelectedChangedListener listener){
        this.mRightListener = listener;
    }

    /*************************************** public method  *************/

    public void setContent(CharSequence str){
        mContentTv.setText(str);
    }

    public void setSubContent(CharSequence str){
        mSubContentTv.setText(str);
    }

    public void enableTipTv(String text){
        mTipTv.setText(text);
        mTipTv.setVisibility(View.VISIBLE);
    }

    public void resetToInit(){
        mCenterIv.clearAnimation();
        mCenterIv.animate()
                .scaleX(ICON_SCALE_DEFAULT)
                .scaleY(ICON_SCALE_DEFAULT)
                .x(mCenterIvInitX)
                .setDuration(0)
                .start();
        if(mHasCenterIcon){
            mCenterIv.getDrawable().setAlpha(255);
        }
        hideButton();
        resumeAnimation();

    }

    public void setLeftIcon(int resId){
        mLeftIv.setImageResource(resId);
        mIsShowLeftButton = (resId != 0);
    }

    public void setRightIcon(int resId){
        mRightIv.setImageResource(resId);
        mIsShowRightButton = (resId != 0);
    }

    public void setLeftIconColor(ColorStateList color){
        mLeftIv.setImageTintList(color);
    }

    public void setRightIconColor(ColorStateList color){
        mRightIv.setImageTintList(color);
    }

    public void setLeftBgColor(ColorStateList color){
        mLeftBgDrawable.setTintList(color);
    }

    public void setRightBgColor(ColorStateList color){
        mRightBgDrawable.setTintList(color);
    }


}

