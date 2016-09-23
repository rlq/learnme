package com.lq.ren.many.calendar.detail0922.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lq.ren.many.calendar.detail0922.Observable;
import com.lq.ren.many.calendar.detail0922.Observer;
import com.lq.ren.many.calendar.detail0922.model.DetailModel;

/**
 * Author lqren on 16/9/21.
 */
public abstract class DetailView<Details> extends FrameLayout
        implements Observer<Details> {

    private Observable<Details> detailsObservable;

    public DetailView(Context context) {
        super(context);
    }

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setViewModel(DetailModel<Details> model) {
        //Un-subscribe first
        if (detailsObservable != null) {
            detailsObservable.deleteObserver(this);
            detailsObservable = null;
        }

        if (model != null) {
            detailsObservable = model.getDetailObservable();
            detailsObservable.addObserver(this);
            model.requestDetail();
        }
    }
}
