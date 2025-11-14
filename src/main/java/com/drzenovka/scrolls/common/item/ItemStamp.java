package com.drzenovka.scrolls.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemStamp extends Item {
    public ItemStamp(){
        this.setUnlocalizedName("stamp")
            .setTextureName("scrolls:stamp")
            .setMaxStackSize(1)
            .setCreativeTab(CreativeTabs.tabMisc);
    }

}
