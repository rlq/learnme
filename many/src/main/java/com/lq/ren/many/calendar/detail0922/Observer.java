package com.lq.ren.many.calendar.detail0922;

/**
 * Author lqren on 16/9/21.
 */
public interface Observer<T> {

    void update(Observable<T> observer, T data);
}
