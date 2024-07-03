package com.hyfata.najoan.koreanpatch.mixin.accessor;

import net.minecraft.client.gui.widget.TabNavigationWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TabNavigationWidget.class)
public interface TabNavigationWidgetInvoker {
    @Invoker("getCurrentTabIndex")
    int currentTabIndex();
}
