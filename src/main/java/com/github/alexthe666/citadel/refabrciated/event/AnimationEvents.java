package com.github.alexthe666.citadel.refabrciated.event;

import com.github.alexthe666.citadel.animation.AnimationEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface AnimationEvents {
    Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.post(event);
            if (event.isCanceled()) return;
        }
    });

    @FunctionalInterface
    interface Start {
        void post(AnimationEvent.Start<?> event);
    }

    Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.post(event);
        }
    });

    @FunctionalInterface
    interface Tick {
        void post(AnimationEvent.Tick<?> event);
    }

}
