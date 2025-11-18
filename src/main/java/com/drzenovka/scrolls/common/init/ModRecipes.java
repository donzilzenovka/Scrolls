package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.util.ColorUtils;
import com.drzenovka.scrolls.common.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.drzenovka.scrolls.common.util.ColorUtils.DYE_NAME_LIST;

public class ModRecipes {

    public static void init() {

        for(int i = 1; i<DYE_NAME_LIST.length; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(
                new ItemStack(ModItems.paperColored, 1, i), Items.paper, DYE_NAME_LIST[i]));
        }

        //debug stamp recipe
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.stamp),
        new ItemStack(Items.potato),
        new ItemStack(Items.stick));

        // Ink Bottles
        for(int i = 1; i<DYE_NAME_LIST.length; i++) {
            GameRegistry.addRecipe(new ShapelessOreRecipe(
                new ItemStack(ModItems.inkBottle, 1, i), new ItemStack(Items.potionitem, 1, 0), DYE_NAME_LIST[i]));
        }


        // StampedScrolls
        GameRegistry.addRecipe(new RecipeStampScroll());
        GameRegistry.addRecipe(new RecipeColoredScroll());
    }
}
