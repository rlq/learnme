package com.lq.ren.many.calendar.bdmap0923;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author lqren on 16/9/22.
 */
public interface IMapView {

    void onResume();

    boolean dispatchTouchEvent(MotionEvent event);

    void onPause();

    void onDestroy();

    void setVisibility(int visibility);

    int getVisibility();

    IMapView initView(@NonNull Context context);

    IMapView initSportFinishView(@NonNull Context context);

    IMapView initSportRunningView(@NonNull Context context);

    IMapView initSportShareView(@NonNull Context context);

    View getView();

    void setOnMapReady(OnMapViewReadyCallback callback);

    interface OnMapViewReadyCallback {
        void onMapViewLoadOk(@NonNull IMap map);
    }
}
