package com.lq.ren.many.learn.mvp.Presenter;

import com.lq.ren.many.learn.mvp.MVPViewModel;
import com.lq.ren.many.learn.obser.BehaviorObservable;
import com.lq.ren.many.learn.obser.Observable;
import com.lq.ren.many.learn.obser.Observation;
import com.lq.ren.many.learn.obser.Observer;
import com.lq.ren.many.learn.obser.RegistrationHub;
import com.lq.ren.many.learn.obser.Registrations;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 17/2/4.
 */
public class MainViewModel extends MVPViewModel {

    protected final BehaviorObservable<List<Integer>> countObservable = new BehaviorObservable<>();

    public Observable<List<Integer>> getCountChange() {
        return countObservable;
    }


    @Override
    protected void subscribeToDataStoreInternal(RegistrationHub registrationHub) {
        getCountChange().notifyObservers(generateData());
        //or
        registrationHub.add(Observation.create(getCountChange(),
                new Observer<List<Integer>>() {
                    @Override
                    public void update(Observable<List<Integer>> observable, List<Integer> data) {

                    }
                })
        );
        //or
        registrationHub.add(Registrations.create(new Runnable() {
            @Override
            public void run() {
                //TODO sync
            }
        }));

    }

    private List<Integer> generateData() {
        List<Integer> datas = new ArrayList<>();
        datas.add(1);
        datas.add(2);
        datas.add(3);
        return datas;
    }
}
