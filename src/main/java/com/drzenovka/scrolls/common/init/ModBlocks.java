package com.drzenovka.scrolls.common.init;


import com.drzenovka.scrolls.common.block.BlockStamp;
import com.drzenovka.scrolls.common.item.ItemStamp;
import net.minecraft.block.Block;

import com.drzenovka.scrolls.common.block.BlockHangingScroll;
import com.drzenovka.scrolls.common.block.BlockInkCauldron;
import com.drzenovka.scrolls.common.item.ItemBlockInkCauldron;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {

    // Example placeholder for a pinned scroll block
    public static Block pinnedScroll;
    public static Block inkCauldron;
    public static BlockStamp stamp;

    public static void init() {
        // If you ever want scrolls as placeable blocks
        pinnedScroll = new BlockHangingScroll();
        inkCauldron = new BlockInkCauldron();
        stamp = new BlockStamp();

        GameRegistry.registerBlock(pinnedScroll, "pinnedScroll");
        GameRegistry.registerBlock(inkCauldron, ItemBlockInkCauldron.class, "inkCauldron");
        GameRegistry.registerBlock(stamp, ItemStamp.class, "stamp");
    }
}
