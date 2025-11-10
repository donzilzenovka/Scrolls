package com.drzenovka.scrolls.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockPinnedScroll extends Block {

    public BlockPinnedScroll() {
        super(Material.wood);  // protected constructor is fine here
        setBlockName("pinnedScroll");
        setBlockTextureName("scrolls:pinned_scroll");
        setHardness(0.5F);
    }
}
