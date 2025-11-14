package com.drzenovka.scrolls.common.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

public class ModOreDict {

    public static void init() {
        // Example: register your scroll ink as a "dyeBlack"
        OreDictionary.registerOre("ink", new ItemStack(Items.dye, 1, 0));
        OreDictionary.registerOre("paper", new ItemStack(Items.paper));
        OreDictionary.registerOre("quill", new ItemStack(Items.feather));


        // Register each wax color in the OreDictionary and local map
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            String color = COLOR_NAMES[i];

            // Example OreDict: "waxRed", "waxBlue", etc.
            OreDictionary.registerOre("wax" + capitalize(color), new ItemStack(ModItems.wax, 1, i));

            // If you want scroll variants in OreDict too:
            //OreDictionary.registerOre("scrollStamped" + capitalize(color), new ItemStack(ModItems.scrollStamped, 1, i));
        }
    }

    private static String capitalize(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
