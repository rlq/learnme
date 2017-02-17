package com.lq.ren.many.learn.obser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 17/2/4.
 */
public class Observable<T> {

    List<Observer<T>> observers = new ArrayList<>();

    boolean changed = false;

    public Observable() {}

    public void addObserver(Observer<T> observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    public void deleteObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public void deleteObservers() {
        observers.clear();
    }

    public int countObservers() {
        return observers.size();
    }

    protected void clearChanged() {
        changed = false;
    }

    public void setChanged() {
        changed = true;
    }

    public boolean hasChanged() {
        return changed;
    }

    @SuppressWarnings("unchecked")
    public void notifyObservers(T data) {
        int size;
        Observer<T>[] arrays = null;
        synchronized (this) {
            if (hasChanged()) {
                clearChanged();
                size = observers.size();
                arrays = new Observer[size];
                observers.toArray(arrays);
            }
        }
        if (arrays != null) {
            for (Observer<T> observer : arrays) {
                observer.update(this, data);
            }
        }
    }

    public void notifyObservers() {
        notifyObservers(null);
    }


}
