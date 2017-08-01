package com.lq.ren.many.learn.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;

import com.mobvoi.ticwear.appstore.R;

public class Utilities {

    private static int sIconWidth = -1;
    private static int sIconHeight = -1;
    private static int sIconTextureWidth = -1;
    private static int sIconTextureHeight = -1;

    private static final Paint sBlurPaint = new Paint();
    private static final Paint sGlowColorPressedPaint = new Paint();
    private static final Paint sGlowColorFocusedPaint = new Paint();
    private static final Paint sDisabledPaint = new Paint();
    private static final Rect sOldBounds = new Rect();
    private static final Canvas sCanvas = new Canvas();

    static {
        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
    }

    static int sColors[] = {0xffff0000, 0xff00ff00, 0xff0000ff};
    static int sColorIndex = 0;

    public static int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());

    }

    public static void initStatics(Context context) {
        final Resources resources = context.getResources();
        sIconWidth = sIconHeight = resources.getDimensionPixelSize(R.dimen.wear_app_thumnail_size);
    }

    public static String appendIconSizeInfo(String logUrl) {
        return appendImageSizeInfo(logUrl, sIconWidth, sIconHeight);
    }

    public static String appendImageSizeInfo(String logUrl, int width, int height) {
        if (TextUtils.isEmpty(logUrl)) {
            return logUrl;
        } else {
            StringBuilder imgBuilder = new StringBuilder();
            imgBuilder.append(logUrl).append("@").append(width).append("w").append("_").append(height).append("h")
                    .append(".src");
            return imgBuilder.toString();
        }
    }

}
