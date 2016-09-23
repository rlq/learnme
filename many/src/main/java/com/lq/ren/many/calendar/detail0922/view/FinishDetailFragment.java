package com.lq.ren.many.calendar.detail0922.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.ren.many.calendar.detail0922.Observable;
import com.lq.ren.many.calendar.detail0922.data.Detail;
import com.lq.ren.many.calendar.detail0922.model.DetailModel;
import com.lq.ren.many.calendar.detail0922.model.FinishDetailModel;

/**
 * Author lqren on 16/9/21.
 */
public class FinishDetailFragment extends DetailFragment<Detail> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected DetailModel<Detail> createViewModel() {
        return new FinishDetailModel();
    }

    @Override
    public FinishDetailModel getViewModel() {
        return (FinishDetailModel)super.getViewModel();
    }

    @Override
    public void update(Observable<Detail> observer, Detail data) {

    }
}
