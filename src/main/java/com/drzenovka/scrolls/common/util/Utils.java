package com.drzenovka.scrolls.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

    public static boolean isOreDictItem(ItemStack stack, String key) {
        if (stack == null) return false;

        int[] ids = OreDictionary.getOreIDs(stack);
        for (int id : ids) {
            if (OreDictionary.getOreName(id)
                .equals(key)) {
                return true;
            }
        }
        return false;
    }
}
