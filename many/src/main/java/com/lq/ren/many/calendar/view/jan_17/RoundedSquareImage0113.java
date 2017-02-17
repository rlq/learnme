package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Author lqren on 17/1/13.
 */
public class RoundedSquareImage0113 extends ImageView {

    private int mCornerRadius;
    private boolean mTopLeft = true;
    private boolean mTopRight = true;
    private boolean mBottomRight = true;
    private boolean mBottomLeft = true;
    private RectF mRoundRect = new RectF();
    private Path mPath;
    private float[] mRadii = new float[8];

    public RoundedSquareImage0113(Context context) {
        super(context);
        init(context, null);
    }

    public RoundedSquareImage0113(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedSquareImage0113(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPath = new Path();
    }

    /**
     * 设置圆角矩形角度
     *
     * @param cornerRadius 角度
     */
    public void setCornerRadius(int cornerRadius) {
        this.mCornerRadius = cornerRadius;
        invalidate();
    }

    /**
     * 设置四个角落,顺时针方向
     *
     * @param topLeft     上左
     * @param topRight    上右
     * @param bottomRight 下右
     * @param bottomLeft  下左
     */
    public void setCorners(boolean topLeft, boolean topRight, boolean bottomRight, boolean bottomLeft) {
        this.mTopLeft = topLeft;
        this.mTopRight = topRight;
        this.mBottomRight = bottomRight;
        this.mBottomLeft = bottomLeft;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        mRoundRect.set(0, 0, getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        mPath.reset();

        mRadii[0] = mTopLeft ? mCornerRadius : 0;
        mRadii[1] = mTopLeft ? mCornerRadius : 0;
        mRadii[2] = mTopRight ? mCornerRadius : 0;
        mRadii[3] = mTopRight ? mCornerRadius : 0;
        mRadii[4] = mBottomRight ? mCornerRadius : 0;
        mRadii[5] = mBottomRight ? mCornerRadius : 0;
        mRadii[6] = mBottomLeft ? mCornerRadius : 0;
        mRadii[7] = mBottomLeft ? mCornerRadius : 0;

        mPath.addRoundRect(mRoundRect, mRadii, Path.Direction.CW);
        canvas.clipPath(mPath);
        super.onDraw(canvas);
    }
}
