package com.drzenovka.scrolls.common.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotScrollCrafting extends SlotCrafting {

    private final IInventory matrix;

    public SlotScrollCrafting(EntityPlayer player, IInventory craftMatrix, IInventory resultInventory, int slotIndex, int x, int y){
        super(player, craftMatrix, resultInventory, slotIndex, x, y);
        this.matrix = craftMatrix;
    }
    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack craftedResult){
        super.onPickupFromSlot(player, craftedResult);

        //Remove entire ink bottle AFTER the result is taken
        for (int i = 0; i < this.matrix.getSizeInventory(); i++){
            matrix.setInventorySlotContents(i, null); //consume fully
                break;
        }
    }
}
