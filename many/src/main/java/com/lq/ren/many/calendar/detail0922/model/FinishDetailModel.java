package com.lq.ren.many.calendar.detail0922.model;


import com.lq.ren.many.calendar.detail0922.data.DataSession;
import com.lq.ren.many.calendar.detail0922.data.Detail;

/**
 * Author lqren on 16/9/21.
 */
public class FinishDetailModel extends DetailModel<Detail> {

    @Override
    protected Detail translateSession(DataSession session) {
        return null;
    }

    @Override
    public synchronized void unsubscribeToDataStore() {
        super.unsubscribeToDataStore();
    }

    private Detail converSession(DataSession session) {
        Detail detail = new Detail();
        detail.id = session.data.id;
        detail.name = session.data.name;
        return detail;
    }
}
