package com.lq.ren.many.learn.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.List;


public class JSONUtil {
    private static final String TAG = "JSONUtil";

    public final static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        try {
            return JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            Log.e(TAG, String.format("Failed to parse json string to array."));
            return null;
        }
    }

    public final static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            return JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            Log.e(TAG, "Fail to parse the string " + jsonString + "\n" + e.getMessage(), e);
            return null;
        }
    }

    public final static <T> String toJSONString(T t) {
        try {
            return JSON.toJSONString(t);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return "";
        }
    }
}
