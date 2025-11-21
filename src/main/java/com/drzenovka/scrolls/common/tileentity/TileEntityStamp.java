package com.drzenovka.scrolls.common.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityStamp extends TileEntity {

    // Optional: store ink color or stamp type if you want multiple variants
    private int color = 0;

    public void setColor(int color) {
        this.color = color;
        markDirty(); // mark dirty so Minecraft knows to sync
    }

    public int getColor() {
        return color;
    }
}
