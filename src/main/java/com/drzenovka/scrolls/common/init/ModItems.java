package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.item.ItemScroll;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

    // Public reference for the scroll item
    public static ItemScroll scroll;

    public static void init() {
        // Initialize the scroll item
        scroll = new ItemScroll();
        scroll.setUnlocalizedName("scroll")
            .setTextureName("scrolls:scroll")
            .setMaxStackSize(16)
            .setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabMisc);

        // Register with GameRegistry
        GameRegistry.registerItem(scroll, "scroll");
    }
}
