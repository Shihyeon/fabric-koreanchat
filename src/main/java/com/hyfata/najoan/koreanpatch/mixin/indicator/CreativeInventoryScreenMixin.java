package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.hyfata.najoan.koreanpatch.util.language.LanguageUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends Screen {
    @Shadow private TextFieldWidget searchBox;

    protected CreativeInventoryScreenMixin(Text title) {
        super(title);
    }

    @Unique
    boolean search = false;

    @Inject(method = {"render"}, at = @At(value = "TAIL", shift = At.Shift.BY, by = -3))
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (search) {
            int x = searchBox.getX() + searchBox.getWidth() + 19;
            int y = searchBox.getY() + searchBox.getHeight() / 2;

            Indicator.showCenteredIndicator(context, x, y);
        }
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

    @Inject(at = {@At(value = "HEAD")}, method = {"setSelectedTab"})
    private void check(ItemGroup group, CallbackInfo callbackInfo) {
        search = group.getType() == ItemGroup.Type.SEARCH;
    }
}
