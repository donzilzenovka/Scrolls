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
    public static final int WIDTH_PIXELS = 8;
    public static final int HEIGHT_PIXELS = 8;

    public EntityHangingScroll(World world) {
        super(world);
    }

    public EntityHangingScroll(World world, int x, int y, int z, int side) {
        super(world, x, y, z, side); // direction will be set by setDirection
        this.field_146063_b = x; // Wall X
        this.field_146064_c = y; // Wall Y
        this.field_146062_d = z; // Wall Z

        // You MUST call setPosition again after manually setting posX/Y/Z
        // to update the internal entity bounding box list reference.
        this.setDirection(side);

        // 4. (Optional) Rotation yaw for rendering
        switch (side) {
            case 2 -> this.rotationYaw = 180.0F; // north (Z-)
            case 0 -> this.rotationYaw = 0.0F;   // south (Z+)
            case 1 -> this.rotationYaw = 90.0F;  // west (X-)
            case 3 -> this.rotationYaw = -90.0F; // east (X+)
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
