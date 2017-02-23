package com.lq.ren.many.calendar.compare170222;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author lqren on 17/2/22.
 */
public class CompareUtil {


    // > 0 new ; < 0 old ; = 0 eq
    public static int compareName(@Nullable String name1, @Nullable String name2) {
        if (name1 == null) name1 = "";
        if (name2 == null) name2 = "";
        final Pattern versionNamePattern = Pattern.compile("^([Hh]emu_)?(\\d+(\\.\\d+){2,3})(_intl)?(_[ab]\\d+)?$");
        Matcher matcher = versionNamePattern.matcher(name1);
        String lRawVersion = "";
        if (matcher.find()) {
            lRawVersion = matcher.group(2);
        }
        matcher = versionNamePattern.matcher(name2);
        String rRawVersion = "";
        if (matcher.find()) {
            rRawVersion = matcher.group(2);
        }
        return compareResult(lRawVersion, rRawVersion);
    }

    public static int compareResult(String name1, String name2) {
        int[] oldNames = parseName(name1);
        int[] newNames = parseName(name2);
        int len = Math.max(oldNames.length, newNames.length);
        for (int i = 0; i < len; i++) {
//            if (oldNames[i] < newNames[i]) return 1;
//            else if (oldNames[i] > newNames[i]) return -1;

            int v1 = i < oldNames.length ? oldNames[i] : 0;
            int v2 = i < newNames.length ? newNames[i] : 0;

            if (v1 < v2) return -1;
            else if (v1 > v2) return 1;
        }
//        int r = oldNames.length - newNames.length;
//        if (r != 0) {
//            r = r > 0 ? 1 : -1;
//        }
        return 0;
    }

    public static int[] parseName(String name) {
        String[] nameStr = name.split("\\.");
        int[] names = new int[nameStr.length];
        for (int i = 0; i < nameStr.length; i++) {
            try {
                names[i] = Integer.parseInt(nameStr[i]);
            } catch (NumberFormatException e) {
                names[i] = 0;
            }
        }
        return names;

    }
}
