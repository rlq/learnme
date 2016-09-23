package com.lq.ren.many.calendar.detail0922.data;


import com.lq.ren.many.calendar.detail0922.Observable;

/**
 * Author lqren on 16/9/21.
 * 这是获取数据的桥梁
 */
public class DataSession extends Observable<DataSession> {

    public Data data;

    public class Data {
        public int id;
        public String name;
    }
}
