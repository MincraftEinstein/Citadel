package com.github.alexthe666.citadel.item;

import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.refabrciated.client.ClientItemExtensionsProvider;
import com.github.alexthe666.citadel.refabrciated.client.IClientItemExtensions;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class ItemCustomRender extends Item implements ClientItemExtensionsProvider {

    public ItemCustomRender(Properties props) {
        super(props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(((IClientItemExtensions) Citadel.PROXY.getISTERProperties()));
    }
}
