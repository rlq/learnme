package com.lq.ren.many.learn.service;

import android.content.Context;
import android.util.Log;

/**
 * Created by lqren on 16/8/25.
 */
public class BaseReceiver extends MesReceiver {


    @Override
    public void onCompanionConnected(Context context) {
        Log.e("HEHE", "与companion 已连接");
    }

    @Override
    public void onCompanionDisconnected(Context context) {
        Log.e("HEHE", "与companion 已断开连接");
    }

    @Override
    public void onMessageReceived(Context context, String path, byte[] data) {
        if (path.startsWith("3")) {
            Log.e("HEHE", "请求已接收, 处理data");
        }
    }
}
