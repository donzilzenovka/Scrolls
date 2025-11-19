package com.drzenovka.scrolls.client.renderer.block;

import com.drzenovka.scrolls.client.core.ClientProxy;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.util.ColorUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderInkCauldron extends TileEntitySpecialRenderer {

    private final Minecraft mc = Minecraft.getMinecraft();


    private void renderInkCauldron(TileEntityInkCauldron te, double x, double y, double z, float partialTicks) {

        int level = te.getLevel();
        //System.out.println("render level: " + level);
        if (level <= 0) return; // empty, nothing to render

        //for setting water height akin to regular cauldron
        float cauldronConstant = 0.1875f; // for setting water height akin to vanilla
        float fillHeight = 0.5f + (level * cauldronConstant) - cauldronConstant;

        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        IIcon icon = ClientProxy.ClientIconHelper.customInkCauldronFluidIcon; // Replace with your actual static reference

        // Check for null just in case of an error
        if (icon == null) return;

        // Set color: water or ink
        if (te.isWater()) {
            icon = Blocks.water.getIcon(1, 0); // top face of water
            GL11.glColor4f(1f, 1f, 1f, 1f);
        } else {
            icon = ClientProxy.ClientIconHelper.customInkCauldronFluidIcon; // custom desaturated water
            int meta = te.getColorMeta();
            float r = ColorUtils.GL11_COLOR_VALUES[meta][0];
            float g = ColorUtils.GL11_COLOR_VALUES[meta][1];
            float b = ColorUtils.GL11_COLOR_VALUES[meta][2];
            GL11.glColor4f(r, g, b, 1f);
        }
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glDisable(GL11.GL_TEXTURE_2D); // Disable texture 2D to set the bright light map
        //GL11.glEnable(GL11.GL_BLEND); // Ensure blending is on if you fixed that previously
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);// for transparency
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        //net.minecraft.client.renderer.OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

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

        t.addVertexWithUV(minX, yLevel, minZ, uMin, vMin); // Top Left (in the Z-plane)
        t.addVertexWithUV(minX, yLevel, maxZ, uMin, vMax); // Bottom Left
        t.addVertexWithUV(maxX, yLevel, maxZ, uMax, vMax); // Bottom Right
        t.addVertexWithUV(maxX, yLevel, minZ, uMax, vMin); // Top Right

        t.draw();

        //GL11.glDisable(GL11.GL_BLEND); // for transparency
        GL11.glPopAttrib();

        // 6. Re-enable lighting and smooth shading for subsequent renders
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glColor4f(1f, 1f, 1f, 1f); // Reset color
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        if (!(te instanceof TileEntityInkCauldron)) return;
        renderInkCauldron((TileEntityInkCauldron) te, x, y, z, partialTicks);

    }

}
