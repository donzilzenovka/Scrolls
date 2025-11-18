package com.drzenovka.scrolls.client.renderer.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.drzenovka.scrolls.client.gui.ScrollTextFormatter;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import com.drzenovka.scrolls.common.util.ColorUtils;

public class RenderHangingScroll extends Render {

    private static final ResourceLocation SCROLL_TEXTURE = new ResourceLocation(
        "scrolls",
        "textures/entity/hanging_scroll.png");
    private static final int LINE_SPACING = 10; // pixels between lines at render scale

    public RenderHangingScroll() {
        this.shadowSize = 0f;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return SCROLL_TEXTURE;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        if (!(entity instanceof EntityHangingScroll scroll)) return;

        // Load scroll data
        String text = scroll.getScrollText();
        int paperColor = scroll.getPaperColor();
        int stampCount = scroll.getStampCount();
        int[] stampColors = scroll.getStampColors();
        int inkColor = scroll.getInkColor();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(scroll.rotationYaw, 0, 1, 0);

        try {
            drawBackground(paperColor);
            GL11.glDisable(GL11.GL_LIGHTING);
            drawText(text, inkColor);
            drawStamps(stampCount, stampColors);
        } finally {
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glPopMatrix();
    }

    private void drawBackground(int paperColor) {
        bindEntityTexture(null); // texture is already SCROLL_TEXTURE
        float[] color = ColorUtils.GL11_COLOR_VALUES[paperColor];

        GL11.glColor4f(color[0], color[1], color[2], 1f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0F, 0F);
        GL11.glVertex3f(-0.5F, 0.5F, 0F);
        GL11.glTexCoord2f(1F, 0F);
        GL11.glVertex3f(0.5F, 0.5F, 0F);
        GL11.glTexCoord2f(1F, 1F);
        GL11.glVertex3f(0.5F, -0.5F, 0F);
        GL11.glTexCoord2f(0F, 1F);
        GL11.glVertex3f(-0.5F, -0.5F, 0F);
        GL11.glEnd();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

    private void drawText(String text, Integer inkColor) {
        if (text.isEmpty()) return;

        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        GL11.glPushMatrix();
        GL11.glTranslatef(0f, 0f, -0.001f); // slightly in front of scroll
        GL11.glRotatef(180f, 0, 1, 0);
        GL11.glScalef(0.005f, -0.005f, 0.005f);

        List<String> wrappedLines = new ArrayList<>();
        for (String line : text.split("\n")) {
            wrappedLines.addAll(java.util.Arrays.asList(ScrollTextFormatter.wrapText(fr, line)));
        }

        final int LEFT_MARGIN = -32;
        final int TOP_MARGIN = -55;

        int color = ColorUtils.rgbToHex(
            ColorUtils.GL11_COLOR_VALUES[inkColor][0],
            ColorUtils.GL11_COLOR_VALUES[inkColor][1],
            ColorUtils.GL11_COLOR_VALUES[inkColor][2]);

        for (int i = 0; i < wrappedLines.size(); i++) {
            String line = wrappedLines.get(i);
            fr.drawString(line, LEFT_MARGIN, TOP_MARGIN + i * LINE_SPACING, color);
        }

        GL11.glPopMatrix();
    }

    private void drawStamps(int count, int[] colors) {
        if (count <= 0 || colors == null) return;

        Minecraft.getMinecraft()
            .getTextureManager()
            .bindTexture(new ResourceLocation("scrolls", "textures/gui/stamp_texture.png"));

        GL11.glPushMatrix();
        GL11.glTranslatef(0f, 0f, -0.002f); // slightly in front of text
        GL11.glRotatef(180f, 0, 1, 0);
        GL11.glScalef(0.05f, -0.05f, 0.05f);

        for (int i = 0; i < count; i++) {
            int dye = colors[i];
            if (dye < 0 || dye >= ColorUtils.GL11_COLOR_VALUES.length) continue;

            float[] c = ColorUtils.GL11_COLOR_VALUES[dye];
            GL11.glColor4f(c[0], c[1], c[2], 1f);

            GL11.glPushMatrix();
            // smaller offsets, proportional to scale
            // GL11.glScalef(1f, 1f, 1f);
            GL11.glTranslatef(-3F + i * 0.9F, 5.5F, -0.01F + i * 0.01f);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex3f(-1, 1, 0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex3f(1, 1, 0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex3f(1, -1, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex3f(-1, -1, 0);
            GL11.glEnd();

            GL11.glPopMatrix();
        }

        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glPopMatrix();
    }
}
