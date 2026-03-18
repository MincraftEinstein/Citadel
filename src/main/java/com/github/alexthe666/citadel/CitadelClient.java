package com.github.alexthe666.citadel;

import net.fabricmc.api.ClientModInitializer;

public class CitadelClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Citadel.PROXY.onClientInit();
    }
}
