package com.github.alexthe666.citadel;

import com.github.alexthe666.citadel.refabrciated.client.ClientExtensionsManager;
import com.github.alexthe666.citadel.server.message.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CitadelClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Citadel.PROXY.onClientInit();
        ClientExtensionsManager.earlyInit();
        registerClientReceivers();
    }

    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PropertiesMessage.TYPE, (message, _ctx) -> PropertiesMessage.handle(message, null));
        ClientPlayNetworking.registerGlobalReceiver(AnimationMessage.TYPE, (message, _ctx) -> AnimationMessage.handle(message));
        ClientPlayNetworking.registerGlobalReceiver(DanceJukeboxMessage.TYPE, (message, _ctx) -> DanceJukeboxMessage.handle(message));
        ClientPlayNetworking.registerGlobalReceiver(SyncePathMessage.TYPE, (message, _ctx) -> SyncePathMessage.handle(message));
        ClientPlayNetworking.registerGlobalReceiver(SyncPathReachedMessage.TYPE, (message, _ctx) -> SyncPathReachedMessage.handle(message));
    }
}
