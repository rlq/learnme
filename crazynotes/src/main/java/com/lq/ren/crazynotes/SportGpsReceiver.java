package com.lq.ren.crazynotes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;


/**
 * Author lqren on 17/6/12.
 */

public class SportGpsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("HEHE", "Gps onReceiver : " + intent.getAction() );

        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Log.w("HEHE", "Gps android.location.PROVIDERS_CHANGED " );
            LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isProvideEnable = alm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
    }
}
