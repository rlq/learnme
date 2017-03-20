package com.lq.ren.crazynotes.net;

import android.app.Activity;

/**
 * Author lqren on 17/3/14.
 */

public class NetRequest {

    private NetRequest(Activity activity, int method, String url, String contentType,
                       byte[] requestBody, )




    public interface NetListener<T> {
        void onResponse(T resp)
    }
}
