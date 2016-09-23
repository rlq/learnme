package com.lq.ren.many.calendar.view.step5;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.ColorInt;


/**
 * Created by lqren on 16/9/5.
 * 与widget/ClipPathDrawable 相同
 */
public abstract class ClipPathDrawable0905 extends Drawable{

    static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;
    private final Paint mPathPaint = new Paint();
    private final Drawable mSource;
    private final Path mClipPath = new Path();

    private ColorStateList mTint = null;
    private Mode mTintMode = DEFAULT_TINT_MODE;
    private PorterDuffColorFilter mTintFilter;

    public ClipPathDrawable0905(int color) {
        this(new ColorDrawable(color));
    }

    public ClipPathDrawable0905(Drawable drawable) {
        super();
        mSource = drawable;
        mPathPaint.setAntiAlias(true);
    }

    /**
     * 一下的3个method, 去设置ColorStateList Mode
     *
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mSource.setColorFilter(colorFilter);
        mPathPaint.setColorFilter(colorFilter);
    }

    @Override
    public void setTintList(ColorStateList tint) {
        mSource.setTintList(tint);
        mTint = tint;
        mTintFilter = createTintFilter(tint, mTintMode);
        invalidateSelf();
    }

    @Override
    public void setTintMode(Mode tintMode) {
        mSource.setTintMode(tintMode);
        mTintMode = tintMode;
        mTintFilter = createTintFilter(mTint, tintMode);
        invalidateSelf();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        if (mTint != null && mTintMode != null) {
            mTintFilter = createTintFilter(mTint, mTintMode);
            return true;
        }
        return false;
    }

    @Override
    public boolean isStateful() {
        return mTint != null && mTint.isStateful();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mSource.setBounds(left, top, right, bottom);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        onResetPath(mClipPath, bounds);
    }

    abstract protected void onResetPath(Path path, Rect bounds);

    public Path getClipPath() {
        return mClipPath;
    }

    public Paint getPathPaint() {
        return mPathPaint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();

        if (mSource instanceof ColorDrawable) {
            int color = ((ColorDrawable) mSource).getColor();

        }
    }

    private void drawWithColor(Canvas canvas, @ColorInt int color) {
        final ColorFilter colorFilter = mPathPaint.getColorFilter();
        if ((color >>> 24) != 0 || colorFilter != null || mTintFilter != null) {
            if (colorFilter == null) {
                mPathPaint.setColorFilter(mTintFilter);
            }
            mPathPaint.setColor(color);
            canvas.drawPath(mClipPath, mPathPaint);
            mPathPaint.setColorFilter(colorFilter);
        }
    }

    private void drawWithClip(Canvas canvas, Drawable drawable) {
        canvas.clipRect(getBounds());
        canvas.clipPath(mClipPath);
        drawable.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {
        mSource.setAlpha(i);
    }

    @Override
    public int getOpacity() {
        return 0;
    }



    PorterDuffColorFilter createTintFilter(ColorStateList tint, Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }

        final int color = tint.getColorForState(getState(), Color.TRANSPARENT);
        return new PorterDuffColorFilter(color, tintMode);
    }
}
