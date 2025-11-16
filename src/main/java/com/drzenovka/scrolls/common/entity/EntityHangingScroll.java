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

    //private static final int DW_SCROLL_TEXT = 10;
    //private static final int DW_SCROLL_AUTHOR = 11;
    //private static final int DW_STAMP_COUNT = 12;
    //private static final int DW_STAMP_COLORS = 13;
    //private static final int DW_PAPER_COLOR = 14;

    private static final int DW_SCROLL_DATA = 10;
    private static final int IDX_NBT_TEXT = 0;
    private static final int IDX_NBT_AUTHOR = 1;
    private static final int IDX_STAMP_COUNT = 2;
    private static final int IDX_STAMP_COLOR0 = 3;
    private static final int IDX_STAMP_COLOR1 = 4;
    private static final int IDX_STAMP_COLOR2 = 5;
    private static final int IDX_PAPER_COLOR = 6;
    private static final int IDX_INK_COLOR = 7;

    String[] scrollData = new String[] {"", "", "0", "-1", "-1", "-1", "0", "15"};
    public String scrollText = "Dr Zenovka was here";
    public String scrollAuthor = "";
    public int paperColor = 0;
    public int stampCount = 0;
    public int[] stampColors = new int[] { -1, -1, -1 };
    public int inkColor = 15;


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
        //return this.dataWatcher.getWatchableObjectString(DW_SCROLL_TEXT);
        return getMultiString(IDX_NBT_TEXT);
    }

    public Integer getPaperColor() {
        // always prefer synced watcher value
        //return this.dataWatcher.getWatchableObjectInt(DW_PAPER_COLOR);
        return getMultiInt(IDX_PAPER_COLOR);
    }

    public Integer getStampCount() {
        //return this.dataWatcher.getWatchableObjectInt(DW_STAMP_COUNT);
        return getMultiInt(IDX_STAMP_COUNT);
    }

    // Getter
    public int[] getStampColors() {
        //String s = this.dataWatcher.getWatchableObjectString(DW_STAMP_COLORS);
        //String[] parts = s.split(",");
        //int[] result = new int[parts.length];
        //for (int i = 0; i < parts.length; i++) {
        //    result[i] = Integer.parseInt(parts[i]);
        //}
        //return result;
        int[] stamp_colors = new int[3];
        for (int i = 0; i < 3; i++){
            stamp_colors[i] = getMultiInt(IDX_STAMP_COLOR0 + i);
        }
        return stamp_colors;

    }

    public int getInkColor() {
        return getMultiInt(IDX_INK_COLOR);
    }

    public void setStampColors(int[] colors) {
        for (int i = 0; i < 3 && i < colors.length; i++){
            setMultiInt(IDX_STAMP_COLOR0 + i, colors[i]);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        // index 30 is usually safe for mod entities; vanilla ItemFrame stops earlier
        //this.dataWatcher.addObject(DW_SCROLL_TEXT, ""); // text
        //this.dataWatcher.addObject(DW_SCROLL_AUTHOR, ""); // author
        //this.dataWatcher.addObject(DW_STAMP_COUNT, 0);  // stampCount
        //this.dataWatcher.addObject(DW_STAMP_COLORS, "-1,-1,-1"); // stampColors CSV ("3,4,5")
        //this.dataWatcher.addObject(DW_PAPER_COLOR, 0); //Paper color
        // Initial default values:

        //scrollData = new String[] {"", "", "0", "-1", "-1", "-1", "0", "0"};
        //dataWatcher.addObject(DW_SCROLL_DATA, String.join(",", scrollData));
        this.dataWatcher.addObject(DW_SCROLL_DATA, "");  // empty, but not null
    }

    public void syncToWatcher() {
        //this.dataWatcher.updateObject(DW_SCROLL_TEXT, scrollText);
        //this.dataWatcher.updateObject(DW_SCROLL_AUTHOR, scrollAuthor);
        //this.dataWatcher.updateObject(DW_STAMP_COUNT, stampCount);

        // compact colours into a CSV string
        //this.dataWatcher.updateObject(DW_STAMP_COLORS,
        //    stampColors[0] + "," + stampColors[1] + "," + stampColors[2]);

        //this.dataWatcher.updateObject(DW_PAPER_COLOR, paperColor);
        setMultiString(IDX_NBT_TEXT, scrollText);
        setMultiString(IDX_NBT_AUTHOR, scrollAuthor);
        setMultiInt(IDX_STAMP_COUNT, stampCount);
        for (int i = 0; i < 3; i++) setMultiInt(IDX_STAMP_COLOR0 + i, stampColors[i]);
        setMultiInt(IDX_PAPER_COLOR, paperColor);
        setMultiInt(IDX_INK_COLOR, inkColor);

    }

    public void syncFromWatcher() {
        //scrollText = dataWatcher.getWatchableObjectString(DW_SCROLL_TEXT);
        //scrollAuthor = dataWatcher.getWatchableObjectString(DW_SCROLL_AUTHOR);
        //stampCount = dataWatcher.getWatchableObjectInt(DW_STAMP_COUNT);

        //String csv = dataWatcher.getWatchableObjectString(DW_STAMP_COLORS);
        //String[] split = csv.split(",");
        //for (int i = 0; i < 3 && i < split.length; i++) {
        //    try { stampColors[i] = Integer.parseInt(split[i]); }
        //    catch (Exception ignored) { stampColors[i] = -1; }
        //}

        //paperColor = dataWatcher.getWatchableObjectInt(DW_PAPER_COLOR);
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
        //nbt.setString("ScrollText", scrollText);
        //nbt.setString("ScrollAuthor", scrollAuthor);
        //nbt.setInteger("StampCount", stampCount);

        //for (int i = 0; i < 3; i++) {
        //    nbt.setInteger("StampColor" + i, stampColors[i]);
        //}

        //nbt.setInteger("PaperColor", paperColor);
        nbt.setString("ScrollData", String.join(",", getMultiArray()));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        //scrollText  = nbt.getString("ScrollText");
        //scrollAuthor = nbt.getString("ScrollAuthor");
        //stampCount  = nbt.getInteger("StampCount");

        //for (int i = 0; i < 3; i++)
        //    stampColors[i] = nbt.getInteger("StampColor" + i);

        //paperColor = nbt.getInteger("PaperColor");
        String csv = nbt.getString("ScrollData");
        scrollData = csv.split(",", -1);

        syncFromWatcher();
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
