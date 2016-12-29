package com.he.learnme.d3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Author lqren on 16/11/21.
 */
public class BufferUtil {

    public static IntBuffer intBuffer;
    public static FloatBuffer floatBuffer;

    public static IntBuffer iBuffer(int[] a) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(a.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());//排列
        intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(a);
        intBuffer.position();
        return intBuffer;
    }

    public static FloatBuffer fBuffer(float[] a) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(a.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());//排列
        floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(a);
        floatBuffer.position();
        return floatBuffer;
    }
}
