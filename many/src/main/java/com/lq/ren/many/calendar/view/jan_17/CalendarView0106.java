package com.lq.ren.many.calendar.view.jan_17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * custom view for the Calendar showing the dates having sport
 */
public class CalendarView0106 extends View implements View.OnTouchListener {
    private final static String TAG = "fit.view.calendarview";
    private Date selectedStartDate;
    private Date selectedEndDate;
    private Date curDate; // 当前日历显示的月
    private Date today; // 今天的日期文字显示红色
    private Date downDate; // 手指按下状态时临时日期
    private Date showFirstDate, showLastDate; // 日历显示的第一个日期和最后一个日期

    private boolean isDownIndexSet;
    private int downIndex; // 按下的格子索引
    private Calendar calendar;
    private Surface surface;
    private int[] date = new int[42]; // 日历显示数字
    int showWeeks = 6;//每个月跨度几个星期,可以是4,5,6
    private int curStartIndex, curEndIndex; // 当前显示的日历起始的索引

    List<Integer> sportDates;
    List<List<Integer>> sprtDateRanges;

    //给控件设置监听事件
    private OnItemClickListener onItemClickListener;

    public CalendarView0106(Context context) {
        super(context);
        init();
    }

    public CalendarView0106(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        curDate = selectedStartDate = selectedEndDate = today = new Date();
        calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        surface = new Surface();
        surface.density = getResources().getDisplayMetrics().density;
//        setBackgroundColor(surface.bgColor);
        setOnTouchListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure");
        surface.width = getResources().getDisplayMetrics().widthPixels;
        surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 2 / 5);
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
                View.MeasureSpec.EXACTLY);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
                View.MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDownIndex(int dateOfMonth){
        isDownIndexSet = true;
        downIndex = dateOfMonth;
        calendar.set(Calendar.DAY_OF_MONTH,dateOfMonth);
        downDate = calendar.getTime();
        invalidate();

    }

    public void setSportDates(List<Integer> sportDates) {
        this.sportDates = sportDates;
        onChangeRange(sportDates);

    }


    private void onChangeRange(List<Integer> list) {
        List<List<Integer>> result = new ArrayList<List<Integer>>(); //list of list of integer
        int i;
        int start = 0;
        List<Integer> sList = new ArrayList<Integer>(2);
        for (i = 1; i < list.size(); i++) {

            if (list.get(i - 1) + 1 != list.get(i)) {
                sList.add(list.get(start));
                sList.add(list.get(i - 1));
                result.add(sList);
                sList = new ArrayList<Integer>(2);
                start = i;

            }

        }
        sList.add(list.get(start));        // for last range
        sList.add(list.get(list.size() - 1));
        result.add(sList);
        onChangeRangWeekly(result);
    }


    private void onChangeRangWeekly(List<List<Integer>> result){
        List<List<Integer>> dateBoundries = new ArrayList<List<Integer>>(); //list of list of integer
        List<Integer> boundary ;
        for (List<Integer> range:result){
            int begin = range.get(0);
            int end = range.get(1);
            if(begin == end){
                dateBoundries.add(range);
            }else{
                int index = begin;
                for (;begin<=end;begin++){
                    calendar.set(Calendar.DAY_OF_MONTH,begin);
                    if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                        boundary = new ArrayList<Integer>(2);
                        boundary.add(index);
                        boundary.add(begin);
                        dateBoundries.add(boundary);
                        index = begin + 1;

                    }
                    if(begin == end){
                        boundary = new ArrayList<Integer>(2);
                        boundary.add(index);
                        boundary.add(begin);
                        dateBoundries.add(boundary);
                    }
                }
            }
        }

        sprtDateRanges = dateBoundries;

        invalidate();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        Log.d(TAG, "[onLayout] changed:"
                + (changed ? "new size" : "not change") + " left:" + left
                + " top:" + top + " right:" + right + " bottom:" + bottom);
//        if (changed) {
        surface.init();
//        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw");
        // 星期
        float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
        for (int i = 0; i < surface.weekText.length; i++) {
            float weekTextX = i
                    * surface.cellWidth
                    + (surface.cellWidth - surface.weekPaint
                    .measureText(surface.weekText[i])) / 2f;
            canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
                    surface.weekPaint);
        }


        // 计算日期
        calculateDate();

        //画出有运动的日期
        drawSportsDate(canvas);
        // 按下状态，选择状态背景色
        drawDownOrSelectedBg(canvas);
        // write date number
        // today index
        int todayIndex = -1;
        calendar.setTime(curDate);
        String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
                + calendar.get(Calendar.MONTH);
        calendar.setTime(today);
        String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
                + calendar.get(Calendar.MONTH);
        if (curYearAndMonth.equals(todayYearAndMonth)) {
            int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
            todayIndex = curStartIndex + todayNumber - 1;
        }
        int i = 0;
        int reduce = 0;
        if (date[6] > 28) {
            i = 7;
            reduce = 7;
        }
        int size = (i + showWeeks * 7);
        for (; i < size; i++) {
            int color = surface.textColor;
            if (isLastMonth(i)) {
                color = surface.borderColor;
            } else if (isNextMonth(i)) {
                color = surface.borderColor;
            }
            if (todayIndex != -1 && i == todayIndex) {
                //toaday text color normal
//                color = surface.todayNumberColor;
            }

//            Log.i("Cal","  i ==="+i);
            drawCellText(canvas, i - reduce, date[i] + "", color);
        }
        super.onDraw(canvas);
    }

    private void getShowWeeks() {
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (daysInMonth) {
            case 28:
                if (dayInWeek == Calendar.SUNDAY) {
                    showWeeks = 4;
                } else {
                    showWeeks = 5;
                }
                break;
            case 29:
                showWeeks = 5;
                break;
            case 30:
                if (dayInWeek == Calendar.SATURDAY) {
                    showWeeks = 6;
                } else {
                    showWeeks = 5;
                }
                break;
            case 31:
                if (dayInWeek == Calendar.SATURDAY || dayInWeek == Calendar.FRIDAY) {
                    showWeeks = 6;
                } else {
                    showWeeks = 5;
                }
                break;
        }


    }

    private void calculateDate() {
        calendar.setTime(curDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int monthStart = dayInWeek;
        if (monthStart == 1) {
            monthStart = 8;
        }
        monthStart -= 1;  //以日为开头-1，以星期一为开头-2
        curStartIndex = monthStart;
        date[monthStart] = 1;
        // last month
        if (monthStart > 0) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
            int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
            for (int i = monthStart - 1; i >= 0; i--) {
                date[i] = dayInmonth;
                dayInmonth--;
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[0]);
        }
        showFirstDate = calendar.getTime();
        // this month
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i < monthDay; i++) {
            date[monthStart + i] = i + 1;
        }
        curEndIndex = monthStart + monthDay;
        // next month
        for (int i = monthStart + monthDay; i < 42; i++) {
            date[i] = i - (monthStart + monthDay) + 1;
        }
        if (curEndIndex < 42) {
            // 显示了下一月的
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_MONTH, date[41]);
        showLastDate = calendar.getTime();
    }

    private void drawSportsDate(Canvas canvas) {
        if (sportDates == null || sportDates.size() == 0) {
            return;
        }
        for (List<Integer> range : sprtDateRanges) {
            int begin = range.get(0);
            int end = range.get(1);
            if (begin == end) {
                drawCircle(canvas, begin);
            } else {
                drawRange(canvas, begin, end);
            }
        }

    }

    /**
     * @param canvas
     * @param index
     * @param text
     */
    private void drawCellText(Canvas canvas, int index, String text, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.datePaint.setColor(color);
        float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight + surface.cellHeight * 3 / 4f;
        float cellX = (surface.cellWidth * (x - 1))
                + (surface.cellWidth - surface.datePaint.measureText(text))
                / 2f;
        canvas.drawText(text, cellX, cellY, surface.datePaint);
    }


    private void drawRange(Canvas canvas, int start, int end) {
        int x = getXByIndex(curStartIndex + start - 1);
        int y = getYByIndex(curStartIndex + start - 1);
        float left = surface.cellWidth * (x - 1) + (surface.cellWidth / 2 - Math.min(surface.cellWidth / 2, surface.cellHeight / 2));
        float top = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight + (surface.cellHeight / 2 - Math.min(surface.cellWidth / 2, surface.cellHeight / 2));
        int endx = getXByIndex(curStartIndex + end - 1);
        int endy = getYByIndex(curStartIndex + end - 1);
        float right = surface.cellWidth * (endx) - (surface.cellWidth / 2 - Math.min(surface.cellWidth / 2, surface.cellHeight / 2));
        float bottom = surface.monthHeight + surface.weekHeight + (endy)
                * surface.cellHeight - (surface.cellHeight / 2 - Math.min(surface.cellWidth / 2, surface.cellHeight / 2));

        surface.cellBgPaint.setColor(surface.cellSelectedColor);
        canvas.drawRoundRect(new RectF(left, top, right, bottom), Math.min(surface.cellWidth / 2, surface.cellHeight / 2), Math.min(surface.cellWidth / 2, surface.cellHeight / 2), surface.cellBgPaint);

    }

    private void drawCircle(Canvas canvas, int index) {
        int x = getXByIndex(curStartIndex + index - 1);
        int y = getYByIndex(curStartIndex + index - 1);
        float left = surface.cellWidth * (x - 1);
        float top = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight;

        canvas.drawCircle(left + surface.cellWidth / 2, top + surface.cellHeight / 2, Math.min(surface.cellHeight / 2 - 2, surface.cellWidth / 2 - 2), surface.cellBgPaint);

    }

    /**
     * @param canvas
     * @param index
     * @param color
     */
    private void drawCellBg(Canvas canvas, int index, int color) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        surface.cellBgPaint.setColor(color);
        float left = surface.cellWidth * (x - 1);
        float top = surface.monthHeight + surface.weekHeight + (y - 1)
                * surface.cellHeight;

        surface.cellBgPaint.setColor(Color.WHITE);
        canvas.drawCircle(left + surface.cellWidth / 2, top + surface.cellHeight / 2, Math.min(surface.cellHeight / 2, surface.cellWidth / 2), surface.cellBgPaint);
        surface.cellBgPaint.setColor(surface.cellSelectedColor);
        canvas.drawCircle(left + surface.cellWidth / 2, top + surface.cellHeight / 2, Math.min(surface.cellHeight / 2 - 2, surface.cellWidth / 2 - 2), surface.cellBgPaint);
    }

    private void drawDownOrSelectedBg(Canvas canvas) {
        // down and not up
        if (downDate != null) {
            if(isDownIndexSet){
                Log.i(TAG,"downDate.getDay()==="  + downDate.getDay());
                Log.i(TAG,"downIndex==="  + downIndex);
                downIndex = downIndex + curStartIndex -1;
                Log.i(TAG,"downIndex2==="  + downIndex);
                isDownIndexSet = false;
            }
            drawCellBg(canvas, downIndex, surface.cellSelectedColor);
        }
    }


    private boolean isLastMonth(int i) {
        if (i < curStartIndex) {
            return true;
        }
        return false;
    }

    private boolean isNextMonth(int i) {
        if (i >= curEndIndex) {
            return true;
        }
        return false;
    }

    private int getXByIndex(int i) {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i) {
        return i / 7 + 1; // 1 2 3 4 5 6
    }

    // 获得当前应该显示的年月
    public String getYearAndmonth() {
        calendar.setTime(curDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "-" + month;
    }

    public void setMonthDate(Date monthDate){
        calendar.setTime(monthDate);
        curDate = calendar.getTime();
        requestLayout();
        invalidate();
    }

    //上一月
    public String clickLeftMonth() {
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, -1);
        curDate = calendar.getTime();
        invalidate();
        return getYearAndmonth();
    }

    //下一月
    public String clickRightMonth() {
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, 1);
        curDate = calendar.getTime();
        invalidate();
        return getYearAndmonth();
    }

    //设置日历时间
    public void setCalendarData(Date date) {
        calendar.setTime(date);
        invalidate();
    }

    //获取日历时间
    public void getCalendatData() {
        calendar.getTime();
    }


    private void setSelectedDateByCoor(float x, float y) {
        // cell click down
        if (y > surface.monthHeight + surface.weekHeight) {
            int m = (int) (Math.floor(x / surface.cellWidth) + 1);
            int n = (int) (Math
                    .floor((y - (surface.monthHeight + surface.weekHeight))
                            / Float.valueOf(surface.cellHeight)) + 1);
            int downIndexCheckBefore = downIndex;
            downIndex = (n - 1) * 7 + m - 1;
            if (!isValidaClick() || !isClickOnSportDate()) {
                downIndex = downIndexCheckBefore;
                return;
            }
            calendar.setTime(curDate);
            if (isLastMonth(downIndex)) {
                calendar.add(Calendar.MONTH, -1);
            } else if (isNextMonth(downIndex)) {
                calendar.add(Calendar.MONTH, 1);
            }
            int reduce = 0;
            if (date[6] > 28) {
                reduce = 7;
            }
            calendar.set(Calendar.DAY_OF_MONTH, date[downIndex + reduce]);
            downDate = calendar.getTime();
        }
        invalidate();
    }

    private boolean isValidaClick() {
        int reduce = 0;
        if (date[6] > 28) {
            reduce = 7;
        }
        if (downIndex + reduce >= curStartIndex && downIndex + reduce < curEndIndex) {
            return true;
        }
        return false;
    }

    private boolean isClickOnSportDate() {
        if (sportDates == null || sportDates.size() == 0) {
            return false;
        } else {
            return sportDates.contains(downIndex - curStartIndex + 1);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelectedDateByCoor(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (downDate != null) {

                    if (!isValidaClick() || !isClickOnSportDate()) {
                        break;
                    }
                    selectedStartDate = selectedEndDate = downDate;
                    //响应监听事件
                    onItemClickListener.OnItemClick(selectedStartDate, selectedEndDate, downDate);
                    invalidate();
                }

                break;
        }
        return true;
    }

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate);
    }

    /**
     * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
     */
    private class Surface {
        public float density;
        public int width; // 整个控件的宽度
        public int height; // 整个控件的高度
        public float monthHeight; // 显示月的高度
        public float weekHeight; // 显示星期的高度
        public float cellWidth; // 日期方框宽度
        public float cellHeight; // 日期方框高度
        public int bgColor = Color.BLACK;
        private int textColor = Color.WHITE;
        private int borderColor = Color.BLACK;
        public int todayNumberColor = Color.RED;
        public int cellSelectedColor = Color.BLACK;
        public Paint monthPaint;
        public Paint weekPaint;
        public Paint datePaint;
        public Paint cellBgPaint;
        public String[] weekText = {"日", "一", "二", "三", "四", "五", "六"};

        public void init() {
            getShowWeeks();
            float temp = height / 7;
            monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);
            weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
            cellHeight = (height - monthHeight - weekHeight) / (float) showWeeks;
            cellWidth = width / 7f;
            monthPaint = new Paint();
            monthPaint.setColor(textColor);
            monthPaint.setAntiAlias(true);
            float textSize = cellHeight * 0.4f;
            Log.d(TAG, "text size:" + textSize);
            monthPaint.setTextSize(textSize);
            monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
            weekPaint = new Paint();
            weekPaint.setColor(textColor);
            weekPaint.setAntiAlias(true);
            float weekTextSize = weekHeight * 0.6f;
            weekPaint.setTextSize(weekTextSize);
            weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
            datePaint = new Paint();
            datePaint.setColor(textColor);
            datePaint.setAntiAlias(true);
            float cellTextSize = cellHeight * 0.5f;
            datePaint.setTextSize(cellTextSize);
            datePaint.setTypeface(Typeface.DEFAULT_BOLD);
            cellBgPaint = new Paint();
            cellBgPaint.setAntiAlias(true);
            cellBgPaint.setStyle(Paint.Style.FILL);
            cellBgPaint.setColor(cellSelectedColor);
        }
    }
}
