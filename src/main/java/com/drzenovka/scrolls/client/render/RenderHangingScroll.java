package com.drzenovka.scrolls.client.render;

import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderHangingScroll extends Render {

    private static final ResourceLocation SCROLL_TEXTURE =
        new ResourceLocation("scrolls", "textures/entity/hanging_scroll.png");

    public RenderHangingScroll() {
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(net.minecraft.entity.Entity entity, double x, double y, double z,
                         float entityYaw, float partialTicks) {
        if (!(entity instanceof EntityHangingScroll)) return;
        EntityHangingScroll scroll = (EntityHangingScroll) entity;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        bindEntityTexture(scroll);

        // TODO: render scroll model or quad here

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(net.minecraft.entity.Entity entity) {
        return SCROLL_TEXTURE;
    }
}
