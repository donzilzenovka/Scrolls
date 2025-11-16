package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.item.ItemScroll;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeColoredScroll implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundPaper = false;
        boolean foundFeather = false;
        boolean foundDye = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (stack.getItem() == ModItems.paperColored ||
                stack.getItem() == Items.paper) {
                if (foundPaper) return false;
                foundPaper = true;
            }
            else if (stack.getItem() == net.minecraft.init.Items.feather) {
                if (foundFeather) return false;
                foundFeather = true;
            }
            else if (stack.getItem() instanceof ItemDye) {
                if (foundDye) return false;
                foundDye = true;
            }
            else {
                return false; // invalid extra items
            }
        }

        return foundPaper && foundFeather && foundDye;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack paper = null;
        int dyeMeta = -1;

        // find paper & dye
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (stack.getItem() == ModItems.paperColored ||
                stack.getItem() == Items.paper) {
                paper = stack;
            } else if (stack.getItem() instanceof ItemDye) {
                dyeMeta = stack.getItemDamage();
            }
        }

        if (paper == null || dyeMeta == -1)
            return null;

        // output will be a scroll, same paper colour
        ItemStack result = new ItemStack(ModItems.scrollColored, 1, paper.getItemDamage());

        // convert 0–15 dye to your internal colour
        int inkColor = 15 - dyeMeta; // same as stamp invert logic
        int paperColor = paper.getItemDamage();

        // set ink color NBT
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(ItemScroll.INK_COLOR, inkColor);
        tag.setInteger(ItemScroll.PAPER_COLOR, paperColor);

        // (optional) pre-fill empty page text if you want
        tag.setString(ItemScroll.NBT_PAGE, "");
        tag.setString(ItemScroll.NBT_AUTHOR, "");

        result.setTagCompound(tag);
        return result;
    }

    @Override
    public int getRecipeSize() { return 3; }

    @Override
    public ItemStack getRecipeOutput() {
        // generic preview
        return new ItemStack(ModItems.scrollColored);
    }
}
