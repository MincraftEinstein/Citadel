package com.github.alexthe666.citadel.client;

import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.client.shader.CitadelInternalShaders;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.resources.ResourceLocation;

public class ClientEvents {
    public static void registerShaders(CoreShaderRegistrationCallback.RegistrationContext e) {
        try {
            e.register(ResourceLocation.parse("citadel:rendertype_rainbow_aura"), DefaultVertexFormat.POSITION_TEX_COLOR, CitadelInternalShaders::setRenderTypeRainbowAura);
        } catch (Exception exception) {
            Citadel.LOGGER.error(e);
        }
    }
}
