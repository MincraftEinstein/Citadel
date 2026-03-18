package com.github.alexthe666.citadel.refabrciated.event;

import com.github.alexthe666.citadel.animation.AnimationEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface AnimationEvents {
    Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.isCanceled()) return;
        }
    });

    @FunctionalInterface
    interface Start {
        void event(AnimationEvent.Start<?> event);
    }

    Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
        }
    });

    @FunctionalInterface
    interface Tick {
        void event(AnimationEvent.Tick<?> event);
    }

}
