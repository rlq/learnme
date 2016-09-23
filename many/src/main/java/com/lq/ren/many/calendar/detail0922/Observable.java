package com.lq.ren.many.calendar.detail0922;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 16/9/21.
 */
public class Observable<T> {
    List<Observer<T>> observers = new ArrayList<>();

    boolean changed = false;

    public Observable() {

    }

    public void addObserver(Observer<T> observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (observers.contains(observer)) {
                observers.add(observer);
            }
        }
    }

    public void clearChanged() {
        changed = false;
    }

    public int observersCount() {
        return observers.size();
    }

    public synchronized void deleteObserver(Observer<T> observer) {
        observers.remove(observer);
    }

    public synchronized void deleteObservers() {
        observers.clear();
    }

    public boolean hasChanged() {
        return changed;
    }

    public void notifyObservers() {
        notifyObservers();
    }

    protected void setChanged() {
        changed = true;
    }

    @SuppressWarnings("unused")
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
            for (Observer<T> observers : arrays) {
                observers.update(this, data);
            }
        }
    }
}
