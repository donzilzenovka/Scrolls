package com.drzenovka.scrolls.client.renderer.item;

import com.drzenovka.scrolls.common.item.ItemInkBottle;
import com.drzenovka.scrolls.common.util.ColorUtils;
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
        return  type == ItemRenderType.INVENTORY ||
                type == ItemRenderType.FIRST_PERSON_MAP ||
                type == ItemRenderType.EQUIPPED ||        // Held in third-person view
                type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack stack, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        // --- NEW: Apply necessary GL transformations based on render type ---
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED) {
            // Move to the item's standard held position (usually a good starting point)
            // Note: These values are typical defaults, you may need to adjust them for a scroll.
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F); // Center the rendering at 0.5, 0.5, 0.5

            if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                // Apply first-person specific transformations if you want it closer/rotated differently
                // (Often necessary if you want a custom 3D look, but maybe not for a 2D icon)
            }
            // Scaling to match standard icon size in the world (adjust scale as needed)
            GL11.glScalef(1.0F, 1.0F, 1.0F);
        }

        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);

        IIcon base = stack.getItem().getIcon(stack, 0);
        IIcon overlay = stack.getItem().getIcon(stack, 1);

        // Draw base bottle fully
        //drawIcon(base, 0, 0, 16, 16);

        // Get ink level ratio
        int max = ItemInkBottle.MAX_USES;
        int left = max - ItemInkBottle.getUses(stack);
        float ratio = (float) left / max;

        if (ratio <= 0f) return; // empty, skip overlay

        // Draw overlay using full UVs, but clip the quad vertically
        drawPartialIconVertical(overlay, 0, 0, 16, 16, stack);
        drawIcon(base, 0, 0, 16, 16);

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED) {
            GL11.glPopMatrix();
        }
    }

    // -------- Draw full icon --------
    private void drawIcon(IIcon icon, int x, int y, int w, int h) {
        Tessellator t = Tessellator.instance;

        // Enable blending for alpha
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);



        //GL11.glColor4f(1f, 1f, 1f, 1f); // white with full alpha

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
    private void drawPartialIconVertical(IIcon icon, int x, int y, int w, int h, ItemStack stack) {

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int dyeMeta = stack.getItemDamage();
        float r = ColorUtils.GL11_COLOR_VALUES[dyeMeta][0];
        float g = ColorUtils.GL11_COLOR_VALUES[dyeMeta][1];
        float b = ColorUtils.GL11_COLOR_VALUES[dyeMeta][2];
        GL11.glColor4f(r, g, b, 1f);

        float uMin = icon.getMinU();
        float uMax = icon.getMaxU();
        float vMin = icon.getMinV();
        float vMax = icon.getMaxV();

        int maxUses = ItemInkBottle.MAX_USES;   // 8
        int leftUses = maxUses - ItemInkBottle.getUses(stack);

        // Your visual spec:
        int startPixels = 10;  // full
        int minPixels   = 2;   // last visible sliver

        // How many uses consumed
        int used = maxUses - leftUses;

        // Start at 10px → decrease by 1px per use
        int drawHeight = startPixels - used;

        // Clamp so the last 2px always show
        if (drawHeight < minPixels) drawHeight = minPixels;

        int yStart = y + (h - drawHeight);

        float interp = (float) drawHeight / (float) h;
        float vCut = vMax - (vMax - vMin) * interp;

        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();
        t.addVertexWithUV(x,       yStart + drawHeight, 0, uMin, vMax);
        t.addVertexWithUV(x + w,   yStart + drawHeight, 0, uMax, vMax);
        t.addVertexWithUV(x + w,   yStart,              0, uMax, vCut);
        t.addVertexWithUV(x,       yStart,              0, uMin, vCut);
        t.draw();

        GL11.glColor4f(1f, 1f, 1f, 1f);
    }


}
