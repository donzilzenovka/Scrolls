package com.drzenovka.scrolls.common.init;

import net.minecraft.item.Item;

import com.drzenovka.scrolls.common.item.ItemPaperColored;
import com.drzenovka.scrolls.common.item.ItemScroll;
import com.drzenovka.scrolls.common.item.ItemScrollColored;
import com.drzenovka.scrolls.common.item.ItemStamp;
import com.drzenovka.scrolls.common.item.ItemWax;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {

    public static ItemScroll scrollColored;
    public static ItemWax wax;
    public static Item paperColored;
    public static Item stamp;

    public static void init() {
        scrollColored = new ItemScrollColored();
        wax = new ItemWax();
        paperColored = new ItemPaperColored();
        stamp = new ItemStamp();

        GameRegistry.registerItem(scrollColored, "scrollColored");
        GameRegistry.registerItem(wax, "wax");
        GameRegistry.registerItem(paperColored, "paperColored");
        GameRegistry.registerItem(stamp, "stamp");

    }
}
