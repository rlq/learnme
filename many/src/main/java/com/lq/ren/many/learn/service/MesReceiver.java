package com.lq.ren.many.learn.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by lqren on 16/8/25.
 */
public abstract class MesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals("1")) {
            onCompanionConnected(context);
        } else if (action.equals("2")) {
            onCompanionDisconnected(context);
        } else {
            String path = intent.getStringExtra("3");
            byte[] data = intent.getByteArrayExtra("4");
            onMessageReceived(context, path, data);
        }
    }

    public abstract void onCompanionConnected(Context context);

    public abstract void onCompanionDisconnected(Context context);

    public abstract void onMessageReceived(Context context, String path, byte[] data);

}
