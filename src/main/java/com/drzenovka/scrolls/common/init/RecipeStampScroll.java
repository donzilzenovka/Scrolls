package com.drzenovka.scrolls.common.init;

import static com.drzenovka.scrolls.common.init.ModOreDict.INK;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.util.DyeColorMap;
import com.drzenovka.scrolls.common.util.Utils;

public class RecipeStampScroll implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundScroll = false;
        boolean foundStamp = false;
        boolean foundDye = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (stack.getItem() == ModItems.scroll) {
                if (foundScroll) return false; // only one allowed
                foundScroll = true;

                // Prevent adding stamps if already 3
                NBTTagCompound tag = stack.getTagCompound();
                if (tag != null && tag.getInteger("stampCount") >= 3) return false;
            } else if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).field_150939_a;
                if (block == ModBlocks.stamp) {
                    if (foundStamp) return false;
                    foundStamp = true;
                }
            } else if (Utils.isOreDictItem(stack, INK)) {
                if (foundDye) return false;
                foundDye = true;
            } else {
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

            if (stack.getItem() == ModItems.scroll) {
                scroll = stack;
            } else if (Utils.isOreDictItem(stack, INK)) {
                dyeMeta = DyeColorMap.getColorForStack(stack);
            }
        }

        if (scroll == null || dyeMeta == -1) return null;

        // copy original scroll
        ItemStack output = scroll.copy();

        int color = dyeMeta;

        // read or create NBT
        NBTTagCompound tag = output.getTagCompound();
        if (tag == null) tag = new NBTTagCompound();

        int count = tag.getInteger("stampCount");
        if (count < 3) {
            tag.setInteger("stampColor" + count, color);
            tag.setInteger("stampCount", count + 1);
        }

        output.setTagCompound(tag);
        return output;
    }

    @Override
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(ModItems.scroll);
    }

}
