package com.he.learnme.tts1201;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.he.learnme.R;

import java.util.Locale;

/**
 * Author lqren on 16/12/1.
 * 英文的可以识别,但中文的却不行
 */
public class TTS121Activity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        mTTS = new TextToSpeech(this, this);
        final EditText edit = (EditText) findViewById(R.id.tts_edit);
        edit.setText("hahahaha");

        Button btn = (Button) findViewById(R.id.tts_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mTTS.setPitch(1.0f);
                        mTTS.setSpeechRate(0.3f);
                        if (edit.getText().length() > 0) {
                            mTTS.speak(edit.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            mTTS.speak("Nothing to Say", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }).start();

            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result1 = mTTS.setLanguage(Locale.US);
            int result2 = mTTS.setLanguage(Locale.CHINESE);
            if (result1 == TextToSpeech.LANG_MISSING_DATA
                    || result1 == TextToSpeech.LANG_NOT_SUPPORTED
                    || result2 == TextToSpeech.LANG_MISSING_DATA
                    || result2 == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("HEHE", "数据丢失或不支持");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
    }


    /**
     * 识别
     */



}
