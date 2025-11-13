package com.drzenovka.scrolls.client.gui;

import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ScrollTextFormatter {

    /** Maximum line width in pixels for the scroll */
    public static final int MAX_LINE_WIDTH = 70;

    /**
     * Wrap text into multiple lines based on pixel width.
     */
    public static String[] wrapText(FontRenderer font, String text) {
        List<String> lines = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        for (char c : text.toCharArray()) {
            String test = current.toString() + c;
            if (font.getStringWidth(test) > MAX_LINE_WIDTH && current.length() > 0) {
                lines.add(current.toString());
                current = new StringBuilder();
            }
            current.append(c);
        }

        if (current.length() > 0) {
            lines.add(current.toString());
        }

        return lines.toArray(new String[0]);
    }
}
