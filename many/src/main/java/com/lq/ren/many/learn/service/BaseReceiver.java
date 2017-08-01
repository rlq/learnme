package com.lq.ren.many.learn.service;

import android.content.Context;
import android.util.Log;

/**
 * Created by lqren on 16/8/25.
 */
public class BaseReceiver extends MesReceiver {

    private static final String TAG = "HEHE";

    @Override
    public void onConnected(Context context) {
        Log.d(TAG, "connected");
    }

    @Override
    public void onDisconnected(Context context) {
        Log.d(TAG, "disconnected");
    }

    @Override
    public void onMessageReceived(Context context, String path, byte[] data) {
        if (path.startsWith(ACTION_MSG_RECEIVED_PATH)) {
            Log.d(TAG, "msg received, handle data");
        }
    }
}
