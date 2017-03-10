package com.he.learnme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.he.learnme.scroll_170301.RecycleViewScrollbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this, TTS121Activity.class));

        //1214 down install apk
        //new UpdateManager(this).showNoticeDialog();

        //2017.03.02 recyclerView scrollbar
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new RecycleViewScrollbar())
                    .commitAllowingStateLoss();
        }
    }
}
