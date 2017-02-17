package com.lq.ren.many.learn.obser;

/**
 * Author lqren on 17/2/4.
 */
public class BehaviorObservable<T> extends Observable<T> {

    private T lastValue;
    private boolean hasLastValue = false;

    @Override
    public void notifyObservers() {
        boolean hasLast;
        T last;
        synchronized (this) {
            hasLast = hasLastValue;
            last = lastValue;
        }
        if (hasLast) {
            setChanged();
            super.notifyObservers();
        }
    }

    @Override
    public void notifyObservers(T data) {
        synchronized (this) {
            hasLastValue = true;
            lastValue = data;
        }
        setChanged();
        super.notifyObservers(data);
    }

    @Override
    public void addObserver(Observer<T> observer) {
        super.addObserver(observer);

        if (observer != null) {
            boolean hasLast;
            T last;
            synchronized (this) {
                hasLast = hasLastValue;
                last = lastValue;
            }
            if (hasLast) {
                observer.update(this, last);
            }
        }
    }
}
