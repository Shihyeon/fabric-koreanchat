package com.hyfata.najoan.koreanpatch.mixin.accessor;

import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BookEditScreen.PageContent.class)
public interface BookEditScreenPageContentAccessor {
    @Accessor("position")
    BookEditScreen.Position getPosition();
}
