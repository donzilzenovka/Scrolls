package com.drzenovka.scrolls.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.drzenovka.scrolls.client.gui.GuiScroll;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;

import cpw.mods.fml.common.network.IGuiHandler;

public class ScrollGuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null; // no server container needed
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            // x is actually the entity ID
            if (x > 0) {
                Entity e = world.getEntityByID(x);
                if (e instanceof EntityHangingScroll) {
                    EntityHangingScroll scroll = (EntityHangingScroll) e;
                    // return new GuiScroll(player, scroll.getScrollStack()); // pass the scroll's ItemStack
                }
            }

            // fallback: current held item
            int handSlot = player.inventory.currentItem;
            return new GuiScroll(player, handSlot);
        }
        return null;
    }

}
