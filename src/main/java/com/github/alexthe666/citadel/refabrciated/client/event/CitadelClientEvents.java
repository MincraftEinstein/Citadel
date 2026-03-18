package com.github.alexthe666.citadel.refabrciated.client.event;

import com.github.alexthe666.citadel.client.event.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;

public interface CitadelClientEvents {
    Event<StarBrightness> STAR_BRIGHTNESS = EventFactory.createArrayBacked(StarBrightness.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface StarBrightness {
        void event(EventGetStarBrightness event);
    }


    Event<PosePlayerHand> POSE_PLAYER_HAND = EventFactory.createArrayBacked(PosePlayerHand.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface PosePlayerHand {
        void event(EventPosePlayerHand event);
    }

    Event<GetOutlineColor> OUTLINE_COLOR = EventFactory.createArrayBacked(GetOutlineColor.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface GetOutlineColor {
        void event(EventGetOutlineColor event);
    }

    Event<GetFluidRenderType> FLUID_RENDER_TYPE = EventFactory.createArrayBacked(GetFluidRenderType.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface GetFluidRenderType {
        void event(EventGetFluidRenderType event);
    }

    interface RenderSplashText {
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

    interface LivingRendererEvents {
        Event<LivingRendererEvents.SetupRotations> SETUP_ROTATIONS = EventFactory.createArrayBacked(LivingRendererEvents.SetupRotations.class, callbacks -> event -> {
            for (var callback : callbacks) {
                callback.event(event);
            }
        });

        @FunctionalInterface
        interface SetupRotations {
            void event(EventLivingRenderer.SetupRotations event);
        }

        interface AccessToBufferSourceEvents {
            Event<AccessToBufferSourceEvents.PreSetupAnimations> PRE_ANIM = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PreSetupAnimations.class, callbacks -> event -> {
                for (var callback : callbacks) {
                    callback.event(event);
                }
            });

            @FunctionalInterface
            interface PreSetupAnimations {
                void event(EventLivingRenderer.PreSetupAnimations event);
            }

            Event<AccessToBufferSourceEvents.PostSetupAnimations> POST_ANIM = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PostSetupAnimations.class, callbacks -> event -> {
                for (var callback : callbacks) {
                    callback.event(event);
                }
            });

            @FunctionalInterface
            interface PostSetupAnimations {
                void event(EventLivingRenderer.PostSetupAnimations event);
            }

            Event<AccessToBufferSourceEvents.PostRenderModel> POST_RENDER_MODEL = EventFactory.createArrayBacked(AccessToBufferSourceEvents.PostRenderModel.class, callbacks -> event -> {
                for (var callback : callbacks) {
                    callback.event(event);
                }
            });

            @FunctionalInterface
            interface PostRenderModel {
                void event(EventLivingRenderer.PostRenderModel event);
            }

        }
    }
}
