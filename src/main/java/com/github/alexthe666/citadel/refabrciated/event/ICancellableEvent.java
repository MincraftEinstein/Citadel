package com.github.alexthe666.citadel.refabrciated.event;

public interface ICancellableEvent {
    void setCanceled(boolean canceled);

    boolean isCanceled();
}

