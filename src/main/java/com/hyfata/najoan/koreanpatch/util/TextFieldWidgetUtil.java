package com.hyfata.najoan.koreanpatch.util;

import com.hyfata.najoan.koreanpatch.mixin.accessor.TextFieldWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TextFieldWidgetUtil {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static float getCursorX(TextFieldWidget textField) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) textField;
        int firstCharacterIndex = accessor.getFirstCharacterIndex();
        int selectionStart = accessor.getSelectionStart();

        return textField.getCharacterX(selectionStart - firstCharacterIndex);
    }

    public static float calculateIndicatorY(TextFieldWidget textField) {
        return textField.getY() - Indicator.getIndicatorHeight() / 1.5f;
    }

    public static float getCursorXWithText(TextFieldWidget textField, Text text, int x) {
        int textWidth = client.textRenderer.getWidth(text);
        return Math.max(x + textWidth, getCursorX(textField));
    }
}
