package com.drzenovka.scrolls.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockHangingScroll extends Block {

    public BlockHangingScroll() {
        super(Material.wood);  // protected constructor is fine here
        setBlockName("pinnedScroll");
        setBlockTextureName("scrolls:pinned_scroll");
        setHardness(0.5F);
    }
}
