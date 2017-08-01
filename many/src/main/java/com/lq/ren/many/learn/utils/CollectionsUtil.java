package com.lq.ren.many.learn.utils;

import java.util.Collection;

public class CollectionsUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int size(Collection<?> collection) {
        return collection != null ? collection.size() : 0;
    }

}
