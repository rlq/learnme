package com.lq.ren.many.learn.alarm.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

/**
 * Created by lqren on 16/8/10.
 */
public class MyGestureClient {
    public static final String TAG = "GestureClient";
    public static final String SERVICE_PACKAGE = "";
    public static final String ACTION_GESTURE_SERVICE = "";

    private int gestureGroup ;
    //private IGestureService gestureService;

    public MyGestureClient(int gestureGroup){
        this.gestureGroup = gestureGroup;
    }

    public static MyGestureClient getInstance(int gestureGroup){
        return new MyGestureClient(gestureGroup);
    }

    public static boolean startService(Context context){
        Log.d(TAG, "start service");
        Intent intent = new Intent(ACTION_GESTURE_SERVICE);
        intent.setPackage(SERVICE_PACKAGE);
        intent = createExplicitFromImplicitIntent(context, intent);
        if(intent == null){
            return false;
        }
        return context.startService(intent) != null;
    }

    public static boolean stioService(Context context){
        Intent intent = new Intent(ACTION_GESTURE_SERVICE);
        intent.setPackage(SERVICE_PACKAGE);
        intent = createExplicitFromImplicitIntent(context, intent);
        if(intent != null){
            return false;
        }
        return context.stopService(intent);
    }



    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent){
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent, 0);
        if(resolveInfoList == null || resolveInfoList.size() == 0){
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName componentInfo = new ComponentName(packageName, className);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(componentInfo);
        return explicitIntent;
    }


    public interface IGestureDetectedCallback{
        void onGestureDetected(int type);
    }
}
