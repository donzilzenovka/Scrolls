package com.drzenovka.scrolls.common.init;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ModOreDict {
    private ModOreDict() {};

    public static final String DYE_WHITE      = "dyeWhite";
    public static final String DYE_ORANGE     = "dyeOrange";
    public static final String DYE_MAGENTA    = "dyeMagenta";
    public static final String DYE_LIGHT_BLUE = "dyeLightBlue";
    public static final String DYE_YELLOW     = "dyeYellow";
    public static final String DYE_LIME       = "dyeLime";
    public static final String DYE_PINK       = "dyePink";
    public static final String DYE_GRAY       = "dyeGray";
    public static final String DYE_LIGHT_GRAY = "dyeLightGray";
    public static final String DYE_CYAN       = "dyeCyan";
    public static final String DYE_PURPLE     = "dyePurple";
    public static final String DYE_BLUE       = "dyeBlue";
    public static final String DYE_BROWN      = "dyeBrown";
    public static final String DYE_GREEN      = "dyeGreen";
    public static final String DYE_RED        = "dyeRed";
    public static final String DYE_BLACK      = "dyeBlack";

    public static final String INK            = "ink";
    public static final String PARCHMENT      = "parchment";
    public static final String QUILL          = "quill";
    public static final String WAX            = "wax";

    public static void init() {

        OreDictionary.registerOre(PARCHMENT, new ItemStack(Items.paper));
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre(PARCHMENT, new ItemStack(ModItems.paperColored, 1, i));
        }

        OreDictionary.registerOre(QUILL, new ItemStack(Items.feather));

        // Register each wax color in the OreDictionary and local map
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            String color = COLOR_NAMES[i];
            OreDictionary.registerOre(WAX + capitalize(color), new ItemStack(ModItems.wax, 1, i));
        }

        OreDictionary.registerOre(DYE_WHITE, new ItemStack(Items.dye, 1, 0));
        OreDictionary.registerOre(DYE_ORANGE, new ItemStack(Items.dye, 1, 1));
        OreDictionary.registerOre(DYE_MAGENTA, new ItemStack(Items.dye, 1, 2));
        OreDictionary.registerOre(DYE_LIGHT_BLUE, new ItemStack(Items.dye, 1, 3));
        OreDictionary.registerOre(DYE_YELLOW, new ItemStack(Items.dye, 1, 4));
        OreDictionary.registerOre(DYE_LIME, new ItemStack(Items.dye, 1, 5));
        OreDictionary.registerOre(DYE_PINK, new ItemStack(Items.dye, 1, 6));
        OreDictionary.registerOre(DYE_GRAY, new ItemStack(Items.dye, 1, 7));
        OreDictionary.registerOre(DYE_LIGHT_GRAY, new ItemStack(Items.dye, 1, 8));
        OreDictionary.registerOre(DYE_CYAN, new ItemStack(Items.dye, 1, 9));
        OreDictionary.registerOre(DYE_PURPLE, new ItemStack(Items.dye, 1, 10));
        OreDictionary.registerOre(DYE_BLUE, new ItemStack(Items.dye, 1, 11));
        OreDictionary.registerOre(DYE_BROWN, new ItemStack(Items.dye, 1, 12));
        OreDictionary.registerOre(DYE_GREEN, new ItemStack(Items.dye, 1, 13));
        OreDictionary.registerOre(DYE_RED, new ItemStack(Items.dye, 1, 14));
        OreDictionary.registerOre(DYE_BLACK, new ItemStack(Items.dye, 1, 15));

        String[] vanillaDyes = {
            DYE_WHITE, DYE_ORANGE, DYE_MAGENTA, DYE_LIGHT_BLUE,
            DYE_YELLOW, DYE_LIME, DYE_PINK, DYE_GRAY,
            DYE_LIGHT_GRAY, DYE_CYAN, DYE_PURPLE, DYE_BLUE,
            DYE_BROWN, DYE_GREEN, DYE_RED, DYE_BLACK
        };

        for (String dye : vanillaDyes) {
            registerAllOres(INK, dye);
        }
    }

    private static String capitalize(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static void registerAllOres(String target, String source) {
        if (target.equals(source)) return;

        // getOres returns a *mutable* list used by other mods, so copy it:
        List<ItemStack> stacks = new ArrayList<>(OreDictionary.getOres(source));

        for (ItemStack stack : stacks) {
            // Register a *copy* so nobody accidentally mutates the original
            OreDictionary.registerOre(target, stack.copy());
        }
    }
}
