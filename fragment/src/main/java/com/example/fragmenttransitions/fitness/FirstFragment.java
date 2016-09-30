package com.example.fragmenttransitions.fitness;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmenttransitions.DetailsFragment;
import com.example.fragmenttransitions.R;

/**
 * Display details for a given kitten
 *
 * @author bherbst
 */
public class FirstFragment extends Fragment {

    private CircularProgressView progressView;
    private TimeTicker timer;
    private int count = 1;

    private View icon;
    private TextView gps;
    private View next;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fitness_progress, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);
        this.view = view;
        progressView = (CircularProgressView) view.findViewById(R.id.circle_progress);
        //initTimer();
        initView();
    }

    //929 activity animation
    private void initView() {
        icon = view.findViewById(R.id.item_icon);
        gps = (TextView) view.findViewById(R.id.item_gps);
        next = view.findViewById(R.id.item_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps.setText("gps定位失败\n请重新连接");
                openDetail();
            }
        });
    }

    private void openDetail() {

        /*Intent intent = new Intent(ProgressActivity.this, ContentActivity.class);
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(
                        this,
                        Pair.create(icon, getString(R.string.transition_shared_avatar)),
                        Pair.create((View)gps, getString(R.string.transition_shared_title)));

        startActivity(intent, transitionActivity.toBundle());
        //finish();*/
        ViewCompat.setTransitionName(next, String.valueOf(2) + "_image");

        DetailsFragment kittenDetails = DetailsFragment.newInstance(2);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(next, "kittenImage")
                .replace(R.id.container, kittenDetails)
                .addToBackStack(null)
                .commit();

    }

    private void initTimer() {
        timer = new TimeTicker();
        timer.setCallback(runnable);
        timer.start(1000);
    }

    private void updateProgress(float cur) {
        if (cur >= 60) {
            gps.setText("gps定位失败\n请重新连接");
        }
        Log.i("HEHE", "update cur " + cur);
        progressView.setProgress(cur / 60 * 360f); //这里需* 360f
//        progressView.setColor(getResources().getColor(R.color.b));
    }

    private void cancel() {
        timer.stop();
        timer = null;
        runnable = null;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (timer != null) {
                if (count < 63) {
                    updateProgress(++count);
                } else {
                    cancel();
                    openDetail();
                }
            }
        }
    };
}
