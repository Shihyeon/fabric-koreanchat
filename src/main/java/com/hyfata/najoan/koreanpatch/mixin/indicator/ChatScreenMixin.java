package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.mixin.TextFieldWidgetAccessor;
import com.hyfata.najoan.koreanpatch.util.EasingFunctions;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
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
    int savedTextLength = 0;

    @Unique
    float savedIndicatorX = 0;

    @Unique
    float currentIndicatorX = 0;

    @Unique
    float animationTickTime = 0;

    @Unique
    Object targetComponent;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = {@At(value="HEAD")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) chatField;
        int firstCharacterIndex = accessor.getFirstCharacterIndex();
        int selectionStart = accessor.getSelectionStart();

        int cursorX = chatField.getCharacterX(selectionStart - firstCharacterIndex);
        float indicatorX = Math.min(cursorX, chatField.getWidth());

        if (indicatorX > chatField.getWidth() - 20) {
            indicatorX = chatField.getWidth() - 20;
        }

        if (targetComponent == null || targetComponent != this) {
            targetComponent = this;
            savedTextLength = 0;
            savedIndicatorX = indicatorX;
            currentIndicatorX = indicatorX;
            animationTickTime = 0;
        }

        if (chatField.getText().length() != savedTextLength) {
            animationTickTime = (float) GLFW.glfwGetTime();
            savedTextLength = chatField.getText().length();
            savedIndicatorX = currentIndicatorX;
        }

        if (GLFW.glfwGetTime() - animationTickTime > 1.0f) {
            savedIndicatorX = indicatorX;
        } else {
            currentIndicatorX = savedIndicatorX + (indicatorX - savedIndicatorX) * EasingFunctions.easeOutQuint((float)GLFW.glfwGetTime() - animationTickTime);
            indicatorX = currentIndicatorX;
        }

        Indicator.showIndicator(context, indicatorX + 2, this.height - 27, false);
    }
}
