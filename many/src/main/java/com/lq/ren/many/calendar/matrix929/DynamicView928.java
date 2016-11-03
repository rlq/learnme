package com.lq.ren.many.calendar.matrix929;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Author lqren on 16/9/28.
 */
public class DynamicView928 extends View {
    private static final int POINT_FOR_CYCLE = 10;

    public static class Wave {
        public float amplitude;
        public float cycle;
        public float speed;

        public Wave(float a, float c,float s) {
            amplitude = a;
            cycle = c;
            speed = s;
        }

        public Wave(Wave o) {
            amplitude = o.amplitude;
            cycle = o.cycle;
            speed = o.speed;
        }
    }

    private final List<Wave> waveConfigs = new ArrayList<>();
    private final List<Paint> wavePaints = new ArrayList<>();
    private final Matrix wavePathScale = new Matrix();
    private List<Path> currentPaths = new ArrayList<>();
    private Path drawingPath = new Path();

    private int viewWidth = 0;
    private int viewHeight = 0;
    private float baseWaveAmplitudeScale = 1f;

    private final BlockingQueue<List<Path>> transferPathsQueue = new LinkedBlockingDeque<>();

    private boolean requestFutureChange = false;
    private final Object requestCondition = new Object();

    private long startAnimateTime = 0;
    private Runnable animateTicker = new Runnable() {
        @Override
        public void run() {

        }
    };


    public DynamicView928(Context context) {
        super(context);
        init();
    }

    public DynamicView928(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicView928(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //if (isInEditMode()) {
            Log.e("HEHE", "init");
            addWave(0.5f, 0.5f, 0, 0, 0);
            addWave(0.5f, 2.5f, 0, Color.BLUE, 2);//2.5圈
            //addWave(0.3f, 2f, 0, Color.RED, 2);
            tick();
       //}
    }

    private int addWave(float amplitude, float cycle, float speed, int color, float stroke) {
        synchronized (waveConfigs) {
            waveConfigs.add(new Wave(amplitude, cycle, speed));
        }

        if (waveConfigs.size() > 1) {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStrokeWidth(stroke);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            wavePaints.add(paint);
        }
        return wavePaints.size();
    }

    private void clearWave() {
        synchronized (waveConfigs) {
            waveConfigs.clear();
        }
        wavePaints.clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!transferPathsQueue.isEmpty()) {
            currentPaths = transferPathsQueue.poll();

            if (currentPaths.size() != wavePaints.size()) {
                throw new RuntimeException("Generated paths size " + currentPaths.size() +
                        " not match the paints size " + wavePaints.size());
            }
        }

        if (currentPaths.isEmpty())
            return;
        wavePathScale.setScale(viewWidth, viewHeight * baseWaveAmplitudeScale);
        wavePathScale.postTranslate(0, viewHeight / 2);
        for (int i = 0; i < currentPaths.size(); i++) {
            Path path = currentPaths.get(i);
            Paint paint = wavePaints.get(i);
            drawingPath.set(path);
            drawingPath.transform(wavePathScale);
            canvas.drawPath(drawingPath, paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewWidth = getWidth();
        viewHeight = getHeight();
        Log.v("HEHE", "viewWidth: "+ viewWidth +", height: " + viewHeight);
    }

    // now waveConfigs size = 1
    // 我的做法是怎样的
    private boolean tick() {
        List<Wave> waveList;
        List<Path> newPaths;
        synchronized (waveConfigs) {
            if (waveConfigs.size() < 2) {
                return false; //< 2不创建这些
            }
            waveList = new ArrayList<>(waveConfigs.size());
            newPaths = new ArrayList<>(waveConfigs.size());
            for (Wave o : waveConfigs) {
                waveList.add(new Wave(o));
            }
        }

        long currentTime = SystemClock.uptimeMillis();
        float t = (currentTime - startAnimateTime) / 1000f;
        float maxCycle = 0; //在list找最大的cycle
        float maxAmplitude = 0;
        for (int i = 0; i < waveList.size(); i++) {
            Wave o = waveList.get(i);
            if (o.cycle > maxCycle)
                maxCycle = o.cycle;
            if (o.amplitude > maxAmplitude && i > 0)
                maxAmplitude = o.amplitude;
        }

        int pointCount = (int) (POINT_FOR_CYCLE * maxCycle); //按照最大圈2.5f ,这里应该是25个点
        Wave baseWave = waveList.get(0);
        waveList.remove(0); // list size -1

        float normal = baseWave.amplitude / maxAmplitude;
        List<PointF> baseWavePoints = generateSineWave(baseWave.amplitude,
                baseWave.cycle, -baseWave.speed * t, pointCount); // 这个可以当做是起始点 生成了很多点
        for (Wave w : waveList) {
            float space = - w.speed * t;
            // 获取到其他点 除起始点
            List<PointF> wavePoints = generateSineWave(w.amplitude, w.cycle, space, pointCount);
            if (wavePoints.size() != baseWavePoints.size()) {
                throw new RuntimeException("size same");
            }

            for (int i = 0; i < wavePoints.size(); i++) {
                PointF p = wavePoints.get(i);
                PointF base = baseWavePoints.get(i);
                p.set(p.x, p.y * base.y * normal);
            }
            //得到这些点之后 需要生成曲线
            Path path = generatePathForCurve(wavePoints);
            newPaths.add(path);
        }

        transferPathsQueue.offer(newPaths);
        return true;
    }

    //生成一些sin的点, (0,1)之间, 现在我也有这些点
    private List<PointF> generateSineWave(float amplitude, float cycle, float offset, int pointCount) {
        int count = pointCount;
        if (count <= 0) {
            count = (int) (POINT_FOR_CYCLE * cycle);
        }
        double T = 1. /cycle;
        double f = 1f / T;

        List<PointF> points = new ArrayList<>(count);
        if (count < 2) {
            return points;
        }
        double dx = 1. / (count -1);
        for (double x = 0; x <= 1; x+=dx) {
            double y = amplitude * Math.sin(2 * Math.PI * f * (x + offset));
            points.add(new PointF((float) x, (float) y));
        }
        return points;
    }

    private Path generatePathForCurve(List<PointF> points) {
        Path path = new Path();
        if (points.isEmpty()) {
            return path;
        }

        PointF prevPoint = points.get(0);// 第0点
        for (int i = 0; i < points.size(); i++) {
            PointF point = points.get(i);
            if (i == 0) {
                path.moveTo(point.x, point.y); //
            } else {
                float midx = (prevPoint.x + point.x) / 2;
                float midy = (prevPoint.x + point.y) / 2;
                if (i == 1) {
                    path.lineTo(midx, midy);
                } else {
                    path.quadTo(prevPoint.x, prevPoint.y, midx, midy);
                }
            }
            prevPoint = point;
            Log.e("HEHE", "point: " + point);
        }
        path.lineTo(prevPoint.x, prevPoint.y);
        return path;
    }

    /*
    if (1 - pointF.get(pointF.size() - 1).x <= 0.15 ) {
            if (1 - pointF.get(pointF.size() - 1).y <= 0.15 ) {
                canvas.translate(10, 10);
                Log.d("HEHE", "####translate :x->, y->:"  + pointF.get(pointF.size() - 1) );
            } else if (pointF.get(pointF.size() - 1).y <= 0.15 ) {
                canvas.translate(10, -10);
                Log.i("HEHE", "####translate :x->, <-y:"  + pointF.get(pointF.size() - 1));
            }
        } else if (pointF.get(pointF.size() - 1).x <= 0.15 ) {
            if (1 - pointF.get(pointF.size() - 1).y <= 0.15 ) {
                canvas.translate(-10, 10);
                Log.d("HEHE", "####translate : <-x, y->:"  + pointF.get(pointF.size() - 1) );
            } else if (pointF.get(pointF.size() - 1).y <= 0.15 ) {
                canvas.translate(-10, -10);
                Log.i("HEHE", "####translate : <-x, <-y:"  + pointF.get(pointF.size() - 1) );
            }
        }

        if (1 - pointF.get(0).x <= 0.15 ) {
            if (1 - pointF.get(0).y <= 0.15 ) {
                canvas.translate(-200, -200);
                LogUtil.d("HEHE", "####translate :x->, y->:"  + pointF.get(0));
            } else if (pointF.get(0).y <= 0.15 ) {
                canvas.translate(-200, 200);
                LogUtil.i("HEHE", "$$$$$$translate :x->, <-y:"  + pointF.get(0));
            }
        } else if (pointF.get(0).x <= 0.15 ) {
            if (1 - pointF.get(0).y <= 0.15 ) {
                canvas.translate(200, -200);
                LogUtil.d("HEHE", "@@@@@translate : <-x, y->:"  + pointF.get(0));
            } else if (pointF.get(0).y <= 0.15 ) {
                canvas.translate(200, 200);
                LogUtil.i("HEHE", "*****translate : <-x, <-y:"  + pointF.get(0));
            }
        }

        MockSportLocationClient client;
    private List<SportLocation> locations;
    SportLocation location;


    private void initMapData() {
        client = new MockSportLocationClient();
        locations = new ArrayList<>();
        initPoints();
    }

    //得到起始点 ,然后生成下一个点 ----->/ 一下子生成100个点
    private void initPoints() {
        client.start();
        location = new SportLocation(1000);
        Random rand = new Random(0);
        double startLat = client.getStartLat(rand);
        double startLon = client.getStartLon(rand);
        location.accuracy = 1;
        location.latitude = startLat;
        location.longitude = startLon;
        //locations.add(location);
        setLocationList(location);

        //Log.d("HEHE", "location 0, x: " + locations.get(0).latitude + ", y: " + locations.get(0).longitude);

        for (int i = 1; i < 100; i++) {
            generateSportLocations();
        }

    }

    // 每一秒生成一个点
    private void generateSportLocations() {
        int count = locations.size();
        if (count >= 2) {
            location = client.nextPoint(locations.get(count - 2), locations.get(count - 1));
            //location = client.nextPoint(locations.get(0), locations.get(1));
        } else {
            location = client.nextPoint(locations.get(0), locations.get(0));
        }
        setLocationList(location);
    }

    private void setLocationList(SportLocation location) {
        PathSmoother smoother = new PathSmoother();
        SportLocation sportLocation = smoother.process(location);
        locations.add(sportLocation);
        locations.add(location);
    }
     */

    /**
       public static SportDataType[][] workingTypes = {
            {SportDataType.Distance, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace},
            {SportDataType.Distance, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace},
            {SportDataType.Duration, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace},
            {SportDataType.Duration, SportDataType.Distance, SportDataType.HeartRate, SportDataType.Speed, SportDataType.Pace},
            };

    public static SportData[][] getWorkingData(SportPoint data) {
        SportTime time = new SportTime(data.time);
        HeartRate heartRate = new HeartRate(data.time, SimpleData.DATA_ACCURACY_MEDIUM, (int) data.heart);
        Distance distance = new Distance(data.time, SimpleData.DATA_ACCURACY_MEDIUM, (int) data.distance);
        Calorie calorie = new Calorie(data.time, (float) data.distance);
        Speed speed = new Speed(data.time, SimpleData.DATA_ACCURACY_MEDIUM, (float) data.distance / data.time);
        Pace pace = new Pace(data.time, SimpleData.DATA_ACCURACY_MEDIUM, (float) data.distance / data.time);
        SportData[][] obj =  {
                {distance, heartRate, calorie, speed, pace},
                {distance, heartRate, calorie, speed, pace},
                {distance, heartRate, calorie, speed, pace},
                {time, distance, heartRate, speed, pace},
        };
        return obj;
    }

    public static SportDataType[][] workingStepTypes = {
            {SportDataType.Distance, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace, SportDataType.Steps},
            {SportDataType.Distance, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace, SportDataType.Steps},
            {SportDataType.Duration, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace, SportDataType.Steps},
            {SportDataType.Duration, SportDataType.Distance,  SportDataType.Speed, SportDataType.Pace, SportDataType.Steps, SportDataType.Steps},
            {SportDataType.Duration, SportDataType.Distance, SportDataType.HeartRate, SportDataType.Calorie, SportDataType.Speed, SportDataType.Pace},
    };

     */
}
