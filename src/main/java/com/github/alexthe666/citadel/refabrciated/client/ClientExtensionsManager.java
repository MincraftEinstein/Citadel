package com.github.alexthe666.citadel.refabrciated.client;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.Map;

public class ClientExtensionsManager {
    public static final Map<Item, IClientItemExtensions> ITEM_EXTENSIONS = new Reference2ObjectOpenHashMap<>();
    private static boolean earlyInitialized = false;

    public static void earlyInit() {
        if (earlyInitialized) {
            throw new IllegalStateException("Duplicate early initialization of ClientExtensionsManager");
        } else {
            earlyInitialized = true;
            BuiltInRegistries.ITEM.forEach((item) -> {
                if (item instanceof ClientItemExtensionsProvider provider) {
                    provider.initializeClient((ext) -> ITEM_EXTENSIONS.put(item, ext));
                }
            });
        }
    }
}
