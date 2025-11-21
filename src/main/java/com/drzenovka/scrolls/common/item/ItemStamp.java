package com.drzenovka.scrolls.common.item;

import static com.drzenovka.scrolls.common.core.Scrolls.scrollsTab;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemStamp extends ItemBlock {

    public ItemStamp(Block block) {
        super(block);
        this.setUnlocalizedName("stamp")
            .setMaxStackSize(1)
            .setContainerItem(this)
            .setFull3D()
            .setCreativeTab(scrollsTab);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false;
    }
}
