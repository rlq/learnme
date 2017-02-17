package com.lq.ren.many.learn.obser;

/**
 * Author lqren on 17/2/4.
 */
public interface Observer<T> {

    void update(Observable<T> observable, T data);
}
