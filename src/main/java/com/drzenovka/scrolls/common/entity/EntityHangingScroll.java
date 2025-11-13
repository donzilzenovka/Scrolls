package com.drzenovka.scrolls.common.entity;

import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.item.ItemScroll;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHangingScroll extends EntityItemFrame {

    private String scrollText = "Dr Zenovka was here";
    public static final int WIDTH_PIXELS = 8;
    public static final int HEIGHT_PIXELS = 8;

    //needed for class to work
    public EntityHangingScroll(World world) {
        super(world);
    }

    public EntityHangingScroll(World world, int x, int y, int z, int side) {
        super(world, x, y, z, side); // direction will be set by setDirection
        this.setDirection(side);

    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // index 30 is usually safe for mod entities; vanilla ItemFrame stops earlier
        this.dataWatcher.addObject(30, "");
    }

    public void setScrollText(String text) {
        this.scrollText = text;
        // keep both in sync
        this.dataWatcher.updateObject(30, text == null ? "" : text);
    }

    public String getScrollText() {
        // always prefer synced watcher value
        return this.dataWatcher.getWatchableObjectString(30);
    }

    /** Save/load NBT */
    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("ScrollText", this.getScrollText());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.scrollText = nbt.getString("ScrollText");
        this.dataWatcher.updateObject(30, this.scrollText);
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

        // Always create NBT, even if scrollText is empty
        if (!drop.hasTagCompound()) drop.setTagCompound(new NBTTagCompound());
        drop.getTagCompound().setString(ItemScroll.NBT_PAGE, this.scrollText != null ? this.scrollText : "");
        this.entityDropItem(drop, 0.0F);
    }
}
