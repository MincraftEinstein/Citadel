package net.neoforged.neoforge.client.extensions.common;

import com.github.alexthe666.citadel.mixin.refabricated.ItemRendererAccessor;
import com.github.alexthe666.citadel.refabrciated.client.ClientExtensionsManager;
import com.github.alexthe666.citadel.refabrciated.client.FabUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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

    default HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        return original;
    }

    default Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
        HumanoidModel<?> replacement = getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original);
        if (replacement != original) {
            FabUtils.copyModelProperties(original, replacement);
            return replacement;
        }
        return original;
    }
}
