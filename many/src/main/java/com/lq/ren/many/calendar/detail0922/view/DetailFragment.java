package com.lq.ren.many.calendar.detail0922.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import com.lq.ren.many.calendar.detail0922.Observable;
import com.lq.ren.many.calendar.detail0922.Observer;
import com.lq.ren.many.calendar.detail0922.model.DetailModel;

/**
 * Author lqren on 16/9/21.
 */
public abstract class DetailFragment<Detail> extends Fragment implements Observer<Detail> {

    private DetailView<Detail> detailView;
    private Observable<Detail> detailObservable;
    private String sportId = null;
    private DetailModel<Detail> model;

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getSportId() {
        return this.sportId;
    }

    private boolean checkSportIdValid() {
        if (sportId == null) {
            getActivity().finish();
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkSportIdValid()) {
            return;
        }

        model = createViewModel();
        model.subscribeToDataStore(sportId);
        detailObservable = model.getDetailObservable();
        detailObservable.addObserver(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailView = (DetailView<Detail>) view;
    }

    @Override
    public void onResume() {
        super.onResume();
        detailView.setViewModel(model);
    }

    @Override
    public void onPause() {
        super.onPause();
        detailView.setViewModel(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (detailObservable != null) {
            detailObservable.deleteObserver(this);
            detailObservable = null;
        }
        if (model != null) {
            model.unsubscribeToDataStore();
            model = null;
        }
    }


    abstract protected DetailModel<Detail> createViewModel();

    public DetailModel<Detail> getViewModel() {
        return model;
    }
}
