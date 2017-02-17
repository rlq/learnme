package com.lq.ren.many.learn.obser;

import android.support.annotation.NonNull;

/**
 * Author lqren on 17/2/4.
 */
public class Observation<T> implements Registration {

    private Observable<T> observable;
    private Observer<T> observer;

    public static <T> Observation<T> create(@NonNull Observable<T> observable,
                                            @NonNull Observer<T> observer) {
        return new Observation<T>().set(observable, observer);
    }

    public Observation<T> set(@NonNull Observable<T> observable, @NonNull Observer<T> observer) {
        this.observable = observable;
        this.observer = observer;
        this.observable.addObserver(this.observer);
        return this;
    }

    @Override
    public boolean isCleared() {
        return observable == null || observer == null;
    }

    @Override
    public void clear() {
        observable.deleteObserver(observer);
        observable = null;
        observer = null;
    }
}
