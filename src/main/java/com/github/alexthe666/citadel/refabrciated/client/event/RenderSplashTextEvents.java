package com.github.alexthe666.citadel.refabrciated.client.event;

import com.github.alexthe666.citadel.client.event.EventRenderSplashText;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;

public interface RenderSplashTextEvents {
    Event<RenderSplashTextPre> PRE = EventFactory.createArrayBacked(RenderSplashTextPre.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface RenderSplashTextPre {
        void event(EventRenderSplashText.Pre event);
    }

    Event<RenderSplashTextPost> POST = EventFactory.createArrayBacked(RenderSplashTextPost.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
        }
    });

    @FunctionalInterface
    interface RenderSplashTextPost {
        void event(EventRenderSplashText.Post event);
    }
}
