package com.lq.ren.many.calendar.hashmapstep7;

import java.io.Serializable;

/**
 * Created by lqren on 16/9/9.
 */
public class MapToFileBean implements Serializable {

    String id;
    String time;

    @Override
    public String toString() {
        return "Alarm{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
