package com.lq.ren.many;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Author lqren on 16/9/23.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //SDKInitializer.initialize(getApplicationContext());
    }
}
