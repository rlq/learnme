package com.lq.ren.many.learn.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.lq.ren.many.CustomActivity;
import com.lq.ren.many.R;

/**
 * Created by lqren on 16/7/26.
 */
public class TimeSetActivity extends Activity {

    private TimePicker mTimePicker;
    private int mMinute;
    private int mHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HEHE", "time oncreate");
        setContentView(R.layout.custom_setalarm);

        mTimePicker = (TimePicker) findViewById(R.id.tp_set_alarm_add);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mMinute = minute;
                mHours = hourOfDay;
            }
        });

    }

    public void save(View view){
        Intent i = new Intent(this, CustomActivity.class);
        i.putExtra("Minute", mMinute);
        i.putExtra("Hour", mHours);
        Log.v("HEHE", "hour "+ mHours + "  minute "+ mMinute);

        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
