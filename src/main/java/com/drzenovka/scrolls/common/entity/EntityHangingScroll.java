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


    public static final int WIDTH_PIXELS = 2;
    public static final int HEIGHT_PIXELS = 8;
    private static final float WIDTH_BLOCKS = WIDTH_PIXELS / 16.0F;
    private static final float HEIGHT_BLOCKS = HEIGHT_PIXELS / 16.0F;

    private static final int DW_SCROLL_TEXT = 10;
    private static final int DW_SCROLL_AUTHOR = 11;
    private static final int DW_STAMP_COUNT = 12;
    private static final int DW_STAMP_COLORS = 13;
    private static final int DW_PAPER_COLOR = 14;

    public String scrollText = "Dr Zenovka was here";
    public String scrollAuthor = "";
    public int paperColor = 0;
    public int stampCount = 0;
    public int[] stampColors = new int[] { -1, -1, -1 };

    //needed for class to work
    public EntityHangingScroll(World world) {
        super(world);
        this.setSize(WIDTH_BLOCKS, HEIGHT_BLOCKS);
        this.ignoreFrustumCheck = true;
    }

    public EntityHangingScroll(World world, int x, int y, int z, int side) {
        super(world, x, y, z, side);
        //this.setSize(WIDTH_BLOCKS, HEIGHT_BLOCKS);
        this.ignoreFrustumCheck = true;
    }

    public String getScrollText() {
        // always prefer synced watcher value
        return this.dataWatcher.getWatchableObjectString(DW_SCROLL_TEXT);
    }

    public Integer getPaperColor() {
        // always prefer synced watcher value
        return this.dataWatcher.getWatchableObjectInt(DW_PAPER_COLOR);
    }

    public Integer getStampCount() {
        return this.dataWatcher.getWatchableObjectInt(DW_STAMP_COUNT);
    }

    // Getter
    public int[] getStampColors() {
        String s = this.dataWatcher.getWatchableObjectString(DW_STAMP_COLORS);
        String[] parts = s.split(",");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // index 30 is usually safe for mod entities; vanilla ItemFrame stops earlier
        this.dataWatcher.addObject(DW_SCROLL_TEXT, ""); // text
        this.dataWatcher.addObject(DW_SCROLL_AUTHOR, ""); // author
        this.dataWatcher.addObject(DW_STAMP_COUNT, 0);  // stampCount
        this.dataWatcher.addObject(DW_STAMP_COLORS, "-1,-1,-1"); // stampColors CSV ("3,4,5")
        this.dataWatcher.addObject(DW_PAPER_COLOR, 0); //Paper color
    }

    public void syncToWatcher() {
        this.dataWatcher.updateObject(DW_SCROLL_TEXT, scrollText);
        this.dataWatcher.updateObject(DW_SCROLL_AUTHOR, scrollAuthor);
        this.dataWatcher.updateObject(DW_STAMP_COUNT, stampCount);

        // compact colours into a CSV string
        this.dataWatcher.updateObject(DW_STAMP_COLORS,
            stampColors[0] + "," + stampColors[1] + "," + stampColors[2]);

        this.dataWatcher.updateObject(DW_PAPER_COLOR, paperColor);
    }

    public void syncFromWatcher() {
        scrollText = dataWatcher.getWatchableObjectString(DW_SCROLL_TEXT);
        scrollAuthor = dataWatcher.getWatchableObjectString(DW_SCROLL_AUTHOR);
        stampCount = dataWatcher.getWatchableObjectInt(DW_STAMP_COUNT);

        String csv = dataWatcher.getWatchableObjectString(DW_STAMP_COLORS);
        String[] split = csv.split(",");
        for (int i = 0; i < 3 && i < split.length; i++) {
            try { stampColors[i] = Integer.parseInt(split[i]); }
            catch (Exception ignored) { stampColors[i] = -1; }
        }

        paperColor = dataWatcher.getWatchableObjectInt(DW_PAPER_COLOR);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("ScrollText", scrollText);
        nbt.setString("ScrollAuthor", scrollAuthor);
        nbt.setInteger("StampCount", stampCount);

        for (int i = 0; i < 3; i++) {
            nbt.setInteger("StampColor" + i, stampColors[i]);
        }

        nbt.setInteger("PaperColor", paperColor);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        scrollText  = nbt.getString("ScrollText");
        scrollAuthor = nbt.getString("ScrollAuthor");
        stampCount  = nbt.getInteger("StampCount");

        for (int i = 0; i < 3; i++)
            stampColors[i] = nbt.getInteger("StampColor" + i);

        paperColor = nbt.getInteger("PaperColor");

        syncToWatcher();
    }


    @Override
    public int getWidthPixels() { return WIDTH_PIXELS; }

    @Override
    public int getHeightPixels() { return HEIGHT_PIXELS; }

    @Override
    public void onBroken(Entity breaker) {
        if (breaker instanceof EntityPlayer &&
            ((EntityPlayer)breaker).capabilities.isCreativeMode)
            return;
        ItemStack drop = new ItemStack(ModItems.scrollColored, 1, paperColor);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(ItemScroll.NBT_PAGE, scrollText);
        tag.setString(ItemScroll.NBT_AUTHOR, scrollAuthor);
        tag.setInteger(ItemScroll.STAMP_COUNT, stampCount);

        for (int i = 0; i < 3; i++) {
            tag.setInteger(ItemScroll.STAMP_COLOR + i, stampColors[i]);
        }

        tag.setInteger(ItemScroll.PAPER_COLOR, paperColor);

        drop.setTagCompound(tag);
        this.entityDropItem(drop, 0.0F);
    }

}
