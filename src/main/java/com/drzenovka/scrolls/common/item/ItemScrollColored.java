package com.drzenovka.scrolls.common.item;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.util.ColorUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScrollColored extends ItemScroll {

    private IIcon[] icons;

    public ItemScrollColored() {
        this.setUnlocalizedName("scroll_colored")
            .setHasSubtypes(true)
            .setMaxDamage(0)
            .setMaxStackSize(1)
            .setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons = new IIcon[COLOR_NAMES.length];
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            icons[i] = reg.registerIcon("scrolls:colored_scroll_" + COLOR_NAMES[i]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[Math.min(meta, icons.length - 1)];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        if (meta < 0 || meta >= ColorUtils.COLOR_NAMES.length) meta = 0;
        return "item.scroll.colored." + ColorUtils.COLOR_NAMES[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < ColorUtils.COLOR_NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        initNBT(stack);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);

        if (stack == null || !stack.hasTagCompound()) return;
        NBTTagCompound tag = stack.getTagCompound();

        int count = tag.getInteger(ItemScroll.STAMP_COUNT);
        for (int i = 0; i < count; i++) {
            int colorIndex = tag.getInteger(ItemScroll.STAMP_COLOR + i);

            String stampColor = ColorUtils.COLOR_NAMES[colorIndex];
            EnumChatFormatting color = ColorUtils.COLOR_ENUMS[colorIndex];
            String tooltipKey = "tooltip.scroll.stamp." + stampColor; // assumes keys like tooltip.scroll.stamp.0
            list.add(color + StatCollector.translateToLocal(tooltipKey));
        }
    }

}
