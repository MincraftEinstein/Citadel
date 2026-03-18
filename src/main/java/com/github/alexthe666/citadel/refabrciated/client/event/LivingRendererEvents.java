package com.github.alexthe666.citadel.refabrciated.client.event;

import com.github.alexthe666.citadel.client.event.EventLivingRenderer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface LivingRendererEvents {
    Event<SetupRotations> SETUP_ROTATIONS = EventFactory.createArrayBacked(SetupRotations.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.post(event);
        }
    });

    @FunctionalInterface
    interface SetupRotations {
        void post(EventLivingRenderer.SetupRotations event);
    }

    interface AccessToBufferSourceEvents {
        Event<AccessToBufferSourceEvents.PreSetupAnimations> PRE_ANIM = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PreSetupAnimations.class, callbacks -> event -> {
            for (var callback : callbacks) {
                callback.post(event);
            }
        });

        @FunctionalInterface
        interface PreSetupAnimations {
            void post(EventLivingRenderer.PreSetupAnimations event);
        }

        Event<AccessToBufferSourceEvents.PostSetupAnimations> POST_ANIM = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PostSetupAnimations.class, callbacks -> event -> {
            for (var callback : callbacks) {
                callback.post(event);
            }
        });

        @FunctionalInterface
        interface PostSetupAnimations {
            void post(EventLivingRenderer.PostSetupAnimations event);
        }

        Event<AccessToBufferSourceEvents.PostRenderModel> POST_RENDER_MODEL = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PostRenderModel.class, callbacks -> event -> {
            for (var callback : callbacks) {
                callback.post(event);
            }
        });

        @FunctionalInterface
        interface PostRenderModel {
            void post(EventLivingRenderer.PostRenderModel event);
        }

    }
}
