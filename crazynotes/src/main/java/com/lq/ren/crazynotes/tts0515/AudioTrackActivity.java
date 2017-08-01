package com.lq.ren.crazynotes.tts0515;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;

import com.lq.ren.crazynotes.R;

import java.io.DataInputStream;
import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

/**
 * Author lqren on 17/5/24.
 */

public class AudioTrackActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int sampleRate = 16000;

        InputStream in = null;
        DataInputStream mStream = null;
        final int minBufferSize = AudioTrack.getMinBufferSize(sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        byte[] mBuffer = new byte[minBufferSize * 10];
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize, AudioTrack.MODE_STREAM);
        audioTrack.play();
        try {
            in = getResources().openRawResource(R.raw.begin2);

            /**
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
            byte[] audioData = out.toByteArray();

            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    audioData.length,
                    AudioTrack.MODE_STATIC);
            mAudioTrack.write(audioData, 0, audioData.length);
            mAudioTrack.play();
             **/

            mStream = new DataInputStream(in);
            long i = mStream.read(mBuffer, 0, mBuffer.length);
            Log.e("HEHE", "create i=" + i);

            audioTrack.write(mBuffer, 0, (int) i);
//            audioTrack.write(new byte[minBufferSize], 0, minBufferSize);

//            if (i == -1) {
//                audioTrack.write(new byte[minBufferSize], 0, minBufferSize);
//                Log.e("HEHE", "create == -1");
//            } else {
//                audioTrack.write(mBuffer, 0, (int) i);
//                Log.e("HEHE", "create ！= -1");
//            }

//            audioTrack.play();
            Log.d("HEHE", "playing");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
