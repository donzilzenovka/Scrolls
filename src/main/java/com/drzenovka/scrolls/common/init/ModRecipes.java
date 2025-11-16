package com.drzenovka.scrolls.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        // Crafting recipe: Paper + Feather + Ink Sac
        //GameRegistry.addRecipe(new ShapelessOreRecipe(
        //    new ItemStack(ModItems.scrollColored),
        //    "quill",
        //    "paper",
        //    "ink"
        //));

        //colored paper
        for (int i = 1; i < 16; i++) {
            ItemStack result = new ItemStack(ModItems.paperColored, 1, i);
            GameRegistry.addShapelessRecipe(result,
                new ItemStack(Items.paper),
                new ItemStack(Items.dye, 1, 15 - i) // Minecraft dye colors are reversed order
            );
        }

        ////colored scrolls
        /*
        for (int i = 0; i < 15; i++) {
            ItemStack result = new ItemStack(ModItems.scrollColored, 1, i);
            GameRegistry.addShapelessRecipe(result,
                new ItemStack(ModItems.paperColored, 1, i),
                new ItemStack(Items.feather),
                new ItemStack(Items.dye, 1, 0)
            );
        }

         */

        //StampedScrolls
        GameRegistry.addRecipe(new RecipeStampScroll());
        GameRegistry.addRecipe(new RecipeColoredScroll());
    }
}
