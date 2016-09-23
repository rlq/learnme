package com.lq.ren.many.calendar.view;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;


import com.lq.ren.many.R;

import java.util.Calendar;


/**
 * Created by lqren on 16/7/30.
 */
public class TextTime extends TextView {

    public static final CharSequence _12hour = "h:mm a";
    public static final CharSequence _24hour = "H:mm";

    private CharSequence mFormat12;
    private CharSequence mFormat24;
    private CharSequence mFormat;
    private String mContentDescriptionFormat;

    private boolean mAttached;

    private int mHour;
    private int mMinutes;


    public TextTime(Context context) {
        this(context, null);
    }

    public TextTime(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public TextTime(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        final TypedArray styleAttrs =  context.obtainStyledAttributes(attrs,
                R.styleable.SwipeTodoView, defStyle, 0);

        mFormat12 = "";
        mFormat24 = ""; // styleAttrs.getText(R.sty);
        styleAttrs.recycle();
    }

    public CharSequence getFormat12(){
        return mFormat12;
    }

    public void setFormat12(CharSequence format12){
        mFormat12 = format12;
        chooseFormat();
        updateTime();
    }

    public CharSequence getFormat24(){
        return mFormat24;
    }

    public void setFormat24(CharSequence format24){
        mFormat24 = format24;
        chooseFormat();
        updateTime();
    }

    private void chooseFormat(){
        final boolean format24Requested = DateFormat.is24HourFormat(getContext());
        if(format24Requested){
            mFormat = (mFormat24 == null)? _24hour : mFormat24;
        }else{
            mFormat12 = (mFormat12 == null)? _12hour : mFormat12;
        }
        mContentDescriptionFormat = mFormat.toString();
    }

    private void setTime(int hour, int minutes){
        mHour = hour;
        mMinutes = minutes;
        updateTime();
    }

    public void updateTime(){
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mHour);
        calendar.set(Calendar.MINUTE, mMinutes);
        setText(DateFormat.format(mFormat, calendar));
        if(mContentDescriptionFormat != null){
            setContentDescription(DateFormat.format(mContentDescriptionFormat, calendar));
        }else{
            setContentDescription(DateFormat.format(mFormat,calendar));
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mAttached){
            mAttached = true;
            registerObserver();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mAttached){
            unRegisterObserver();
            mAttached = false;
        }
    }

    private final ContentObserver mFormatChangeObserver = new ContentObserver(
            new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            chooseFormat();
            updateTime();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            chooseFormat();
            updateTime();
        }
    };


    private void registerObserver(){
        final ContentResolver resolver = getContext().getContentResolver();
        resolver.registerContentObserver(Settings.System.CONTENT_URI,
                true, mFormatChangeObserver);
    }

    private void unRegisterObserver(){
        final ContentResolver resolver = getContext().getContentResolver();
        resolver.unregisterContentObserver(mFormatChangeObserver);
    }
}
