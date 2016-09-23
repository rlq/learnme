package com.lq.ren.many.calendar.hashmapstep7;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by lqren on 16/9/9.
 */
public class MapToFile0909String {

    /**
     * 以下是9.7写的一个测试, 因为目前9.9 String 为key时还是会有重复,现在做测试///////////////
     */

    private static File mFile ;
    private static ObjectOutputStream mOutputStream = null;
    private static HashMap<String, Integer> mMapTest = new HashMap<>();

    public static void createFile(Context context) {
        mFile = new File(context.getDir("data", context.MODE_PRIVATE), "Stringtest.txt");
        mMapTest = new HashMap<>();

        readStringStream();
    }

    public static void setValue(String alarmId, int time) {
        mMapTest.put(alarmId, time);
        setMapToFile();
    }

    public static int getValue(String alarmId) {
        if (mMapTest.containsKey(alarmId)) {
            return mMapTest.get(alarmId);
        }
        return 0;
    }

    public static void removeId(String alarmId) {
        if (mFile == null) {
            return;
        }
        if (mMapTest.containsKey(alarmId)) {
            mMapTest.remove(alarmId);
        }
        setMapToFile();
    }


    private static void setMapToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(mFile));
            outputStream.writeObject(mMapTest);
            Log.v("HEHE", "setMapToFile map : "  + mMapTest);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** 以下是 从file中读取各种数据  */

    // 这个是创建后直接read 测试通过
    public static void createFileToRead() throws FileNotFoundException {
        Log.v("HEHE", "createFileToRead " );
        try {
            mOutputStream = new ObjectOutputStream(new FileOutputStream(mFile));
            mOutputStream.writeObject(mMapTest);
            Log.v("HEHE", "writeObject " );
            mOutputStream.flush();
            mOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mFile));
            Log.v("HEHE", "createFileToRead " + objectInputStream.readObject());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** 这个需要测试  相当于存入了hashmap 读取后转为String 重点测试 */
    public static void readStringStream() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mFile));
            if (objectInputStream == null) {
                return;
            }
            //Log.e("HEHE",  "getMapFromFile%%%%  " + objectInputStream.readObject().toString().substring(1));//"getMapFromFile%%%%  " +
            String mapString = objectInputStream.readObject().toString();//"{a=1,b=2,c=3}";//
            mapString = mapString.substring(1, mapString.length() -1);
            if (mapString.length() == 0) {
                return;
            }
            Log.i("HEHE", "getMapFromFile string " + mapString + ", size: " + mapString.split(",").length);//
            String[] strs = new String[mapString.split(",").length];
            for (int i = 0; i < mapString.split(",").length; i++) {
                strs[i] = mapString.split(",")[i];
                Log.e("HEHE", "Str " + strs[i]);
                mMapTest.put(mapString.split(",")[i].split("=")[0].trim(),  Integer.valueOf(mapString.split(",")[i].split("=")[1].trim()));
            }
            Log.i("HEHE", "getmap " + mMapTest + ", map size " + mMapTest.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 这个测试没问题
    public static HashMap<String, String> readHashMapStream() {
        HashMap<String, String> map = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mFile));
            map = (HashMap<String, String>) objectInputStream.readObject();
            if (map != null && map.size() == 0) {
                return null;
            }
            Log.e("HEHE", "readHashMapStream map: " + map + ", size:" + map.size());

//            for (int i = 0; i < map.size(); i++) {
//                Log.e("HEHE", "readBeanStream " + map.get(i));
//            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static MapToFileBean[] readBeanStream() {
        MapToFileBean[] beans = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(mFile));
            beans = (MapToFileBean[]) objectInputStream.readObject();
            for (int i = 0; i < beans.length; i++) {
                Log.e("HEHE", "readBeanStream " + beans[i]);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return beans;
    }

//    private MapToFileBean getALarm() {
//        MapToFileBean alarm = new MapToFileBean();
//        alarm.setId(id);
//        alarm.setTime(time);
//        Log.e("HEHE", "id: "+ id + ", time :" + time);
//        return alarm;
//    }

}
