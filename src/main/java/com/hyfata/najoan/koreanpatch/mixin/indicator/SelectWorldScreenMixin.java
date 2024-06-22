package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.util.AnimationUtil;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.TextFieldWidgetUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {SelectWorldScreen.class})
public class SelectWorldScreenMixin extends Screen {
    @Shadow protected TextFieldWidget searchBox;

    @Unique
    private AnimationUtil animationUtil = null;

    protected SelectWorldScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value = "TAIL")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci){
        if (animationUtil == null) {
            animationUtil = new AnimationUtil((float) this.width / 2 - 105);
        }
        float x = TextFieldWidgetUtil.getCursorX(searchBox);
        float y = 22f; // from searchBox in init()
        Indicator.showIndicator(context, animationUtil.getAnimatedX(x, 0.7f) + 15, y - 6, true);
    }
}
