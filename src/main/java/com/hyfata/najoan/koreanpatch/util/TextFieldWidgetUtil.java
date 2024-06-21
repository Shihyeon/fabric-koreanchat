package com.hyfata.najoan.koreanpatch.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class TextFieldWidgetUtil {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static int getTextWidth(TextFieldWidget textFieldWidget) {
        return client.textRenderer.getWidth(textFieldWidget.getText());
    }
}