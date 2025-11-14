package com.drzenovka.scrolls.common.init;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeStampScroll implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundScroll = false;
        boolean foundStamp = false;
        boolean foundDye = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (stack.getItem() == ModItems.scrollColored) {
                if (foundScroll) return false; // only one allowed
                foundScroll = true;

                // Prevent adding stamps if already 3
                NBTTagCompound tag = stack.getTagCompound();
                if (tag != null && tag.getInteger("stampCount") >= 3)
                    return false;
            }
            else if (stack.getItem() == ModItems.stamp) {
                if (foundStamp) return false;
                foundStamp = true;
            }
            else if (stack.getItem() instanceof ItemDye) {
                if (foundDye) return false;
                foundDye = true;
            }
            else {
                return false; // any other item invalidates recipe
            }
        }

        return foundScroll && foundStamp && foundDye;
    }


    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack scroll = null;
        int dyeMeta = -1;

        // find scroll + dye meta
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (stack.getItem() == ModItems.scrollColored) {
                scroll = stack;
            } else if (stack.getItem() instanceof ItemDye) {
                dyeMeta = stack.getItemDamage();
            }
        }

        if (scroll == null || dyeMeta == -1) return null;

        // copy original scroll
        ItemStack output = scroll.copy();

        int color = ItemDye.field_150922_c[dyeMeta]; // vanilla dye RGB

        // read or create NBT
        NBTTagCompound tag = output.getTagCompound();
        if (tag == null) tag = new NBTTagCompound();

        int count = tag.getInteger("stampCount");
        if (count < 3) {
            tag.setInteger("stampColor" + count, color);
            tag.setInteger("stampCount", count + 1);
        }

        output.setTagCompound(tag);
        System.out.println(output);
        return output;
    }


    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.scrollColored);
    }

}
