package com.lq.ren.many.learn.mvp;

import com.lq.ren.many.learn.obser.RegistrationHub;

/**
 * Author lqren on 17/2/4.
 * The view model dispatch the view change event to underlying data store.
 * And transform the data store change to view properties.
 */
abstract public class MVPViewModel {

    private RegistrationHub registrationHub;

    public final void subscribeToDataStore() {
        unsubscribeFromDataStore();
        registrationHub = new RegistrationHub();
        subscribeToDataStoreInternal(registrationHub);
    }

    public void dispose() {
        if (registrationHub != null) {
            unsubscribeFromDataStore();
        }
    }

    protected abstract void subscribeToDataStoreInternal(RegistrationHub registrationHub);

    public void unsubscribeFromDataStore() {
        if (registrationHub != null) {
            registrationHub.clear();
            registrationHub = null;
        }
    }
}
