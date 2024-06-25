package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.mixin.ChatInputSuggestorAccessor;
import com.hyfata.najoan.koreanpatch.util.AnimationUtil;
import com.hyfata.najoan.koreanpatch.util.Indicator;
import com.hyfata.najoan.koreanpatch.util.TextFieldWidgetUtil;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ChatScreen.class})
public abstract class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    protected TextFieldWidget chatField;

    @Shadow
    ChatInputSuggestor chatInputSuggestor;

    @Unique
    private final AnimationUtil animationUtil = new AnimationUtil();


    @Inject(at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TextFieldWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V")}, method = {"render"})
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ChatInputSuggestorAccessor accessor = (ChatInputSuggestorAccessor) chatInputSuggestor;
        int height = 0;
        if (accessor.getWindow() != null && accessor.getPendingSuggestions() != null && accessor.getPendingSuggestions().isDone()) {
            Suggestions suggestions = accessor.getPendingSuggestions().join();
            if (!suggestions.isEmpty())
                height = Math.min(accessor.getSortSuggestions(suggestions).size(), accessor.getMaxSuggestionSize()) * 12 + 1;
        }

        float indicatorX = Math.min(TextFieldWidgetUtil.getCursorX(chatField), chatField.getWidth() - 20);
        float indicatorY = this.height - 27 - height;

        animationUtil.init(0, 0);
        animationUtil.calculateAnimation(indicatorX, 0, 0.7f);

        context.getMatrices().translate(0.0F, 0.0F, 200.0F);
        Indicator.showIndicator(context, animationUtil.getResultX(), indicatorY, false);
    }
}
