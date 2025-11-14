package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.item.ItemPaperColored;
import com.drzenovka.scrolls.common.item.ItemScroll;
import com.drzenovka.scrolls.common.item.ItemScrollColored;
import com.drzenovka.scrolls.common.item.ItemScrollStamped;
import com.drzenovka.scrolls.common.item.ItemWax;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {

    // Public reference for the scroll item
    public static ItemScroll scroll;
    public static ItemScroll scrollColored;
    public static ItemScroll scrollStamped;
    public static ItemWax wax;
    public static Item paperColored;


    public static void init() {
        // Initialize the scroll item
        scroll = new ItemScroll();
        scrollStamped = new ItemScrollStamped();
        scrollColored = new ItemScrollColored();
        wax = new ItemWax();
        paperColored = new ItemPaperColored();


        // Register with GameRegistry
        GameRegistry.registerItem(scroll, "scroll");
        GameRegistry.registerItem(scrollStamped, "scroll_stamped");
        GameRegistry.registerItem(scrollColored, "scroll_colored");
        GameRegistry.registerItem(wax, "wax");
        GameRegistry.registerItem(paperColored, "colored_paper");

    }
}
