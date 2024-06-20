package com.hyfata.najoan.koreanpatch.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class TextFieldWidgetUtil {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    public static int firstCharacterIndex;

    public static int getCursorPosition(TextFieldWidget textFieldWidget) {
        int cursorPosition = textFieldWidget.getCursor();
        int cursorX = textFieldWidget.getX() + client.textRenderer.getWidth(textFieldWidget.getText().substring(firstCharacterIndex, cursorPosition));

        int maxWidth = textFieldWidget.getWidth();
        int textWidth = client.textRenderer.getWidth(textFieldWidget.getText().substring(firstCharacterIndex));
        int offsetX = textWidth > maxWidth ? textWidth - maxWidth : 0;
        cursorX -= offsetX;
        return cursorX;
    }
}