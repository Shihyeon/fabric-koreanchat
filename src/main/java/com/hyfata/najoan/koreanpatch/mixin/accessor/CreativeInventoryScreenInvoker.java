package com.hyfata.najoan.koreanpatch.mixin.accessor;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={CreativeInventoryScreen.class})
public interface CreativeInventoryScreenInvoker {
    @Invoker(value="search")
    void updateCreativeSearch();
}

