package com.drzenovka.scrolls.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.item.ItemScroll;

import java.util.List;

public class EntityHangingScroll extends EntityItemFrame {

    public static final int WIDTH_PIXELS = 2;
    public static final int HEIGHT_PIXELS = 8;
    private static final float WIDTH_BLOCKS = WIDTH_PIXELS / 16.0F;
    private static final float HEIGHT_BLOCKS = HEIGHT_PIXELS / 16.0F;

    private static final int DW_SCROLL_DATA = 10;
    private static final int IDX_NBT_TEXT = 0;
    private static final int IDX_NBT_AUTHOR = 1;
    private static final int IDX_STAMP_COUNT = 2;
    private static final int IDX_STAMP_COLOR0 = 3;
    private static final int IDX_STAMP_COLOR1 = 4;
    private static final int IDX_STAMP_COLOR2 = 5;
    private static final int IDX_PAPER_COLOR = 6;
    private static final int IDX_INK_COLOR = 7;

    public String scrollText = "Dr Zenovka was here";
    public String scrollAuthor = "";
    public int paperColor = 0;
    public int stampCount = 0;
    public int[] stampColors = new int[] { -1, -1, -1 };
    public int inkColor = 15;

    // needed for class to work
    public EntityHangingScroll(World world) {
        super(world);
        this.setSize(WIDTH_BLOCKS, HEIGHT_BLOCKS);
        this.ignoreFrustumCheck = true;
    }

    public EntityHangingScroll(World world, int x, int y, int z, int side) {
        super(world, x, y, z, side);
        this.ignoreFrustumCheck = true;
    }

    public String getScrollText() {
        return getMultiString(IDX_NBT_TEXT);
    }

    public Integer getPaperColor() {
        return getMultiInt(IDX_PAPER_COLOR);
    }

    public Integer getStampCount() {
        return getMultiInt(IDX_STAMP_COUNT);
    }

    // Getter
    public int[] getStampColors() {
        int[] stamp_colors = new int[3];
        for (int i = 0; i < 3; i++) {
            stamp_colors[i] = getMultiInt(IDX_STAMP_COLOR0 + i);
        }
        return stamp_colors;

    }

    public int getInkColor() {
        return getMultiInt(IDX_INK_COLOR);
    }

    public void setStampColors(int[] colors) {
        for (int i = 0; i < 3 && i < colors.length; i++) {
            setMultiInt(IDX_STAMP_COLOR0 + i, colors[i]);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(DW_SCROLL_DATA, ""); // empty, but not null
    }

    @Override
    public boolean onValidSurface() {
        if (!this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            return false;
        }

        // Also check for ANY hanging entities in the same spot
        List entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(
            this,
            this.boundingBox.expand(0.01D, 0.01D, 0.01D)
        );

        for (Object o : entities) {
            if (o instanceof EntityHanging) {
                return false; // another scroll, frame, painting, etc.
            }
        }

        return super.onValidSurface();
    }


    public void syncToWatcher() {
        setMultiString(IDX_NBT_TEXT, scrollText);
        setMultiString(IDX_NBT_AUTHOR, scrollAuthor);
        setMultiInt(IDX_STAMP_COUNT, stampCount);
        for (int i = 0; i < 3; i++) setMultiInt(IDX_STAMP_COLOR0 + i, stampColors[i]);
        setMultiInt(IDX_PAPER_COLOR, paperColor);
        setMultiInt(IDX_INK_COLOR, inkColor);

    }

    public void syncFromWatcher() {
        scrollText = getMultiString(IDX_NBT_TEXT);
        scrollAuthor = getMultiString(IDX_NBT_AUTHOR);
        stampCount = getMultiInt(IDX_STAMP_COUNT);
        for (int i = 0; i < 3; i++) stampColors[i] = getMultiInt(IDX_STAMP_COLOR0 + i);
        paperColor = getMultiInt(IDX_PAPER_COLOR);
        inkColor = getMultiInt(IDX_INK_COLOR);

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        // Build CSV directly from the entity fields (not from dataWatcher)
        String[] arr = new String[8];

        // strings
        arr[IDX_NBT_TEXT] = (scrollText == null ? "" : scrollText);
        arr[IDX_NBT_AUTHOR] = (scrollAuthor == null ? "" : scrollAuthor);

        // ints
        arr[IDX_STAMP_COUNT] = Integer.toString(stampCount);
        arr[IDX_STAMP_COLOR0] = Integer.toString(stampColors.length > 0 ? stampColors[0] : -1);
        arr[IDX_STAMP_COLOR1] = Integer.toString(stampColors.length > 1 ? stampColors[1] : -1);
        arr[IDX_STAMP_COLOR2] = Integer.toString(stampColors.length > 2 ? stampColors[2] : -1);
        arr[IDX_PAPER_COLOR] = Integer.toString(paperColor);
        arr[IDX_INK_COLOR] = Integer.toString(inkColor);

        nbt.setString("ScrollData", String.join(",", arr));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        String csv = nbt.hasKey("ScrollData") ? nbt.getString("ScrollData") : "";
        String[] parts = csv.split(",", -1);

        // Ensure length and defaults
        String[] arr = new String[8];
        for (int i = 0; i < 8; i++) {
            if (i < parts.length) arr[i] = parts[i];
            else {
                // sensible defaults: empty strings for the first two, numeric defaults for rest
                arr[i] = (i <= IDX_NBT_AUTHOR) ? "" : "0";
            }
        }

        // Populate entity fields directly from arr (do NOT immediately rely on dataWatcher)
        scrollText = arr[IDX_NBT_TEXT];
        scrollAuthor = arr[IDX_NBT_AUTHOR];

        try { stampCount = Integer.parseInt(arr[IDX_STAMP_COUNT]); } catch (Exception e) { stampCount = 0; }
        try { stampColors[0] = Integer.parseInt(arr[IDX_STAMP_COLOR0]); } catch (Exception e) { stampColors[0] = -1; }
        try { stampColors[1] = Integer.parseInt(arr[IDX_STAMP_COLOR1]); } catch (Exception e) { stampColors[1] = -1; }
        try { stampColors[2] = Integer.parseInt(arr[IDX_STAMP_COLOR2]); } catch (Exception e) { stampColors[2] = -1; }

        try { paperColor = Integer.parseInt(arr[IDX_PAPER_COLOR]); } catch (Exception e) { paperColor = 0; }
        try { inkColor = Integer.parseInt(arr[IDX_INK_COLOR]); } catch (Exception e) { inkColor = 15; }

        // Now push to watcher so clients receive updated data
        setMultiArray(arr);
    }

    @Override
    public int getWidthPixels() {
        return WIDTH_PIXELS;
    }

    @Override
    public int getHeightPixels() {
        return HEIGHT_PIXELS;
    }

    @Override
    public void onBroken(Entity breaker) {
        if (breaker instanceof EntityPlayer && ((EntityPlayer) breaker).capabilities.isCreativeMode) return;
        ItemStack drop = new ItemStack(ModItems.scrollColored, 1, paperColor);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString(ItemScroll.NBT_PAGE, scrollText);
        tag.setString(ItemScroll.NBT_AUTHOR, scrollAuthor);
        tag.setInteger(ItemScroll.STAMP_COUNT, stampCount);

        for (int i = 0; i < 3; i++) {
            tag.setInteger(ItemScroll.STAMP_COLOR + i, stampColors[i]);
        }

        tag.setInteger(ItemScroll.PAPER_COLOR, paperColor);
        tag.setInteger(ItemScroll.INK_COLOR, inkColor);

        drop.setTagCompound(tag);
        this.entityDropItem(drop, 0.0F);
    }

    // --- Helper methods ---

    private String[] getMultiArray() {
        String csv = dataWatcher.getWatchableObjectString(DW_SCROLL_DATA);
        String[] arr = csv.split(",", -1); // keep empty strings
        if (arr.length < 8) {
            // pad if needed
            String[] tmp = new String[8];
            System.arraycopy(arr, 0, tmp, 0, arr.length);
            for (int i = arr.length; i < 8; i++) tmp[i] = "0";
            arr = tmp;
        }
        return arr;
    }

    private void setMultiArray(String[] arr) {
        dataWatcher.updateObject(DW_SCROLL_DATA, String.join(",", arr));
    }

    // Get string at index
    public String getMultiString(int index) {
        if (index < 0 || index > 1) throw new IllegalArgumentException("Index not a string");
        return getMultiArray()[index];
    }

    // Set string at index
    public void setMultiString(int index, String value) {
        if (index < 0 || index > 1) throw new IllegalArgumentException("Index not a string");
        String[] arr = getMultiArray();
        arr[index] = value;
        setMultiArray(arr);
    }

    // Get integer at index
    public int getMultiInt(int index) {
        if (index < 2 || index > 7) throw new IllegalArgumentException("Index not an int");
        String[] arr = getMultiArray();
        try {
            return Integer.parseInt(arr[index]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Set integer at index
    public void setMultiInt(int index, int value) {
        if (index < 2 || index > 7) throw new IllegalArgumentException("Index not an int");
        String[] arr = getMultiArray();
        arr[index] = Integer.toString(value);
        setMultiArray(arr);
    }

}
