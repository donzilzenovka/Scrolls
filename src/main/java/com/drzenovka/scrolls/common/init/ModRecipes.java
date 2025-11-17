package com.drzenovka.scrolls.common.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 0), Items.paper, ModOreDict.DYE_WHITE));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 1), Items.paper, ModOreDict.DYE_ORANGE));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 2), Items.paper, ModOreDict.DYE_MAGENTA));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 3), Items.paper, ModOreDict.DYE_LIGHT_BLUE));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 4), Items.paper, ModOreDict.DYE_YELLOW));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 5), Items.paper, ModOreDict.DYE_LIME));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 6), Items.paper, ModOreDict.DYE_PINK));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 7), Items.paper, ModOreDict.DYE_GRAY));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 8), Items.paper, ModOreDict.DYE_LIGHT_GRAY));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 9), Items.paper, ModOreDict.DYE_CYAN));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 10), Items.paper, ModOreDict.DYE_PURPLE));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 11), Items.paper, ModOreDict.DYE_BLUE));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 12), Items.paper, ModOreDict.DYE_BROWN));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 13), Items.paper, ModOreDict.DYE_GREEN));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 14), Items.paper, ModOreDict.DYE_RED));
        GameRegistry.addRecipe(new ShapelessOreRecipe( new ItemStack(ModItems.paperColored, 1, 15), Items.paper, ModOreDict.DYE_BLACK));


        //debug stamp recipe
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.stamp),
        new ItemStack(Items.potato),
        new ItemStack(Items.stick));


        // StampedScrolls
        GameRegistry.addRecipe(new RecipeStampScroll());
        GameRegistry.addRecipe(new RecipeColoredScroll());
    }
}
