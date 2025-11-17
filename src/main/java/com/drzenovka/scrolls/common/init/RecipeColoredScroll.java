package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.util.DyeColorMap;
import com.drzenovka.scrolls.common.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


import com.drzenovka.scrolls.common.item.ItemScroll;

import static com.drzenovka.scrolls.common.init.ModOreDict.INK;
import static com.drzenovka.scrolls.common.init.ModOreDict.PARCHMENT;
import static com.drzenovka.scrolls.common.init.ModOreDict.QUILL;


public class RecipeColoredScroll implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        boolean foundPaper = false;
        boolean foundFeather = false;
        boolean foundDye = false;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null) continue;

            if (Utils.isOreDictItem(stack, PARCHMENT)) {
                if (foundPaper) return false;
                foundPaper = true;
            } else if (Utils.isOreDictItem(stack, QUILL)) {
                if (foundFeather) return false;
                foundFeather = true;
            } else if (Utils.isOreDictItem(stack, INK)) {
                if (foundDye) return false;
                foundDye = true;
            } else {
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

            if (Utils.isOreDictItem(stack, PARCHMENT)) {
                paper = stack;
            } else if (Utils.isOreDictItem(stack, INK)) {
                dyeMeta = DyeColorMap.getColorForStack(stack);
            }
        }

        if (paper == null || dyeMeta == -1) return null;

        // output will be a scroll, same paper colour
        ItemStack result = new ItemStack(ModItems.scrollColored, 1, paper.getItemDamage());

        int inkColor = dyeMeta;
        System.out.println("dyeMeta:" + dyeMeta);
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
    public int getRecipeSize() {
        return 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        // generic preview
        return new ItemStack(ModItems.scrollColored);
    }
}
