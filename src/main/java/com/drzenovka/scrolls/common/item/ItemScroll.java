package com.drzenovka.scrolls.common.item;

import com.drzenovka.scrolls.gui.GuiScroll;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class ItemScroll extends Item {

    public static final String NBT_PAGE = "page";

    /** Initialize NBT for a new scroll */
    private void initNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setString(NBT_PAGE, "");
        }
    }

    /** Get the text stored on the scroll */
    public String getText(ItemStack stack) {
        initNBT(stack);
        return stack.getTagCompound().getString(NBT_PAGE);
    }

    /** Set the text stored on the scroll */
    public void setText(ItemStack stack, String text) {
        initNBT(stack);
        stack.getTagCompound().setString(NBT_PAGE, text);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        initNBT(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) {
            int handSlot = player.inventory.currentItem;
            Minecraft.getMinecraft().displayGuiScreen(new GuiScroll(player, handSlot));
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
                             int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;

        EnumFacing facing = EnumFacing.getFront(side);
        int posX = x + facing.getFrontOffsetX();
        int posY = y + facing.getFrontOffsetY();
        int posZ = z + facing.getFrontOffsetZ();

        // Spawn pinned scroll entity
        com.drzenovka.scrolls.common.entity.EntityHangingScroll entity =
            new com.drzenovka.scrolls.common.entity.EntityHangingScroll(world, posX, posY, posZ, facing.ordinal(), stack);

        if (entity.onValidSurface()) {
            world.spawnEntityInWorld(entity);

            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
        }

        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, java.util.List list, boolean advanced) {
        list.add("A magical scroll with a single page.");
    }
}
