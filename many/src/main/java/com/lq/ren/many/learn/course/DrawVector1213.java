package com.lq.ren.many.learn.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.View;

import com.lq.ren.many.R;

/**
 * Author lqren on 16/12/13.
 */
public class DrawVector1213 extends View {

    private Bitmap bitmap;
    private Canvas canvasVd;
    private VectorDrawable vd;
    private boolean isBegin;

    public DrawVector1213(Context context) {
        super(context);
        vd = (VectorDrawable) getResources().getDrawable(R.drawable.ic_vector_1213);
        vd.setBounds(0, 0, 400, 400);
        isBegin = true;
        bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        canvasVd = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        vd.draw(canvasVd);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }


}
