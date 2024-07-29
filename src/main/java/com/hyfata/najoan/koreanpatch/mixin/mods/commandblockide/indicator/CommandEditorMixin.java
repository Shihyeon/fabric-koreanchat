package com.hyfata.najoan.koreanpatch.mixin.mods.commandblockide.indicator;

import arm32x.minecraft.commandblockide.client.gui.MultilineTextFieldWidget;
import arm32x.minecraft.commandblockide.client.gui.editor.CommandEditor;
import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandEditor.class)
public abstract class CommandEditorMixin {
    @Shadow
    @Final
    protected MultilineTextFieldWidget commandField;

    @Shadow
    private int y;

    @Shadow public abstract boolean isLoaded();

    @Unique
    private static final int margin = 5;

    @Unique
    private int orgX = 0, orgWidth = 0;

    @Inject(at = @At(value = "HEAD"), method = "render")
    public void renderHead(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        KoreanPatchClient.bypassInjection = false;
        if (isLoaded()) {
            if (orgX == 0) {
                orgX = commandField.getX();
            } else if (orgWidth == 0) {
                orgWidth = commandField.getWidth();
            }

            commandField.setX((int) (orgX + Indicator.getIndicatorWidth() + margin));
            if (orgWidth != 0) {
                commandField.setWidth((int) (orgWidth - Indicator.getIndicatorWidth() - margin));
            }
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Larm32x/minecraft/commandblockide/client/gui/Container;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", shift = At.Shift.BEFORE), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (commandField.isFocused()) {
            Indicator.showIndicator(context, (float) orgX, (float) (y - Indicator.getIndicatorHeight() / 2 + 7.5));
        }
    }
}
