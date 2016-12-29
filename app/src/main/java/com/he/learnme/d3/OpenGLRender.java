package com.he.learnme.d3;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Author lqren on 16/11/21.
 */
public class OpenGLRender implements GLSurfaceView.Renderer {

    float rotateTri = 0, rotateQuad = 1;

    //三角形的顶点
    private float[] triggerBuffer = new float[] {
            0, 1, 0, //上顶点
            -1, -1, 0,
            1, -1, 0
    };

    //正方形的顶点
    private float[] quateBuffer = new float[] {
            1, 1, 0,
            -1, -1, 0,
            1, -1, 0
            -1, 1, 0,
    };

    private float[] colorBuffer = new float[] {
            1, 0, 0, 1,
            0, 1, 0, 1,
            0, 0, 1, 1
    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 黑色背景
        gl.glClearColor(0, 0, 0, 0);
        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 所作深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,  GL10.GL_FASTEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        float ratio = (float) width / height;
        //设置OpenGL场景的大小
        gl10.glViewport(0, 0, width, height);
        //设置投影矩阵
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        //垂直投影矩阵
        gl10.glLoadIdentity();
        //设置视口的大小
        gl10.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //选择模型观察矩阵
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        //重置模型观察矩阵
        gl10.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //清除屏幕和深度缓存
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //重置当前的模型观察矩阵
        gl10.glLoadIdentity();
        //左移 1.5单位,并移入屏幕6.0
        gl10.glTranslatef(-1.5f, 0f, -6.0f);
        //设置旋转
        gl10.glRotatef(rotateTri, 0f, 1f, 0f);
        //设置定点数组
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置颜色数组
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glColorPointer(4, GL10.GL_FLOAT, 0, BufferUtil.fBuffer(colorBuffer));
        //绘制三角形
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.fBuffer(triggerBuffer));
        gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glFinish();

        //绘制正方形
        gl10.glLoadIdentity();
        gl10.glTranslatef(1.5f, 0f, -6f);
        gl10.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);//蓝色
        gl10.glRotatef(rotateQuad, 1f, 0f, 0f);
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.fBuffer(quateBuffer));
        gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 4);
        gl10.glFinish();

        //取消顶点数组
        gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //改变旋转角度
        rotateTri += 1f;
        rotateQuad -= 1f;

    }
}
