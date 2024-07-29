package com.hyfata.najoan.koreanpatch.mixin.mods.commandblockide.indicator;

import arm32x.minecraft.commandblockide.client.gui.editor.CommandBlockEditor;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandBlockEditor.class)
public class CommandBlockEditorMixin {
    @Shadow @Final private TextFieldWidget lastOutputField;
    @Unique
    private static final int margin = 5;

    @Unique
    private int orgX = 0, orgWidth = 0;

    @Inject(method = "renderCommandField", at = @At("HEAD"))
    private void renderCommandField(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (orgX == 0) {
            orgX = lastOutputField.getX();
        } else if (orgWidth == 0) {
            orgWidth = lastOutputField.getWidth();
        }

        lastOutputField.setX((int) (orgX + Indicator.getIndicatorWidth() + margin));
        if (orgWidth != 0) {
            lastOutputField.setWidth((int) (orgWidth - Indicator.getIndicatorWidth() - margin));
        }
    }
}
