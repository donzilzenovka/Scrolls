package com.drzenovka.scrolls.common.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

    // Example placeholder for a pinned scroll block
    public static Block pinnedScroll;

    public static void init() {
        // If you ever want scrolls as placeable blocks
        pinnedScroll = new BlockPinnedScroll();
        GameRegistry.registerBlock(pinnedScroll, "pinnedScroll");
    }
}
