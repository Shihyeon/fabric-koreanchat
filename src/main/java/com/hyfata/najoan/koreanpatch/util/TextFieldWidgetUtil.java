package com.hyfata.najoan.koreanpatch.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Unique;

public class TextFieldWidgetUtil {
    @Unique
    static MinecraftClient client = MinecraftClient.getInstance();
    private final TextFieldWidget textFieldWidget;

    public TextFieldWidgetUtil(TextFieldWidget textFieldWidget) {
        this.textFieldWidget = textFieldWidget;
    }

    public int getTextWidth() {
        String text = this.textFieldWidget.getText();
        return client.textRenderer.getWidth(text);
    }
}