package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.TextFieldWidgetUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatScreen.class})
public abstract class ChatScreenMixin extends Screen {

    @Shadow private TextFieldWidget chatField;

    private TextFieldWidgetUtil textFieldWidgetUtil;
    private int width = 0;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value="HEAD")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        textFieldWidgetUtil = new TextFieldWidgetUtil(chatField);
        if (width != textFieldWidgetUtil.getTextWidth()) {
            width = textFieldWidgetUtil.getTextWidth();
            if (width > client.getWindow().getWidth()) {
                width = client.getWindow().getWidth();
            }
        }
        Indicator.showIndicator(context, width + 2, this.height - 27, false);
    }
}
