package com.github.alexthe666.citadel.mixin.refabricated;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {

    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorMaterial;layers()Ljava/util/List;"))
    void modifyModelToCustom(PoseStack poseStack, MultiBufferSource bufferSource, LivingEntity livingEntity, EquipmentSlot slot, int packedLight, HumanoidModel model, CallbackInfo ci, @Local ItemStack stack, @Local(argsOnly = true) LocalRef<HumanoidModel> modModel) {
        var ext = IClientItemExtensions.of(stack);
        if (ext != null) {
            modModel.set((HumanoidModel) ext.getGenericArmorModel(livingEntity, stack, slot, model));
        }
    }

}
