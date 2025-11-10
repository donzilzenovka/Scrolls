package com.drzenovka.scrolls.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ScrollGuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null; // no server container needed
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // For scroll GUI, open for the currently held item
        if (ID == 0) {
            int handSlot = player.inventory.currentItem;
            return new GuiScroll(player, handSlot);
        }
        return null;
    }
}
