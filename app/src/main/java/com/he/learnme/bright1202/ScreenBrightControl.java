package com.he.learnme.bright1202;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.System;
import android.util.Log;
import android.view.WindowManager;

/**
 * Author lqren on 16/12/2.
 */
public class ScreenBrightControl {

    /**
     * 权限 <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     * （1）方法：
     lp.screenBrightness 取值 0.0 -- 1.0 ※设定值（float）的范围，默认小于 0（系统设定）、0.0（暗）～1.0（亮） ※调用处理的地方，例如， Activity.onCreate()等等

     代码：
     WindowManager.LayoutParams lp = getWindow().getAttributes();

     lp.screenBrightness = 1.0f;

     getWindow().setAttributes(lp);

     （2）恢复
     当离开当前Acitivity时，屏幕亮度会恢复到原先的亮度。另外将lp.screenBrightness 设为 -1（WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE），也会让屏幕恢复到原先的亮度（即系统设置）。

     （3）最小亮度
     WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE，官方文档说这个值可以将屏幕亮度设置到最低亮度（Lowest Brightness）。实际意识是将屏幕设置到全黑，屏幕也无法响应触控了。
     在G3（CM6）上，将屏幕设置到最低亮度值是0.004（精度0.001），这时屏幕基本全黑，但仍能控制。低于0.004（精度0.001）时，屏幕便失去控制。0.01也是个要记录的值，屏幕亮度足够低，当仍能看到东西。这些值应该是和设备有关的，不能乱设。
     * 直接用 getWindow().setAttributes(WindowManager.LayoutParams) 可以只改变当前窗口的亮度，不影响系统设置。
     * @param context
     * @return
     */
    public static boolean isAutoBrightness(Context context) {
        boolean auto = false;
        ContentResolver contentResolver = context.getContentResolver();
        try {
            auto = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            Log.e("HEHE", "Settings.SettingNotFountException: ", e);
        }
        return auto;
    }

    public static void setScreenBrightness(Context context, int value) {
        Settings.System.putInt(context.getContentResolver(), System.SCREEN_BRIGHTNESS, value);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.screenBrightness = (value <= 0 ? 1 : value) / 255f;
    }

    public static int getBrightnessLevel(Context context) {
        return System.getInt(context.getContentResolver(), System.SCREEN_BRIGHTNESS, -1);
    }

    public static void stopAutoBrightness(Context context) {
        System.putInt(context.getContentResolver(), System.SCREEN_BRIGHTNESS_MODE, System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    public static void startAutoBrightness(Context context) {
        System.putInt(context.getContentResolver(), System.SCREEN_BRIGHTNESS_MODE, System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }


}
