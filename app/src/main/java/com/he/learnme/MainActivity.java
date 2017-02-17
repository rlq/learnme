package com.he.learnme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.he.learnme.downinstall1214.UpdateManager;

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
