package com.he.learnme.d3;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Author lqren on 16/11/21.
 */
public class TextDomeActivity extends Activity {

    OpenGLRender render = new OpenGLRender();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView gview=new GLSurfaceView(this);
        gview.setRenderer(render);
        setContentView(gview);
    }
}
