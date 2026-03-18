package com.github.alexthe666.citadel.mixin.refabricated;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.spongepowered.asm.mixin.gen.Invoker;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.client.gui.screens.Screen.class)
public interface ScreenAccessor<T extends GuiEventListener & Renderable & NarratableEntry> {
    @Invoker("addRenderableWidget")
    T citadel_addRenderableWidget(T widget);
}
