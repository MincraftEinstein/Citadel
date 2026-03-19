package net.neoforged.neoforge.client.extensions.common;

import com.github.alexthe666.citadel.mixin.refabricated.ItemRendererAccessor;
import com.github.alexthe666.citadel.refabrciated.client.ClientExtensionsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IClientItemExtensions {
    IClientItemExtensions DEFAULT = new IClientItemExtensions() {};

    static IClientItemExtensions of(ItemStack stack) {
        return of(stack.getItem());
    }

    static IClientItemExtensions of(Item item) {
        return ClientExtensionsManager.ITEM_EXTENSIONS.get(item);
    }


    default BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return ((ItemRendererAccessor) Minecraft.getInstance().getItemRenderer()).citadel_getBlockEntityRenderer();
    }
}
