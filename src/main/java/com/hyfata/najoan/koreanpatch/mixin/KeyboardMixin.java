package com.hyfata.najoan.koreanpatch.mixin;

import com.hyfata.najoan.koreanpatch.client.KeyBinds;
import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.plugin.InputManager;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.hyfata.najoan.koreanpatch.util.language.LanguageUtil;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onInput(long window, int keyCode, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (window == client.getWindow().getHandle() && action == 1 && client.currentScreen != null && !KoreanPatchClient.bypassInjection) {
            ModLogger.debug(keyCode + " " + scanCode + " " + modifiers + " " + action);

            if (KeyBinds.getImeBinding().matchesKey(keyCode, scanCode) && modifiers == 2) {
                InputManager.getController().toggleFocus();
            } else if (KeyBinds.getLangBinding().matchesKey(keyCode, scanCode) && !KoreanPatchClient.IME) {
                LanguageUtil.toggleCurrentType();
            }
        }
    }
}
