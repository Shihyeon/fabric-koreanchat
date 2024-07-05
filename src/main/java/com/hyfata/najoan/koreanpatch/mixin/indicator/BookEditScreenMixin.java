package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.mixin.accessor.BookEditScreenPageContentAccessor;
import com.hyfata.najoan.koreanpatch.util.AnimationUtil;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.language.LanguageUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {BookEditScreen.class})
public abstract class BookEditScreenMixin extends Screen {

    protected BookEditScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    protected abstract BookEditScreen.PageContent getPageContent();

    @Shadow
    private boolean signing;

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Unique
    AnimationUtil animationUtil = new AnimationUtil();

    @Inject(at = {@At(value = "RETURN")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        float x = (this.width - 192) / 2f; // int i = (this.width - 192) / 2; in render() method
        float y;
        if (signing) {
            y = 50 + 4.5f;
        } else {
            BookEditScreenPageContentAccessor pageContent = (BookEditScreenPageContentAccessor) getPageContent();
            y = pageContent.getPosition().y + 32 + 4.5f; //absolutePositionToScreenPosition() + (fontHeight(9) / 2)
        }

        animationUtil.init(0, y - 4);
        animationUtil.calculateAnimation(0, y);

        Indicator.showCenteredIndicator(context, x + 10, animationUtil.getResultY());
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"keyPressed(III)Z"})
    private void init(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (this.client.currentScreen != null && (keyCode == KoreanPatchClient.KEYCODE || scanCode == KoreanPatchClient.SCANCODE)) {
            LanguageUtil.toggleCurrentType();
        }
    }
}

