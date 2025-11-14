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

    public static final float[][] GL11_COLOR_VALUES = new float[][] {
        {1f, 1f, 1f},   // white
        {1.0f, 0.13f, 0.01f},   // orange
        {1.00f, 0.09f, 0.40f},  // magenta
        {0.15f, 0.62f, 0.94f},  // light blue
        {1.00f, 1.00f, 0.00f},  // yellow
        {0.00f, 0.68f, 0.05f},  // lime
        {1.00f, 0.31f, 0.49f},  // pink
        {0.11f, 0.11f, 0.11f},  // gray
        {0.34f, 0.34f, 0.34f},  // light gray
        {0.11f, 0.14f, 0.32f},  // cyan
        {1.00f, 0.03f, 0.49f},  // purple
        {0.02f, 0.03f, 0.57f},  // blue
        {0.22f, 0.08f, 0.03f},  // brown
        {0.00f, 0.14f, 0.02f},  // green
        {1.00f, 0.05f, 0.05f},  // red
        {0.02f, 0.02f, 0.02f}   // black
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

    public static int rgbToHex(float r, float g, float b) {
        int ri = (int) (r * 255.0f) & 0xFF;
        int gi = (int) (g * 255.0f) & 0xFF;
        int bi = (int) (b * 255.0f) & 0xFF;

        return (ri << 16) | (gi << 8) | bi;
    }
}
