package com.github.alexthe666.citadel.refabrciated.client;

import java.util.function.Consumer;

public interface ClientItemExtensionsProvider {
    void initializeClient(Consumer<IClientItemExtensions> consumer);
}
