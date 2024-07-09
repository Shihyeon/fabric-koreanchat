package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.util.AnimationUtil;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.language.LanguageUtil;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {AbstractSignEditScreen.class})
public abstract class SignEditScreenMixin extends Screen {
    @Shadow
    private int currentRow;

    @Shadow
    @Final
    private SignBlockEntity blockEntity;

    @Unique
    public final MinecraftClient client = MinecraftClient.getInstance();

    @Unique
    AnimationUtil animationUtil = new AnimationUtil();

    protected SignEditScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value = "TAIL")}, method = {"renderSignText"})
    public void addCustomLabel(DrawContext context, CallbackInfo ci) {
        float x = -(blockEntity.getMaxTextWidth() / 2f) - Indicator.getIndicatorWidth() / 2 - 5;
        int l = 4 * blockEntity.getTextLineHeight() / 2;
        float y = currentRow * blockEntity.getTextLineHeight() - l + client.textRenderer.fontHeight / 2f;

        animationUtil.init(0, y - 4);
        animationUtil.calculateAnimation(0, y);

        Indicator.showCenteredIndicator(context, x, animationUtil.getResultY());
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"keyPressed(III)Z"})
    public void init(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.client.currentScreen != null &&
                KoreanPatchClient.langBinding.matchesKey(keyCode, scanCode)) {
            LanguageUtil.toggleCurrentType();
        }
    }
}

