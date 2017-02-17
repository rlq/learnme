package com.lq.ren.many.learn.obser;

/**
 * Author lqren on 17/2/4.
 */
public class BooleanRegistration implements Registration {

    private final Runnable clearAction;
    private boolean cleared = false;

    public BooleanRegistration(Runnable clearAction) {
        this.clearAction = clearAction;
    }

    @Override
    public boolean isCleared() {
        return cleared;
    }

    @Override
    public void clear() {
        cleared = true;
        if (clearAction != null) {
            clearAction.run();
        }
    }
}
