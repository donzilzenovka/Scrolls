package com.drzenovka.scrolls.common.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict {

    public static void init() {
        // Example: register your scroll ink as a "dyeBlack"
        OreDictionary.registerOre("ink", new ItemStack(Items.dye, 1, 0));
        OreDictionary.registerOre("paper", new ItemStack(Items.paper));
        OreDictionary.registerOre("quill", new ItemStack(Items.feather));
    }
}
