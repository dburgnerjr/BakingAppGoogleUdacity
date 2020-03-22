package com.danielburgnerjr.bakingapp.IdlingResource;

import androidx.annotation.NonNull;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.requireNonNull;

public class SimpleIdlingResource implements IdlingResource {

    @NonNull
    private final ThreadLocal<ResourceCallback> mCallback = new ThreadLocal<>();

    // Idleness is controlled with this boolean.
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback.set(callback);
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow) {
            if (mCallback.get() != null)
                requireNonNull(mCallback).get().onTransitionToIdle();
        }
    }
}
