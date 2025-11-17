package com.drzenovka.scrolls.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;

import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_BLACK;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_BLUE;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_BROWN;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_CYAN;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_GRAY;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_GREEN;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_LIGHT_BLUE;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_LIGHT_GRAY;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_LIME;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_MAGENTA;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_ORANGE;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_PINK;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_PURPLE;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_RED;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_WHITE;
import static com.drzenovka.scrolls.common.init.ModOreDict.DYE_YELLOW;

public class DyeColorMap {
    private static final Map<String, Integer> DYE_TO_COLOR = new HashMap<>();

    static {
        DYE_TO_COLOR.put(DYE_WHITE,   0);
        DYE_TO_COLOR.put(DYE_ORANGE,  1);
        DYE_TO_COLOR.put(DYE_MAGENTA, 2);
        DYE_TO_COLOR.put(DYE_LIGHT_BLUE, 3);
        DYE_TO_COLOR.put(DYE_YELLOW, 4);
        DYE_TO_COLOR.put(DYE_LIME, 5);
        DYE_TO_COLOR.put(DYE_PINK, 6);
        DYE_TO_COLOR.put(DYE_GRAY, 7);
        DYE_TO_COLOR.put(DYE_LIGHT_GRAY, 8);
        DYE_TO_COLOR.put(DYE_CYAN, 9);
        DYE_TO_COLOR.put(DYE_PURPLE, 10);
        DYE_TO_COLOR.put(DYE_BLUE, 11);
        DYE_TO_COLOR.put(DYE_BROWN, 12);
        DYE_TO_COLOR.put(DYE_GREEN, 13);
        DYE_TO_COLOR.put(DYE_RED, 14);
        DYE_TO_COLOR.put(DYE_BLACK, 15);
    }

    public static int getColorForStack(ItemStack stack) {
        for (int oreID : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(oreID);
            if (DYE_TO_COLOR.containsKey(name)) {
                return DYE_TO_COLOR.get(name);
            }
        }
        return -1;
    }
}
