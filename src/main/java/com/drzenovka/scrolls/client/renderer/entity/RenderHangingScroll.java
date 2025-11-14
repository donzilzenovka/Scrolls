package com.drzenovka.scrolls.client.renderer.entity;

import com.drzenovka.scrolls.client.gui.ScrollTextFormatter;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHangingScroll extends Render {

    private static final ResourceLocation SCROLL_TEXTURE =
        new ResourceLocation("scrolls", "textures/entity/hanging_scroll.png");

    // pixel width limit matching GuiScroll proportions
    private static final int MAX_LINE_WIDTH = ScrollTextFormatter.MAX_LINE_WIDTH;
    private static final int LINE_SPACING = 10; // pixels between lines at render scale

    public RenderHangingScroll() {
        this.shadowSize = 0.0F;
    }

    @Override
    public void doRender(net.minecraft.entity.Entity entity, double x, double y, double z,
                         float yaw, float partialTicks) {
        if (!(entity instanceof EntityHangingScroll scroll)) return;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(scroll.rotationYaw, 0, 1, 0);
        bindEntityTexture(scroll);

        // Draw scroll background
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0F, 0F); GL11.glVertex3f(-0.5F,  0.5F, 0F);
        GL11.glTexCoord2f(1F, 0F); GL11.glVertex3f( 0.5F,  0.5F, 0F);
        GL11.glTexCoord2f(1F, 1F); GL11.glVertex3f( 0.5F, -0.5F, 0F);
        GL11.glTexCoord2f(0F, 1F); GL11.glVertex3f(-0.5F, -0.5F, 0F);
        GL11.glEnd();

        // Prepare text render
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        String rawText = scroll.getScrollText();
        if (!rawText.isEmpty()) {

            // Split on stored newlines, then wrap each
            String[] logicalLines = rawText.split("\n");
            java.util.List<String> wrappedLines = new java.util.ArrayList<>();

            for (String line : logicalLines) {
                for (String wrapped : ScrollTextFormatter.wrapText(fr, line)) {
                    wrappedLines.add(wrapped);
                }
            }

            // Transform to face outward, scale down to fit scroll
            GL11.glTranslatef(0F, 0F, -0.001F);
            GL11.glRotatef(180F, 0F, 1F, 0F);
            GL11.glScalef(0.005F, -0.005F, 0.005F);

            /*
            // Render each line centered
            int totalHeight = wrappedLines.size() * (fr.FONT_HEIGHT + 1);
            int startY = -totalHeight / 2;

            for (int i = 0; i < wrappedLines.size(); i++) {
                String line = wrappedLines.get(i);
                int width = fr.getStringWidth(line);
                fr.drawString(line, -width / 2, startY + i * LINE_SPACING, 0x3F2F1A);
            }
             */
            // Render each line left-aligned, starting from top of scroll
            int totalHeight = wrappedLines.size() * (fr.FONT_HEIGHT + 1);
            //int startY = -totalHeight / 2;

            // Small offsets so text isn’t glued to the edge
            final int LEFT_MARGIN = -32; // tweak this for desired positioning
            final int TOP_MARGIN = -55;     // vertical offset from top of scroll (tweak as needed)

            for (int i = 0; i < wrappedLines.size(); i++) {
                String line = wrappedLines.get(i);
                fr.drawString(line, LEFT_MARGIN, TOP_MARGIN + i * LINE_SPACING, 0x3F2F1A);
            }
        }

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(net.minecraft.entity.Entity entity) {
        return SCROLL_TEXTURE;
    }
}
