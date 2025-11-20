package com.drzenovka.scrolls.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.drzenovka.scrolls.common.init.ModItems;

public class ScrollsTab extends CreativeTabs {

    public ScrollsTab() {
        super("scrolls");
    }

    @Override
    public Item getTabIconItem() {
        // fallback icon if itemDriedHerb not registered yet
        return ModItems.scroll;
    }
}
