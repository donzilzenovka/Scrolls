package com.drzenovka.scrolls.client.renderer;

import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.util.ColorUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TESRInkCauldron extends TileEntitySpecialRenderer {

    private final Minecraft mc = Minecraft.getMinecraft();



    private void renderInkCauldron(TileEntityInkCauldron te, double x, double y, double z, float partialTicks) {

        int level = te.getLevel();
        if (level <= 0) return; // empty, nothing to render

        float fillHeight = (float) level / 3.0f * 0.625f; // vanilla cauldron max height ~0.625 blocks

        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        IIcon icon = Blocks.water.getIcon(1, 0); // top face of water

        // Set color: water or ink
        if (te.isWater()) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
        } else {
            int meta = te.getColorMeta();
            float r = ColorUtils.GL11_COLOR_VALUES[meta][0];
            float g = ColorUtils.GL11_COLOR_VALUES[meta][1];
            float b = ColorUtils.GL11_COLOR_VALUES[meta][2];
            GL11.glColor4f(r, g, b, 1f);
        }

        Tessellator t = Tessellator.instance;
        t.startDrawingQuads();

        // Draw a flat quad for the fluid surface inside the cauldron
        double minX = x + 0.125;
        double maxX = x + 0.875;
        double minZ = z + 0.125;
        double maxZ = z + 0.875;
        double yLevel = y + 0.0625 + fillHeight; // offset inside cauldron

        float uMin = icon.getMinU();
        float uMax = icon.getMaxU();
        float vMin = icon.getMinV();
        float vMax = icon.getMaxV();

        t.addVertexWithUV(minX, yLevel, minZ, uMin, vMin);
        t.addVertexWithUV(maxX, yLevel, minZ, uMax, vMin);
        t.addVertexWithUV(maxX, yLevel, maxZ, uMax, vMax);
        t.addVertexWithUV(minX, yLevel, maxZ, uMin, vMax);

        t.draw();

        GL11.glColor4f(1f, 1f, 1f, 1f); // reset color
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        if (!(te instanceof TileEntityInkCauldron)) return;
        renderInkCauldron((TileEntityInkCauldron) te, x, y, z, partialTicks);
    }

}
