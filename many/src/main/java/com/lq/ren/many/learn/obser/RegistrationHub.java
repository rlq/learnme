package com.lq.ren.many.learn.obser;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 17/2/4.
 */
public class RegistrationHub {

    private final List<Registration> registrations = new ArrayList<>();

    public void add(@NonNull Registration registration) {
        registrations.add(registration);
    }

    public void clear() {
        for (Registration registration : registrations) {
            if (!registration.isCleared()) {
                registration.clear();
            }
        }
        registrations.clear();
    }
}
