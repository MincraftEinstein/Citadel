package com.github.alexthe666.citadel.refabrciated.event;

public interface ICancellableEvent {
    default void setCanceled(boolean canceled) {
        ((Event) this).isCanceled = canceled;
    }

    default boolean isCanceled() {
        return ((Event) this).isCanceled;
    }
}

