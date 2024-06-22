package com.hyfata.najoan.koreanpatch.util;

import com.hyfata.najoan.koreanpatch.mixin.TextFieldWidgetAccessor;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class TextFieldWidgetUtil {
    public static float getCursorX(TextFieldWidget textField) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) textField;
        int firstCharacterIndex = accessor.getFirstCharacterIndex();
        int selectionStart = accessor.getSelectionStart();

        return textField.getCharacterX(selectionStart - firstCharacterIndex);
    }
}
