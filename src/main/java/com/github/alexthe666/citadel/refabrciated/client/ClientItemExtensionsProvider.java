package com.github.alexthe666.citadel.refabrciated.client;

import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public interface ClientItemExtensionsProvider {
    void initializeClient(Consumer<IClientItemExtensions> consumer);
}
