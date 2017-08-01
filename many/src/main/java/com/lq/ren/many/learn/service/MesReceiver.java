package com.lq.ren.many.learn.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lqren on 16/8/25.
 */
public abstract class MesReceiver extends BroadcastReceiver {

    protected static final String ACTION_CONNECTED = "1";
    protected static final String ACTION_DISCONNECTED = "2";
    protected static final String ACTION_MSG_RECEIVED_PATH = "3";
    protected static final String ACTION_MSG_RECEIVED_DATA = "4";

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ACTION_CONNECTED)) {
            onConnected(context);
        } else if (action.equals(ACTION_DISCONNECTED)) {
            onDisconnected(context);
        } else {
            String path = intent.getStringExtra(ACTION_MSG_RECEIVED_PATH);
            byte[] data = intent.getByteArrayExtra(ACTION_MSG_RECEIVED_DATA);
            onMessageReceived(context, path, data);
        }
    }

    public abstract void onConnected(Context context);

    public abstract void onDisconnected(Context context);

    public abstract void onMessageReceived(Context context, String path, byte[] data);

}
