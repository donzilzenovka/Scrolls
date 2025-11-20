package com.drzenovka.scrolls.common.item;

import static com.drzenovka.scrolls.common.core.Scrolls.scrollsTab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item {

    public ItemStamp() {
        this.setUnlocalizedName("stamp")
            .setTextureName("scrolls:stamp")
            .setMaxStackSize(1)
            .setContainerItem(this)
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
