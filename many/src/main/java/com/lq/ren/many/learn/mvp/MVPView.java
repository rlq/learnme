package com.lq.ren.many.learn.mvp;

/**
 * Author lqren on 17/2/4.
 * A view interface for MVP architecture.
 *
 * Remember, the View is just an interface to receive data changes for display, and
 * do actions to change the data. You can implement MVPView by Android's View,
 * Fragment, Activity or anything else.
 */
public interface MVPView<T extends MVPViewModel> {

    void setViewModel(T viewModel);
}
