package com.drzenovka.scrolls.common.util;

import net.minecraft.util.EnumChatFormatting;

public class ColorUtils {

    public static final String[] COLOR_NAMES = {
        "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray",
        "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    };

    public static EnumChatFormatting[] COLOR_ENUMS = {
        EnumChatFormatting.WHITE, EnumChatFormatting.GOLD, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.AQUA,
        EnumChatFormatting.YELLOW, EnumChatFormatting.GREEN, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.DARK_GRAY,
        EnumChatFormatting.GRAY, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.BLUE,
        EnumChatFormatting.GOLD, EnumChatFormatting.DARK_GREEN, EnumChatFormatting.RED, EnumChatFormatting.DARK_GRAY
    };

    /** Returns a safe color name by index (0–15). Defaults to "white" if out of bounds. */
    public static String getColorName(int index) {
        if (index < 0 || index >= COLOR_NAMES.length)
            return "white";
        return COLOR_NAMES[index];
    }

    /** Returns a safe color name by index (0–15). Defaults to "white" if out of bounds. */
    public static EnumChatFormatting getColorEnumName(int index) {
        if (index < 0 || index >= COLOR_ENUMS.length)
            return EnumChatFormatting.WHITE;
        return COLOR_ENUMS[index];
    }
}
