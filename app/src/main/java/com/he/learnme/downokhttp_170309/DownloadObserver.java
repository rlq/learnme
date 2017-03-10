package com.he.learnme.downokhttp_170309;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author lqren on 17/3/9.
 */

public abstract class DownloadObserver implements Observer<DownloadInfo> {
    protected Disposable disposable; //取消注册的监听者
    protected DownloadInfo info;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(DownloadInfo value) {
        info = value;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
