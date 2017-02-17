package com.lq.ren.many.learn.obser;

/**
 * Author lqren on 17/2/4.
 */
public class Registrations {

    public static Registration create(final Runnable runnable) {
        return new BooleanRegistration(runnable);
    }
}
