package com.drzenovka.scrolls.client.renderer.entity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderStamp extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);

        final float offset = 7f / 16f;

        // Translate X by offset, Y by 0 (to stay on ground), Z by offset
        GL11.glTranslatef(offset, 0f, offset);

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glColor3f(1f, 1f, 1f);
        this.bindTexture(new ResourceLocation("scrolls:textures/blocks/stamp.png"));

// draw 2x5x2 cube in pixel units
        drawCube();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

    }

    private void drawCube() {
        final float P = 1f / 16f; //world pixel
        final float T = 1f / 32f;  //texture pixel
        final float W = 2f * P;
        final float H = 5f * P;
        //final float U_END = 2f * P;
        //final float V_END = 5f * P;

        final float U_START_SIDE1 = 0f * T;
        final float V_START_SIDE1 = 0f * T;
        final float U_END_SIDE1 = U_START_SIDE1 + 2f * T;
        final float V_END_SIDE1 = V_START_SIDE1 + 5f * T;

        final float U_START_SIDE2 = 2f * T;
        final float V_START_SIDE2 = 0f * T;
        final float U_END_SIDE2 = U_START_SIDE2 + 2f * T;
        final float V_END_SIDE2 = V_START_SIDE2 + 5f * T;

        final float U_START_SIDE3 = 4f * T;
        final float V_START_SIDE3 = 0f * T;
        final float U_END_SIDE3 = U_START_SIDE3 + 2f * T;
        final float V_END_SIDE3 = V_START_SIDE3 + 5f * T;

        final float U_START_SIDE4 = 6f * T;
        final float V_START_SIDE4 = 0f * T;
        final float U_END_SIDE4 = U_START_SIDE4 + 2f * T;
        final float V_END_SIDE4 = V_START_SIDE4 + 5f * T;

        final float U_START_TOP = 8f * T;
        final float V_START_TOP = 0f * T;
        final float U_END_TOP = U_START_TOP + 2f * T;
        final float V_END_TOP = V_START_TOP + 2F * T;

        final float U_START_BOTTOM = 10f * T;
        final float V_START_BOTTOM = 0f * T;
        final float U_END_BOTTOM = U_START_BOTTOM + 2f * T;
        final float V_END_BOTTOM = V_START_BOTTOM + 2f * T;

        GL11.glBegin(GL11.GL_QUADS);

        // --- Top face (Y=H) ---
        // Must be CCW when looking DOWN at the face (i.e., when Y is decreasing)
        // Original was: 0, H, 0 -> W, H, 0 -> W, H, W -> 0, H, W (This is CW when looking down)
        // Corrected (CCW): 0, H, 0 -> 0, H, W -> W, H, W -> W, H, 0
        GL11.glTexCoord2f(U_START_TOP, V_START_TOP);      GL11.glVertex3f(0, H, 0);
        GL11.glTexCoord2f(U_START_TOP, V_END_TOP);   GL11.glVertex3f(0, H, W); // Reversed Z
        GL11.glTexCoord2f(U_END_TOP, V_END_TOP);    GL11.glVertex3f(W, H, W);
        GL11.glTexCoord2f(U_END_TOP, V_START_TOP);   GL11.glVertex3f(W, H, 0); // Reversed Z

        // --- Bottom face (Y=0) ---
        // Must be CCW when looking UP at the face (i.e., when Y is increasing)
        // Original was: 0, 0, 0 -> 0, 0, W -> W, 0, W -> W, 0, 0 (This was already CCW and correct)
        // Keeping it as is:
        GL11.glTexCoord2f(U_START_BOTTOM, V_START_BOTTOM);       GL11.glVertex3f(0, 0, 0);
        GL11.glTexCoord2f(U_START_BOTTOM, V_END_BOTTOM);    GL11.glVertex3f(W, 0, 0);
        GL11.glTexCoord2f(U_END_BOTTOM, V_END_BOTTOM); GL11.glVertex3f(W, 0, W);
        GL11.glTexCoord2f(U_END_BOTTOM, V_START_BOTTOM);    GL11.glVertex3f(0, 0, W);

        // --- Front face (Z=0) --- (Reference, confirmed working)
        GL11.glTexCoord2f(U_START_SIDE1, V_END_SIDE1);    GL11.glVertex3f(0, 0, 0); // Bottom-Left (TL UV)
        GL11.glTexCoord2f(U_START_SIDE1, V_START_SIDE1);       GL11.glVertex3f(0, H, 0); // Top-Left (BL UV)
        GL11.glTexCoord2f(U_END_SIDE1, V_START_SIDE1);    GL11.glVertex3f(W, H, 0); // Top-Right (BR UV)
        GL11.glTexCoord2f(U_END_SIDE1, V_END_SIDE1); GL11.glVertex3f(W, 0, 0); // Bottom-Right (TR UV)

        // --- Back face (Z=W) ---
        // Must be CCW when looking TOWARDS the origin (Z decreasing).
        // Original was: W, 0, W -> 0, 0, W -> 0, H, W -> W, H, W (This was CW)
        // Corrected (CCW): 0, 0, W -> W, 0, W -> W, H, W -> 0, H, W
        GL11.glTexCoord2f(U_START_SIDE2, V_END_SIDE2);    GL11.glVertex3f(0, 0, W);
        GL11.glTexCoord2f(U_END_SIDE2, V_END_SIDE2); GL11.glVertex3f(W, 0, W);
        GL11.glTexCoord2f(U_END_SIDE2, V_START_SIDE2);    GL11.glVertex3f(W, H, W);
        GL11.glTexCoord2f(U_START_SIDE2, V_START_SIDE2);       GL11.glVertex3f(0, H, W);

        // --- Left face (X=0) ---
        // Must be CCW when looking along the +X axis (X increasing).
        // Original was: 0, 0, 0 -> 0, 0, W -> 0, H, W -> 0, H, 0 (This was CCW and correct)
        // Keeping it as is:
        GL11.glTexCoord2f(U_END_SIDE3, V_END_SIDE3); GL11.glVertex3f(0, 0, 0);
        GL11.glTexCoord2f(U_START_SIDE3, V_END_SIDE3);    GL11.glVertex3f(0, 0, W);
        GL11.glTexCoord2f(U_START_SIDE3, V_START_SIDE3);       GL11.glVertex3f(0, H, W);
        GL11.glTexCoord2f(U_END_SIDE3, V_START_SIDE3);    GL11.glVertex3f(0, H, 0);

        // --- Right face (X=W) ---
        // Must be CCW when looking along the -X axis (X decreasing).
        // Original was: W, 0, W -> W, 0, 0 -> W, H, 0 -> W, H, W (This was CW)
        // Corrected (CCW): W, 0, 0 -> W, 0, W -> W, H, W -> W, H, 0
        GL11.glTexCoord2f(U_START_SIDE4, V_END_SIDE4);    GL11.glVertex3f(W, 0, W); // Bottom-Left (TL UV)
        GL11.glTexCoord2f(U_END_SIDE4, V_END_SIDE4); GL11.glVertex3f(W, 0, 0); // Bottom-Right (BL UV)
        GL11.glTexCoord2f(U_END_SIDE4, V_START_SIDE4);    GL11.glVertex3f(W, H, 0); // Top-Right (BR UV)
        GL11.glTexCoord2f(U_START_SIDE4, V_START_SIDE4);       GL11.glVertex3f(W, H, W); // Top-Left (TR UV)

        GL11.glEnd();
    }
}
