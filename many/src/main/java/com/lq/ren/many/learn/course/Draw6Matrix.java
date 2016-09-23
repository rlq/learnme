package com.lq.ren.many.learn.course;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/8/13.
 * Matrix 可以操作图像,分为translate rotate scale skew(倾斜)
 * 4种操作 除了translate外, 都有 set post pre 3种操作方式
 */
public class Draw6Matrix extends Activity{

    private Button mLeftBtn;
    private Button mRightBtn;
    private ImageView mImage;
    private TextView mText;

    private int scaleTime = 1;
    private int scaleAngle = 1;

    private Bitmap mSourceBmp = null;
    private int widthOrig = 0;
    private int heightOrig = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_drawmatrix);

        mLeftBtn = (Button) findViewById(R.id.leftBtn);
        mRightBtn = (Button) findViewById(R.id.rightBtn);
        mImage = (ImageView) findViewById(R.id.imageView);
        mText = (TextView) findViewById(R.id.textScale);

        mSourceBmp = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
        widthOrig = mSourceBmp.getWidth();
        heightOrig = mSourceBmp.getHeight();

        mImage.setImageBitmap(mSourceBmp);//加载drawable

        mLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleAngle--;
                if(scaleAngle < -5){
                    scaleAngle = -5;
                }
                setRotate();
            }
        });

        mRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleAngle++;
                if(scaleAngle > 5){
                    scaleAngle =  5;
                }
               setRotate();
            }
        });

    }

    private void setRotate(){
        /**scaleTime = -1 维持1:1的宽高比例 */
        int newWidth = widthOrig * scaleAngle;
        int newHeight = heightOrig * scaleAngle;

        float scaleWidth = ((float) newWidth) / widthOrig;
        float scaleHeight = ((float) newHeight) / heightOrig;

        Matrix matrix = new Matrix();
        /**使用Matrix.postScale() 设置维度 */
        //matrix.postScale(scaleWidth, scaleHeight);

        /** 使用Matrix.setRotate旋转Bitmap*/
        matrix.setRotate( scaleAngle);

        Bitmap bitmap = Bitmap.createBitmap(mSourceBmp, 0, 0,
                widthOrig, heightOrig, matrix, true);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);

        mImage.setImageDrawable(drawable);
        mText.setText(Integer.toString(5*scaleAngle));
    }
}
