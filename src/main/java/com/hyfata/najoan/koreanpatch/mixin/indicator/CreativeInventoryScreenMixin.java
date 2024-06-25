package com.hyfata.najoan.koreanpatch.mixin.indicator;

import com.hyfata.najoan.koreanpatch.util.Indicator;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends Screen {
    protected CreativeInventoryScreenMixin(Text title) {
        super(title);
    }

    @Unique
    boolean search = false;

    @Inject(method = {"render"}, at = @At(value = "TAIL", shift = At.Shift.BY, by = -3))
    private void addCustomLabel(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (search) {
            // init(): this.searchBox = new TextFieldWidget(this.textRenderer, this.x + 82, this.y + 6, 80
            // HandledScreen.init(): this.x = (this.width - this.backgroundWidth) / 2
            int x = (this.width - 176) / 2 + 82;
            int y = (this.height - 166) / 2 + 6;
            int searchBoxWidth = 80;

            Indicator.showIndicator(context, x + searchBoxWidth + 8, y + 14, true);
        }
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"setSelectedTab"})
    private void check(ItemGroup group, CallbackInfo callbackInfo) {
        search = group.getType() == ItemGroup.Type.SEARCH;
    }
}
