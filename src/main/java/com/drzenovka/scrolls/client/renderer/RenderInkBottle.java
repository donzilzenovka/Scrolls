package com.drzenovka.scrolls.client.renderer;

import com.drzenovka.scrolls.common.item.ItemInkBottle;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderInkBottle implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
        // Only custom-render in inventory / GUI
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);

        IIcon base = stack.getItem().getIcon(stack, 0);
        IIcon overlay = stack.getItem().getIcon(stack, 1);

        // Draw base bottle fully
        drawIcon(base, 0, 0, 16, 16);

        // Get ink level ratio
        int max = ItemInkBottle.MAX_USES;
        int left = ItemInkBottle.getUses(stack);
        float ratio = (float) left / max;

        if (ratio <= 0f) return; // empty, skip overlay

        // Draw overlay using full UVs, but clip the quad vertically
        drawPartialIconVertical(overlay, 0, 0, 16, 16, ratio);
    }

    // -------- Draw full icon --------
    private void drawIcon(IIcon icon, int x, int y, int w, int h) {
        Tessellator t = Tessellator.instance;

        // Enable blending for alpha
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1f, 1f, 1f, 1f); // white with full alpha

        t.startDrawingQuads();
        t.addVertexWithUV(x,     y + h, 0, icon.getMinU(), icon.getMaxV());
        t.addVertexWithUV(x + w, y + h, 0, icon.getMaxU(), icon.getMaxV());
        t.addVertexWithUV(x + w, y,     0, icon.getMaxU(), icon.getMinV());
        t.addVertexWithUV(x,     y,     0, icon.getMinU(), icon.getMinV());
        t.draw();

        // Reset color to avoid affecting other rendering
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }


    // -------- Draw partial icon (bottom-to-top filling) --------
    private void drawPartialIconVertical(IIcon icon, int x, int y, int w, int h, float ratio) {

        // Enable blending for alpha
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glColor4f(1f, 1f, 1f, 1f); // white with full alpha

        // Texture UV coordinates
        float uMin = icon.getMinU();
        float uMax = icon.getMaxU();
        float vMin = icon.getMinV();
        float vMax = icon.getMaxV();

        // How tall the visible area is
        int drawHeight = (int) (h * ratio);

        // vCut marks the UV coordinate for the LOWER boundary of the visible area.
        // Because v increases downward, we interpolate from MAX V upward.
        float vCut = vMax - (vMax - vMin) * ratio;

        // y-position of the visible area (shifted upward by how tall the filled area is)
        int yStart = y + (h - drawHeight);

        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();

        // Draw only the lower portion: (bottom up)
        t.addVertexWithUV(x,       yStart + drawHeight, 0, uMin, vMax);
        t.addVertexWithUV(x + w,   yStart + drawHeight, 0, uMax, vMax);
        t.addVertexWithUV(x + w,   yStart,              0, uMax, vCut);
        t.addVertexWithUV(x,       yStart,              0, uMin, vCut);

        t.draw();

        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

}
