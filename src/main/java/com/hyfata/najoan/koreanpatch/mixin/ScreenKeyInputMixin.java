package com.hyfata.najoan.koreanpatch.mixin;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenKeyInputMixin {
    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void onInput(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (KoreanPatchClient.imeBinding.matchesKey(keyCode, scanCode) && modifiers == 2) {
            KoreanPatchClient.getController().toggleFocus();
        }
    }
}
