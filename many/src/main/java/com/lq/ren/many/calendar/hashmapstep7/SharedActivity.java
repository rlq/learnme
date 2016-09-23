package com.lq.ren.many.calendar.hashmapstep7;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lq.ren.many.R;


/**
 * Created by lqren on 16/9/7.
 * 这是SharedPreferences 测试通过
 */
public class SharedActivity extends Activity {

    private EditText alarmId;
    private EditText singerTime;
    private Button commit ;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String id;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_share_step7);

        sharedPreferences = getSharedPreferences("alarm", 0);
        editor = sharedPreferences.edit();
        alarmId = (EditText) findViewById(R.id.alarmId);
        singerTime = (EditText) findViewById(R.id.singerTime);


        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = alarmId.getText().toString();
                time = singerTime.getText().toString();
                editor.putString("alarmId", id);
                editor.putString("singerTime", time);
                editor.commit();

            }
        });

        findViewById(R.id.getString).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getText();
            }
        });

    }

    private void getText() {
        String str = "alarmId :" + sharedPreferences.getString("alarmId", "null") +
                ", singerTime : " + sharedPreferences.getString("singerTime", "null");
        TextView getData = (TextView) findViewById(R.id.getdata);
        getData.setText(str);
    }


}
