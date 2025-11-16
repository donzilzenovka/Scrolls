package com.drzenovka.scrolls.common.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.drzenovka.scrolls.client.gui.GuiScroll;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import com.drzenovka.scrolls.common.util.ColorUtils;

public class ItemScroll extends Item {

    public static final String NBT_PAGE = "page";
    public static final String NBT_AUTHOR = "author";
    public static final String STAMP_COLOR = "stampColor";
    public static final String STAMP_COUNT = "stampCount";
    public static final String PAPER_COLOR = "paperColor";
    public static final String INK_COLOR = "inkColor";

    public ItemScroll() {
        this.setUnlocalizedName("scroll")
            .setTextureName("scrolls:scroll")
            .setMaxStackSize(1)
            .setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabMisc);
    }

    /** Initialize NBT for a new scroll */
    protected void initNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(NBT_PAGE, "");
            tag.setString(NBT_AUTHOR, "");
            tag.setInteger(STAMP_COUNT, 0);

            // Initialize all three stamp colors to -1
            for (int i = 0; i < 3; i++) {
                tag.setInteger(STAMP_COLOR + i, -1);
            }
            tag.setInteger(PAPER_COLOR, this.getDamage(stack));
            tag.setInteger(INK_COLOR, 15);

            stack.setTagCompound(tag);
        }
    }

    /** Get the text stored on the scroll */
    public String getText(ItemStack stack) {
        initNBT(stack);
        return stack.getTagCompound()
            .getString(NBT_PAGE);
    }

    /** Set the text stored on the scroll */
    public void setText(ItemStack stack, String text) {
        initNBT(stack);
        stack.getTagCompound()
            .setString(NBT_PAGE, text);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        // Open GUI only when NOT sneaking
        if (world.isRemote && !player.isSneaking()) {
            Minecraft.getMinecraft()
                .displayGuiScreen(new GuiScroll(player, player.inventory.currentItem));
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {

        // Only place scroll when sneaking
        if (!player.isSneaking()) return false;

        // Only horizontal walls
        if (side < 2) return false;

        if (!player.canPlayerEdit(x, y, z, side, stack)) return false;

        // Map side to EntityHangingScroll direction
        int direction = switch (side) {
            case 2 -> 2; // north
            case 3 -> 0; // south
            case 4 -> 1; // west
            case 5 -> 3; // east
            default -> 0;
        };

        if (!world.isRemote) {
            EntityHangingScroll scrollEntity = new EntityHangingScroll(world, x, y, z, direction);

            // Transfer NBT state
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound();

                scrollEntity.scrollText = tag.getString(NBT_PAGE);
                scrollEntity.scrollAuthor = tag.getString(NBT_AUTHOR);
                scrollEntity.stampCount = tag.getInteger(STAMP_COUNT);

                for (int i = 0; i < 3; i++) scrollEntity.stampColors[i] = tag.getInteger(STAMP_COLOR + i);

                scrollEntity.paperColor = tag.getInteger(PAPER_COLOR);
                scrollEntity.inkColor = tag.getInteger(INK_COLOR);

                scrollEntity.syncToWatcher();
            }

            if (!scrollEntity.onValidSurface()) return false;

            world.spawnEntityInWorld(scrollEntity);

            if (!player.capabilities.isCreativeMode) {
                stack.stackSize--;
            }
        }
        return true;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        initNBT(stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        initNBT(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, java.util.List list, boolean advanced) {
        initNBT(stack);

        NBTTagCompound tag = stack.getTagCompound();
        String editor = tag.getString(NBT_AUTHOR);
        int colorIndex = tag.getInteger(ItemScroll.INK_COLOR);
        EnumChatFormatting color = ColorUtils.COLOR_ENUMS[colorIndex];
        if (editor != null && !editor.isEmpty()) {
            list.add(color + "Last inscribed by " + editor);
        } else {
            list.add(color + "Blank");
        }
    }
}
