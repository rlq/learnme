package com.lq.ren.many;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.lq.ren.many.calendar.hashmapstep7.MapToFile0909String;
import com.lq.ren.many.calendar.indicator1019.ActionView;
import com.lq.ren.many.calendar.matrix929.Canvas1022;
import com.lq.ren.many.calendar.matrix929.DynamicView928;
import com.lq.ren.many.calendar.path1205.PathView;
import com.lq.ren.many.calendar.progress0924.GradientProgress;
import com.lq.ren.many.calendar.view.CombintionView;
import com.lq.ren.many.calendar.view.ListDeleteAdapter;
import com.lq.ren.many.calendar.view.ListDeleteView;
import com.lq.ren.many.calendar.view.step5.RoundImage0905;
import com.lq.ren.many.learn.course.Draw1208Ripple;
import com.lq.ren.many.learn.course.Draw4Shader;
import com.lq.ren.many.learn.course.DrawVector1213;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lqren on 16/8/11.
 */
public class CustomActivity extends Activity {

    private List<String> mContentList = new ArrayList<String>();
    private ListDeleteView mDeleteView;
    private ListDeleteAdapter mAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.custom_customview);

        //initListView();

        /**DrawView */
        //initDrawView();

        /** gps*/
        //testGps();

        /**0905 RoundView 圆角 */
        //step5SetRoundDrawable();

        /**0907 shared */
        //startActivity(new Intent(CustomActivity.this, SharedActivity.class));

        /**0909 file and hashMap */
       // mapToFileReadString();

        /**0918 draw point */
        //setContentView(new Draw918(this));

        /**0919 calendar add */
       // calendar0919();

        /**0919 LoadingView */
        //setContentView(new LoadingView(this));

        /**0922 BaiduMap */
//        FrameLayout layout = (FrameLayout) findViewById(R.id.map);
//        layout.addView(new MapImp().createFixMapView(this));

        //0928 path
        //setContentView();

        //1010 date
        Log.d("HEHE", "10/10 date: "+ getDate(10, 7));

        //10.16
        //startActivity(new Intent(this, GestureActivity.class));

        //10.19
        //setContentView(new ActionView(this));

        //10.24
//        GradientProgress view = new GradientProgress(this);
//        view.setCurrentCount(50);
//        view.setMaxCount(100);
//        view.setScore(50);
//        setContentView(view);

        //12.05
//        setContentView(new PathView(this));

        //12.08
//        setContentView(new Draw1208Ripple(this));

        //12.13
        setContentView(new DrawVector1213(this));
    }

    private void initListView() {

        ((CombintionView) findViewById(R.id.combine)).setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("HEHE", "哈哈");
            }
        });

        for (int i = 0; i < 20; i++) {
            mContentList.add(i, "我是 " + i);
        }

        mDeleteView = (ListDeleteView) findViewById(R.id.delete_view);
        mDeleteView.setOnDeleteListener(new ListDeleteView.OnListDeleteListener() {
            @Override
            public void onDelete(int index) {
                Log.d("HEHE", "哈哈2 ");
                mContentList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });
        Log.d("HEHE", "哈哈1 ");

        mAdapter = new ListDeleteAdapter(this, 0);
        mAdapter.setDate(mContentList);
        mDeleteView.setAdapter(mAdapter);
    }

    private void initDrawView() {
        // Draw1View view = new Draw1View(this);

        //Draw2Canvas view = new Draw2Canvas(this);

        // Draw3Graph view = new Draw3Graph(this);

        Draw4Shader view = new Draw4Shader(this);

        // Draw5Animator view = new Draw5Animator(this);

        // Draw5AnimatorFrame view = new Draw5AnimatorFrame(this);

        setContentView(view);
    }

    LocationManager mLocationManager;

    private void testGps() {
        Log.e("HEHE", "test gps");
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_LOW);

        mLocationManager.getBestProvider(c, true);

        String provider = mLocationManager.getBestProvider(c, true);
        Location lc;
        if (provider == null && provider.equals("")) {

        } else {
            lc = mLocationManager.getLastKnownLocation(provider);
        }
        updateLocation(lc); */

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("HEHE", "test gps false");
            return;
        }
        updateLocation(null);
        //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 100, mListener);
    }


    private LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(CustomActivity.this, "onLocationChanged " + location.toString(), Toast.LENGTH_LONG).show();
            Log.e("HEHE", location.toString());
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Toast.makeText(CustomActivity.this, "onStatusChanged " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", "onStatusChanged " + s);

        }

        @Override
        public void onProviderEnabled(String s) {
            Toast.makeText(CustomActivity.this, "onProviderEnabled " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", "onProviderEnabled " + s);
            updateLocation(null);
        }

        @Override
        public void onProviderDisabled(String s) {
            Toast.makeText(CustomActivity.this, "onProviderDisabled " + s, Toast.LENGTH_LONG).show();
            Log.e("HEHE", " onProviderDisabled " + s);

        }
    };

    public void updateLocation(Location lc) {
        String last;
        TextView tv = (TextView) findViewById(R.id.gps);
        if (lc != null) {
            double lat = lc.getLatitude();
            double lng = lc.getLongitude();

            last = "维度：" + lat + "\n经度：" + lng;
        } else {
            last = "无法获取当前位置！";
        }
        tv.setText("您的当前位置：\n" + last);
    }

    // 0905 Drawable
    private void step5SetRoundDrawable() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qq);
        ImageView iv = (ImageView) findViewById(R.id.roundIv);
        iv.setImageDrawable(new RoundImage0905(bitmap));
    }

    // 0909 mapToFile  call to MapToFile0909String and bean
    int count = 0;

    private void mapToFileReadString() {
        MapToFile0909String.createFile(this);
        findViewById(R.id.addCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add 重复的key
                count++;
                MapToFile0909String.setValue("id_" + count, count);
                MapToFile0909String.readStringStream();
            }
        });

        findViewById(R.id.minusCount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                MapToFile0909String.setValue("id_" + count, count);
                //MapToFile0909String.readHashMapStream();
                MapToFile0909String.readStringStream();
            }
        });

    }

    //0919 Calendar add
    private void calendar0919() {
        Calendar calendar = Calendar.getInstance();
        Log.e("HEHE", "calendar add before " + calendar);
        calendar.add(Calendar.MONTH, -2);
//        calendar.add(Calendar.DAY_OF_WEEK, -3);
//        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Log.e("HEHE", "calendar " + calendar);

        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
        Log.v("HEHE", "format " + format.format(calendar.getTime()));
    }

    private Date getDate(int d, int h) {
        Calendar calendar = Calendar.getInstance();
        Log.v("HEHE", "10,10 now caledar: " + calendar);
        Log.v("HEHE", "10,10 now: " + new Date(System.currentTimeMillis()));
        calendar.set(Calendar.DAY_OF_MONTH, d);
        calendar.set(Calendar.HOUR, h);
        Log.v("HEHE", "10,10 caledar: " + calendar);
        return new Date(calendar.getTimeInMillis());
    }


}


