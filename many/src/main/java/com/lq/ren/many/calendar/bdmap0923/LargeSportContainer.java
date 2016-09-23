package com.lq.ren.many.calendar.bdmap0923;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lq.ren.many.calendar.detail0922.data.DataSession;


/**
 * Author lqren on 16/9/22.
 */
public class LargeSportContainer extends FrameLayout {

    SportMapViewCreator sportMapViewCreator;
    SportImageViewCreator sportImageViewCreator;
    IMapView mapView;
    IMap map;

    public interface SportMapViewCreator {
        IMapView createMapView(ViewGroup container);
    }

    public interface SportImageViewCreator {
        ImageView createImageView(ViewGroup container, DataSession sportData);
    }

    public interface OnMapOverlayUpdatedListener {
        void onMapOverlayUpdated(IMap map);
    }

    public interface OnImageInsertedListener {
        void onImageInserted(ViewGroup container);
    }

    public LargeSportContainer(Context context) {
        super(context);
    }

    public LargeSportContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LargeSportContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**AbstractMapOverlayHelper SportMapViewCreator SportImageViewCreator*/
    public void initView() {
        addMapViewIfNeed();
    }
    /**mapView  */
    private void addMapViewIfNeed() {
        if (mapView == null) {
            mapView = sportMapViewCreator.createMapView(this);
            mapView.onResume();
            addView(mapView.getView());
            mapView.setVisibility(INVISIBLE);
        }
    }

    private void removeMapViewIfNeed() {
        if (mapView != null) {
            //Cancel touch event before remove to avoid NPE in BaiduMap
            final long now = SystemClock.uptimeMillis();
            MotionEvent event = MotionEvent.obtain(now, now,
                    MotionEvent.ACTION_CANCEL, 0f, 0f, 0);
            mapView.dispatchTouchEvent(event);

            if (map != null) {
                map.setOnMapLoadedCallback(null);
            }
            mapView.onPause();
            mapView.onDestroy();
            removeView(mapView.getView());
            mapView = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeMapViewIfNeed();
    }
}
