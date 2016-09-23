package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/13.
 */
public class Draw5Animator extends View {

    private Animation mAniAlpha = null;
    private Animation mAniScale = null;
    private Animation mAniTrans = null;
    private Animation mAniRotate = null;

    private Bitmap mBitmap = null;

    public Draw5Animator(Context context) {
        super(context);

        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                mAniAlpha = new AlphaAnimation(0.1f, 0.1f);
                mAniAlpha.setDuration(3000);
                startAnimation(mAniAlpha);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mAniScale = new ScaleAnimation(0, 1.0f, 0, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                mAniScale.setDuration(500);
                startAnimation(mAniScale);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mAniTrans = new TranslateAnimation(10, 100, 10, 100);
                mAniTrans.setDuration(1000);
                startAnimation(mAniTrans);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mAniRotate = new RotateAnimation(0, 360f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                mAniRotate.setDuration(1000);
                startAnimation(mAniRotate);
                break;
        }
        return true;
    }
}
