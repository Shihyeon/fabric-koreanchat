package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.hyfata.najoan.koreanpatch.util.language.LanguageUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {AnvilScreen.class})
public class AnvilScreenMixin extends Screen {

    @Shadow
    private TextFieldWidget nameField;

    protected AnvilScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value = "TAIL")}, method = {"renderForeground"})
    private void customLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        float x = nameField.getX() + nameField.getWidth() - Indicator.getIndicatorWidth();
        float y = nameField.getY() - Indicator.getIndicatorHeight() - 6;

        Indicator.showIndicator(context, x, y);
    }

    @Inject(method = {"keyPressed"}, at = @At(value = "HEAD"))
    private void keyPress(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        ModLogger.debug(keyCode + " " + scanCode + " " + modifiers);
        if (KoreanPatchClient.imeBinding.matchesKey(keyCode, scanCode) && modifiers == 2) {
            KoreanPatchClient.getController().toggleFocus();
        } else if (KoreanPatchClient.langBinding.matchesKey(keyCode, scanCode) && !KoreanPatchClient.IME) {
            LanguageUtil.toggleCurrentType();
        }
    }
}
