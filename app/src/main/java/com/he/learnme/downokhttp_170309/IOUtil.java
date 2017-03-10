package com.he.learnme.downokhttp_170309;

import java.io.Closeable;
import java.io.IOException;

/**
 * Author lqren on 17/3/9.
 */

public class IOUtil {

    public static void closeAll(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
