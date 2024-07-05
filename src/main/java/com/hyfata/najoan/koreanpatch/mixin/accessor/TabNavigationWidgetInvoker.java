package com.hyfata.najoan.koreanpatch.mixin.accessor;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.TabNavigationWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TabNavigationWidget.class)
public interface TabNavigationWidgetInvoker {
    @Invoker("getCurrentTabIndex")
    int currentTabIndex();

    @Accessor("tabs")
    ImmutableList<Tab> getTabs();
}
