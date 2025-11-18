package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.block.BlockInkCauldron;
import net.minecraft.block.Block;

import com.drzenovka.scrolls.common.block.BlockHangingScroll;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    // Example placeholder for a pinned scroll block
    public static Block pinnedScroll;
    public static Block inkCauldron;

    public static void init() {
        // If you ever want scrolls as placeable blocks
        pinnedScroll = new BlockHangingScroll();
        inkCauldron = new BlockInkCauldron();

        GameRegistry.registerBlock(pinnedScroll, "pinnedScroll");
        GameRegistry.registerBlock(inkCauldron, "inkCauldron");
    }
}
