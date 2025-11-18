package com.drzenovka.scrolls.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item {

    public ItemStamp() {
        this.setUnlocalizedName("stamp")
            .setTextureName("scrolls:stamp")
            .setMaxStackSize(1)
            .setContainerItem(this)
            .setCreativeTab(CreativeTabs.tabMisc);
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
