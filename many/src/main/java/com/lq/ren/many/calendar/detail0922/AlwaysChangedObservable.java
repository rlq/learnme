package com.lq.ren.many.calendar.detail0922;

/**
 * Author lqren on 16/9/21.
 */
public class AlwaysChangedObservable<T> extends Observable<T> {

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        setChanged();
    }

    @Override
    public void notifyObservers(T data) {
        super.notifyObservers(data);
        setChanged();
    }
}
