package com.drzenovka.scrolls.common.entity;

import net.minecraft.entity.EntityHanging;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHangingScroll extends EntityHanging {

    private String scrollData = ""; // Store the scroll text

    public EntityHangingScroll(World world) {
        super(world);
    }

    public EntityHangingScroll(World world, int x, int y, int z, int facing, ItemStack stack) {
        super(world, x, y, z, facing);  // must include facing
        this.hangingDirection = facing; // optional, EntityHanging may already set it
        if (stack.hasTagCompound()) {
            this.scrollData = stack.getTagCompound().getString("page");
        }
    }


    @Override
    public void onBroken(net.minecraft.entity.Entity entity) {
        // Drop item if broken
        if (!this.worldObj.isRemote) {
            // optionally drop the scroll item
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("scrollData", scrollData);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("scrollData")) {
            scrollData = compound.getString("scrollData");
        }
    }

    @Override
    public int getWidthPixels() {
        return 16;
    }

    @Override
    public int getHeightPixels() {
        return 16;
    }
}
