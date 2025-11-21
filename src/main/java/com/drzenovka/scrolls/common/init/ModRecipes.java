package com.drzenovka.scrolls.common.init;

import static com.drzenovka.scrolls.common.util.ColorUtils.DYE_NAME_LIST;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init() {

        for (int i = 1; i < DYE_NAME_LIST.length; i++) {
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(new ItemStack(ModItems.paperColored, 1, i), Items.paper, DYE_NAME_LIST[i]));
        }

        // debug stamp recipe
        GameRegistry
            .addShapelessRecipe(new ItemStack(ModItems.stamp), new ItemStack(Items.potato), new ItemStack(Items.stick));

        // Ink Bottles
        for (int i = 1; i < DYE_NAME_LIST.length; i++) {
            GameRegistry.addRecipe(
                new ShapelessOreRecipe(
                    new ItemStack(ModItems.inkBottle, 1, i),
                    new ItemStack(Items.potionitem, 1, 0),
                    DYE_NAME_LIST[i]));
        }

        GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.inkCauldron),
            "I I",
            "IBI",
            "III",
            'I', Items.iron_ingot,
            'B', Items.bucket);

        // StampedScrolls
        GameRegistry.addRecipe(new RecipeStampScroll());
        GameRegistry.addRecipe(new RecipeScroll());

        RecipeSorter.register(
            "scrolls:recipe_stamp_scroll",
            RecipeStampScroll.class,
            RecipeSorter.Category.SHAPELESS,
            "after:minecraft:shapeless");

        RecipeSorter.register(
            "scrolls:recipe_scroll",
            RecipeScroll.class,
            RecipeSorter.Category.SHAPELESS,
            "after:minecraft:shapeless");
    }
}
