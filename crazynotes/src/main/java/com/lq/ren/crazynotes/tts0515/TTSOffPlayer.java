package com.lq.ren.crazynotes.tts0515;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;


import com.lq.ren.crazynotes.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 17/5/10.
 */

public class TTSOffPlayer implements AudioTrack.OnPlaybackPositionUpdateListener {

    private static final String TAG = "fit.tts.off.player";

    private static final String packagePath = "android.resource://com.lq.ren.many/";
    private Context context;
    private List<Integer> ttsMap = new ArrayList<>();
    private List<Integer> valueIds = new ArrayList<>();
    private int curIndex = 0;
    private int sportCompletion;
    private boolean cn;
    private AudioTrack at;


    public TTSOffPlayer(Context context) {
        this.context = context;
        playBegin();
    }

    private void playBuffer() {
        if (ttsMap.size() < 0 || curIndex >= ttsMap.size())
            return;

        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = context.getResources().openRawResource(ttsMap.get(curIndex));
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            byte[] audioData = out.toByteArray();
            at = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                    AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, audioData.length,
                    AudioTrack.MODE_STREAM);
            at.write(audioData, 0, audioData.length);
            at.setNotificationMarkerPosition(audioData.length / 2);
            at.setPlaybackPositionUpdateListener(this);
            at.play();
            at.flush();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "play audio buffer FileNotFoundException :", e);
        } catch (IOException e) {
            Log.e(TAG, "play audio buffer IOException :", e);
        }
    }

    @Override
    public void onMarkerReached(AudioTrack audioTrack) {
    }

    @Override
    public void onPeriodicNotification(AudioTrack audioTrack) {

    }

    public void playBegin() {
        curIndex = 0;
        ttsMap.add(R.raw.begin2);
        playBuffer();
    }

}

