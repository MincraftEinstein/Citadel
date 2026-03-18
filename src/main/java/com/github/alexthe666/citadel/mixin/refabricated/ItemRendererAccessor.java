package com.github.alexthe666.citadel.mixin.refabricated;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import org.spongepowered.asm.mixin.gen.Accessor;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.client.renderer.entity.ItemRenderer.class)
public interface ItemRendererAccessor {
    @Accessor("blockEntityRenderer")
    BlockEntityWithoutLevelRenderer citadel_getBlockEntityRenderer();
}
