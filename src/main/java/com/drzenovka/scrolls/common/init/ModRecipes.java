package com.drzenovka.scrolls.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

    public static void init() {

        // Crafting recipe: Paper + Feather + Ink Sac
        GameRegistry.addRecipe(new ShapelessOreRecipe(
            new ItemStack(ModItems.scroll),
            "quill",
            "paper",
            "ink"
        ));
    }
}
