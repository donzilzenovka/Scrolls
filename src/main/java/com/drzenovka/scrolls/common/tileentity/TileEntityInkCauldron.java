package com.drzenovka.scrolls.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityInkCauldron extends TileEntity {

    private int level = 3; // 1-3 like vanilla
    private int colorMeta = -1; // -1 = water, 0-15 = dye color

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(3, level));
        markDirty();
    }

    public void decrementLevel(int amount) {
        setLevel(level - amount);
    }

    public boolean isWater() {
        return colorMeta == -1;
    }

    public void setInk(int meta) {
        this.colorMeta = meta;
    }

    public int getColorMeta() {
        return colorMeta;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.level = nbt.getInteger("level");
        this.colorMeta = nbt.getInteger("colorMeta");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("level", level);
        nbt.setInteger("colorMeta", colorMeta);
    }

    public void incrementLevel(int i) {
        this.level += i;
        if (this.level > 3) this.level = 3;  // clamp to max cauldron level
        this.markDirty();
    }

}
