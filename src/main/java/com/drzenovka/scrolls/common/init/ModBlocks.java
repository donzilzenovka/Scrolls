package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.block.BlockHangingScroll;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ModBlocks {

    // Example placeholder for a pinned scroll block
    public static Block pinnedScroll;

    public static void init() {
        // If you ever want scrolls as placeable blocks
        pinnedScroll = new BlockHangingScroll();
        GameRegistry.registerBlock(pinnedScroll, "pinnedScroll");
    }
}
