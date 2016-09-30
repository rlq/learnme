package com.example.fragmenttransitions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.fragmenttransitions.fitness.FirstFragment;

/**
 * Main activity that holds our fragments
 *
 * @author bherbst
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new FirstFragment())
                    //.add(R.id.container, new GridFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }
}
