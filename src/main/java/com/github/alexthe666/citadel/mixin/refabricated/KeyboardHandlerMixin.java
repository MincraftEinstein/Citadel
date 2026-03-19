package com.github.alexthe666.citadel.mixin.refabricated;

import com.github.alexthe666.citadel.refabrciated.client.event.ScreenEventKeyPressed;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @WrapOperation(method = "method_1454", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"))
    private static boolean preScreenInputs(Screen instance, int keyCode, int scanCode, int modifiers, Operation<Boolean> original) {
        var event = new ScreenEventKeyPressed.Pre(instance, keyCode, scanCode, modifiers);
        ScreenEventKeyPressed.PRE.invoker().post(event);
        if (event.isCanceled()) return true;
        return original.call(instance, keyCode, scanCode, modifiers);
    }
}
