package com.hyfata.najoan.koreanpatch.mixin.accessor;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreateWorldScreen.GameTab.class)
public interface CreateWorldScreenGameTabAccessor {
    @Accessor("worldNameField")
    TextFieldWidget getWorldNameField();
}
