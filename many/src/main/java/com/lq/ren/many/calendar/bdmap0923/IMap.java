package com.lq.ren.many.calendar.bdmap0923;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * Author lqren on 16/9/22.
 */
public interface IMap {

    IMap init(@NonNull Object map);

    void clear();

    void snapshot(SnapshotReadyCallback callback);//快照

    void setOnMapLoadedCallback(OnMapLoadedCallback callback);

    void addOverlay(Object options);

    //void animateMapStatus(MapStatusUpdateMobvoi status);

    void setOnMapDrawFrameCallback(OnMapDrawFrameCallback callback);

    interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    interface OnMapLoadedCallback {
        void onMapLoaded();
    }

    interface OnMapDrawFrameCallback {
        void onMapDrawFrame();
    }

}
