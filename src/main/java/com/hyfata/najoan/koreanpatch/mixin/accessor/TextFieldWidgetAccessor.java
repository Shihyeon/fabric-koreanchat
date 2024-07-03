package com.hyfata.najoan.koreanpatch.mixin.accessor;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextFieldWidget.class)
public interface TextFieldWidgetAccessor {
    @Accessor("firstCharacterIndex")
    int getFirstCharacterIndex();

    @Accessor("selectionStart")
    int getSelectionStart();
}