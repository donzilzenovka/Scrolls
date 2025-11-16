package com.drzenovka.scrolls.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

public class DyeColorMap {
    private static final Map<String, Integer> DYE_TO_COLOR = new HashMap<>();

    static {
        DYE_TO_COLOR.put("dyeWhite",   0);
        DYE_TO_COLOR.put("dyeOrange",  1);
        DYE_TO_COLOR.put("dyeMagenta", 2);
        DYE_TO_COLOR.put("dyeLightBlue", 3);
        DYE_TO_COLOR.put("dyeYellow", 4);
        DYE_TO_COLOR.put("dyeLime", 5);
        DYE_TO_COLOR.put("dyePink", 6);
        DYE_TO_COLOR.put("dyeGray", 7);
        DYE_TO_COLOR.put("dyeLightGray", 8);
        DYE_TO_COLOR.put("dyeCyan", 9);
        DYE_TO_COLOR.put("dyePurple", 10);
        DYE_TO_COLOR.put("dyeBlue", 11);
        DYE_TO_COLOR.put("dyeBrown", 12);
        DYE_TO_COLOR.put("dyeGreen", 13);
        DYE_TO_COLOR.put("dyeRed", 14);
        DYE_TO_COLOR.put("dyeBlack", 15);
    }

    public static int getColorForStack(ItemStack stack) {
        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if (DYE_TO_COLOR.containsKey(name)) {
                return DYE_TO_COLOR.get(name);
            }
        }
        return -1; // invalid
    }
}
