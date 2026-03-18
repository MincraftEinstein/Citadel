package com.github.alexthe666.citadel.refabrciated.client.event;

import com.github.alexthe666.citadel.client.event.EventGetStarBrightness;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;

public interface CitadelClientEvents {
    Event<StarBrightness> STAR_BRIGHTNESS = EventFactory.createArrayBacked(StarBrightness.class, callbacks -> event -> {
        for (StarBrightness callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface StarBrightness {
        void event(EventGetStarBrightness event);
    }




}
