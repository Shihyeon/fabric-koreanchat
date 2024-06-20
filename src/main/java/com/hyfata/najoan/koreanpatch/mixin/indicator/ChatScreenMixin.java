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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ChatScreen.class})
public abstract class ChatScreenMixin extends Screen {

    @Shadow
    protected TextFieldWidget chatField;

    @Unique
    int chatWidth;

    @Unique
    float animatedWidth;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value="HEAD")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        int chatFieldWidth = TextFieldWidgetUtil.getCursorPosition(chatField);
        if (chatWidth != chatFieldWidth) {
            chatWidth = chatFieldWidth;
            if (chatWidth > chatField.getWidth() - 20) {
                chatWidth = chatField.getWidth() - 20;
            }
        }

        float interpolationSpeed = 0.1f;
        animatedWidth += ((chatWidth - animatedWidth) * interpolationSpeed);

        Indicator.showIndicator(context, (int) animatedWidth + 2, this.height - 27, false);
    }
}
