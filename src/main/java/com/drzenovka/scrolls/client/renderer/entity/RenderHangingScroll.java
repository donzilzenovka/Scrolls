package com.drzenovka.scrolls.client.renderer.entity;

import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHangingScroll extends Render {

    private static final ResourceLocation SCROLL_TEXTURE =
        new ResourceLocation("scrolls", "textures/entity/hanging_scroll.png");

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

        // Draw scroll quad
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0F, 0F); GL11.glVertex3f(-0.5F,  0.5F, 0F);
        GL11.glTexCoord2f(1F, 0F); GL11.glVertex3f( 0.5F,  0.5F, 0F);
        GL11.glTexCoord2f(1F, 1F); GL11.glVertex3f( 0.5F, -0.5F, 0F);
        GL11.glTexCoord2f(0F, 1F); GL11.glVertex3f(-0.5F, -0.5F, 0F);
        GL11.glEnd();

        // Render text
        GL11.glTranslatef(0F, 0F, -0.001F); // slightly above surface
        GL11.glRotatef(180F,0F,1F, 0F);
        GL11.glScalef(0.005F, -0.005F, 0.005F);
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        String text = "Butts";
        text = scroll.getScrollText();


        if (!text.isEmpty()) {
            int width = fr.getStringWidth(text);
            fr.drawString(text, -width / 2, 0, 0x3F2F1A);
        }

        //GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(net.minecraft.entity.Entity entity) {
        return SCROLL_TEXTURE;
    }
}
