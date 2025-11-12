package com.drzenovka.scrolls.common.entity;

import com.drzenovka.scrolls.common.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHangingScroll extends EntityHanging {

    private String scrollText = "";
    public static final int WIDTH_PIXELS = 16;
    public static final int HEIGHT_PIXELS = 16;

    public EntityHangingScroll(World world, int x, int y, int z, int side) {
        super(world, x, y, z, 0); // direction will be set by setDirection
        this.setDirectionFromSide(side);
    }

    /** Sets the scroll's facing and bounding box automatically based on wall side */
    public void setDirectionFromSide(int side) {
        this.hangingDirection = side;

        switch (side) {
            case 2 -> this.rotationYaw = 180.0F; // north
            case 3 -> this.rotationYaw = 0.0F;   // south
            case 4 -> this.rotationYaw = 90.0F;  // west
            case 5 -> this.rotationYaw = -90.0F; // east
            default -> this.rotationYaw = 0.0F;
        }

        double xOffset = 0.5D;
        double yOffset = 0.5D;
        double zOffset = 0.5D;

        switch (side) {
            case 2 -> zOffset -= 0.5D;
            case 3 -> zOffset += 0.5D;
            case 4 -> xOffset -= 0.5D;
            case 5 -> xOffset += 0.5D;
        }

        this.setPosition(this.posX + xOffset, this.posY + yOffset, this.posZ + zOffset);

        float width = WIDTH_PIXELS / 16.0F;
        float height = HEIGHT_PIXELS / 16.0F;
        this.setSize(width, height);

        // Depth adjustment per facing
        float depth = 0.0625F;
        if (side == 2 || side == 3) { // north/south
            this.boundingBox.minZ = this.posZ - depth;
            this.boundingBox.maxZ = this.posZ + depth;
        } else { // west/east
            this.boundingBox.minX = this.posX - depth;
            this.boundingBox.maxX = this.posX + depth;
        }
    }


    /** Scroll text */
    public void setScrollText(String text) {
        this.scrollText = text;
    }

    public String getScrollText() {
        return this.scrollText;
    }

    /** Save/load NBT */
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("ScrollText", this.scrollText == null ? "" : this.scrollText);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.scrollText = nbt.getString("ScrollText");
    }

    @Override
    public int getWidthPixels() { return WIDTH_PIXELS; }

    @Override
    public int getHeightPixels() { return HEIGHT_PIXELS; }

    /** Drop the scroll item with text when broken */
    @Override
    public void onBroken(Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)
            return;

        ItemStack drop = new ItemStack(ModItems.scroll);
        if (!this.scrollText.isEmpty()) {
            drop.setTagCompound(new NBTTagCompound());
            drop.getTagCompound().setString("page", this.scrollText);
        }
        this.entityDropItem(drop, 0.0F);
    }
}
