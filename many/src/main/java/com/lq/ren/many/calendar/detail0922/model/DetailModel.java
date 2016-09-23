package com.lq.ren.many.calendar.detail0922.model;

import android.os.Handler;
import android.os.Looper;

import com.lq.ren.many.calendar.detail0922.AlwaysChangedObservable;
import com.lq.ren.many.calendar.detail0922.Observable;
import com.lq.ren.many.calendar.detail0922.Observer;
import com.lq.ren.many.calendar.detail0922.data.DataSession;

/**
 * Author lqren on 16/9/21.
 */
public abstract class DetailModel<T> {

    private Observable<DataSession> dataObservable;
    private Observable<T> detailObservable = new AlwaysChangedObservable<>();

    protected Handler uiHandler;
    protected T curDetail;

    public DetailModel() {
        uiHandler = new Handler(Looper.getMainLooper());
    }

    private Observer<DataSession> sessionObserver = new Observer<DataSession>() {
        @Override
        public void update(Observable<DataSession> observer, DataSession data) {
            final T detail = translateSession(data);
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    onDetailUpdate(detail);
                }
            });
        }
    };

    protected void onDetailUpdate (T detail) {
        curDetail = detail;
        //requestDetail();
    }

    public void requestDetail() {
        detailObservable.notifyObservers(curDetail);
    }

    public Observable<T> getDetailObservable() {
        return this.detailObservable;
    }

    public void subscribeToDataStore(final String sportId) {

    }

    public synchronized void unsubscribeToDataStore() {
        if (dataObservable != null) {
            dataObservable.deleteObserver(sessionObserver);
        }
    }

    protected synchronized DataSession subecribeFromDataStoreInternal(String sportId) {
        DataSession session = null;//这里用到SportStore
        return session;
    }


    //call from worker thread
    abstract protected T translateSession(DataSession session);
}
