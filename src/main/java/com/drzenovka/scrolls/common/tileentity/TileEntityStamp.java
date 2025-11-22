package com.drzenovka.scrolls.common.tileentity;

import com.drzenovka.scrolls.client.renderer.entity.RenderStamp;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityStamp extends TileEntity {

    // Optional: store ink color or stamp type if you want multiple variants
    private int color = 0;
    // static reference to the TESR
    public static RenderStamp globalTESR;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (globalTESR != null) {
            // Make it extremely large so frustum culling never happens
            return INFINITE_EXTENT_AABB;
        }
        return super.getRenderBoundingBox();
    }

    public void setColor(int color) {
        this.color = color;
        markDirty(); // mark dirty so Minecraft knows to sync
    }

    public int getColor() {
        return color;
    }


}
