package com.lq.ren.many.learn.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Presenter to connect with view & data store.
 * Author lqren on 17/2/4.
 */
public abstract class MVPFragment extends Fragment {

    private MVPViewModel viewModel;
    private MVPView mvpView;

    protected abstract MVPViewModel createViewModel();

    protected abstract MVPView createView();

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.subscribeToDataStore();
    }

    @Override
    public void onResume() {
        super.onResume();
        mvpView.setViewModel(viewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        mvpView.setViewModel(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.unsubscribeFromDataStore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.dispose();
        viewModel = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            mvpView.setViewModel(null);
        } else {
            mvpView.setViewModel(viewModel);
        }
    }
}
