package com.drzenovka.scrolls.common.handler;

import com.drzenovka.scrolls.common.item.ItemPaperColored;
import com.drzenovka.scrolls.common.item.ItemScroll;
import com.drzenovka.scrolls.common.item.ItemScrollColored;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {

        if (fuel == null) return 0;

        // Example: scrolls burn for 200 ticks, paper for 100
        if (fuel.getItem() instanceof ItemScroll) {
            return 20; // 1 seconds
        }

        if (fuel.getItem() instanceof ItemScrollColored) {
            return 20;
        }

        if (fuel.getItem() instanceof ItemPaperColored) {
            return 20;
        }

        if (fuel.getItem() == Items.paper) {
            return 20; // 1 second
        }

        return 0;
    }
}
