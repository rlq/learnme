package com.he.learnme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.he.learnme.downinstall.UpdateManager;
import com.he.learnme.tts1201.TTS121Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this, TTS121Activity.class));

        //1214 down install apk
        new UpdateManager(this).showNoticeDialog();
    }
}
