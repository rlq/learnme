package com.lq.ren.many.calendar.compare170222;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Author lqren on 17/2/28.
 */
public class SDCardUtil0228 {

    private static final long SD_CARD_SAVE_MIN_SIZE = 1024 * 1024 * 50;

    public static long getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return blockSize * totalBlocks;
    }

    public static long getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return blockSize * availableBlocks;
    }

    public static boolean isSDCardCanSaveApk() {
        return SD_CARD_SAVE_MIN_SIZE < getSDAvailableSize();
    }
}
