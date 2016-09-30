package com.lq.ren.fitness;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.ren.transitions.R;

/**
 * Author lqren on 16/9/30.
 */
public class ContentActivity extends AppCompatActivity {

    TextView textTitle;
    ImageView imageAvatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        imageAvatar = (ImageView) findViewById(R.id.image);
        textTitle = (TextView) findViewById(R.id.title);

        if (getIntent() != null) {
            int avatar = getIntent().getIntExtra(getString(R.string.transition_shared_avatar), 0);
            String title = getIntent().getStringExtra(getString(R.string.transition_shared_title));

            if (avatar > 0) {
                imageAvatar.setImageResource(avatar);
                //colorize(((BitmapDrawable) imageAvatar.getDrawable()).getBitmap());
            }
            if (title != null) {
                textTitle.setText(title);
            }

            Transition transition =
                    TransitionInflater.from(this).inflateTransition(R.transition.slide_bottom);
            getWindow().setEnterTransition(transition);
        }
    }
}
