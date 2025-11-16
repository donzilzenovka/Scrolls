package com.drzenovka.scrolls.common.init;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict {

    public static void init() {
        // Example: register your scroll ink as a "dyeBlack"
        //OreDictionary.registerOre("ink", new ItemStack(Items.dye, 1, 0));
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre("ink", new ItemStack(Items.dye, 1, i));
        }
        OreDictionary.registerOre("paper", new ItemStack(Items.paper));
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre("paper", new ItemStack(ModItems.paperColored, 1, i));
        }

        OreDictionary.registerOre("quill", new ItemStack(Items.feather));

        // Register each wax color in the OreDictionary and local map
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            String color = COLOR_NAMES[i];
            OreDictionary.registerOre("wax" + capitalize(color), new ItemStack(ModItems.wax, 1, i));
        }

        OreDictionary.registerOre("DyeBlack", new ItemStack(Items.dye, 1, 0));
        OreDictionary.registerOre("DyeRed", new ItemStack(Items.dye, 1, 1));
        OreDictionary.registerOre("DyeGreen", new ItemStack(Items.dye, 1, 2));
        OreDictionary.registerOre("DyeBrown", new ItemStack(Items.dye, 1, 3));
        OreDictionary.registerOre("DyeBlue", new ItemStack(Items.dye, 1, 4));
        OreDictionary.registerOre("DyePurple", new ItemStack(Items.dye, 1, 5));
        OreDictionary.registerOre("DyeCyan", new ItemStack(Items.dye, 1, 6));
        OreDictionary.registerOre("DyeLightGray", new ItemStack(Items.dye, 1, 7));
        OreDictionary.registerOre("DyeGray", new ItemStack(Items.dye, 1, 8));
        OreDictionary.registerOre("DyePink", new ItemStack(Items.dye, 1, 9));
        OreDictionary.registerOre("DyeLime", new ItemStack(Items.dye, 1, 10));
        OreDictionary.registerOre("DyeYellow", new ItemStack(Items.dye, 1, 11));
        OreDictionary.registerOre("DyeLightBlue", new ItemStack(Items.dye, 1, 12));
        OreDictionary.registerOre("DyeMagenta", new ItemStack(Items.dye, 1, 13));
        OreDictionary.registerOre("DyeOrange", new ItemStack(Items.dye, 1, 14));
        OreDictionary.registerOre("DyeWhite", new ItemStack(Items.dye, 1, 15));

        

    }

    private static String capitalize(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
