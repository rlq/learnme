package com.lq.ren.many.calendar.bdmap0923;

import android.content.Context;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;

/**
 * Author lqren on 16/9/22.
 */
public class MapImp {

    private MapView mapView;
    private BaiduMap map;

    public void init() {
        map = mapView.getMap();
    }

    public MapView createFixMapView(Context context) {
        BaiduMapOptions options = new BaiduMapOptions()
                .overlookingGesturesEnabled(false)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .scaleControlEnabled(false)
                .scrollGesturesEnabled(false)
                .zoomControlsEnabled(false)
                .zoomGesturesEnabled(false);
        MapView mapView = new MapView(context, options);
        mapView.setClickable(false);
        mapView.setVisibility(View.INVISIBLE);
        init();
        return mapView;
    }
}


