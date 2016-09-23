package com.lq.ren.many.learn.course;

import java.util.List;

public class StepItem {
    public long time; //Second
    public long sumDuration; //Second
    public int sumstep;
    public int displayValue;
    public int sumCal;
    public int sumDistance;
    public int days;
    public long avgDuration;
    public int avgSteps;
    public int avgCal;
    public int avgDistance;

    public StepItem() {
        this.time = 0;
    }

    public StepItem(long time) {
        this.time = time;
    }

    public StepItem(long time, int step) {
        this.time = time;
        this.sumstep = step;
    }

    public StepItem(long time, int step, int distance, long duration, int cal) {
        this.time = time;
        this.sumstep = step;
        this.sumDuration = duration;
        this.sumDistance = distance;
        this.sumCal = cal;
    }

    public StepItem(List<StepItem> list, int start, int cnt) {
        int end = Math.min(list.size(), start + cnt);
        this.time = list.get(end - 1).time;
        for (int i = start; i < end; i++) {
            StepItem item = list.get(i);
            this.sumstep += item.sumstep;
            this.sumDistance += item.sumDistance;
            this.sumDuration += item.sumDuration;
            this.sumCal += item.sumCal;
        }
        cnt = end - start;
        if (cnt > 0) {
            this.avgCal = this.sumCal / cnt;
            this.avgDuration = this.sumDuration / cnt;
            this.avgSteps = this.sumstep / cnt;
            this.avgDistance = this.sumDistance / cnt;
        }
    }
}
