package com.github.alexthe666.citadel.refabrciated.client.event;

import com.github.alexthe666.citadel.refabrciated.event.ICancellableEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.screens.Screen;

public interface ScreenEventKeyPressed {
    Event<ScreenEventKeyPressedPre> PRE = EventFactory.createArrayBacked(ScreenEventKeyPressedPre.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.post(event);
            if (event.isCanceled()) return;
        }
    });

    @FunctionalInterface
    interface ScreenEventKeyPressedPre {
        void post(ScreenEventKeyPressed.Pre event);
    }

    class Pre extends com.github.alexthe666.citadel.refabrciated.event.Event implements ICancellableEvent {
        private final Screen screen;
        private final int keyCode;
        private final int scanCode;
        private final int modifiers;

        public Pre(Screen screen, int keyCode, int scanCode, int modifiers) {
            this.screen = screen;
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.modifiers = modifiers;
        }

        public int getKeyCode() {
            return this.keyCode;
        }

        public int getScanCode() {
            return this.scanCode;
        }

        public int getModifiers() {
            return this.modifiers;
        }

        public Screen getScreen() {
            return screen;
        }
    }
}
