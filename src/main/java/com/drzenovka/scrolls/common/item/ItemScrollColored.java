package com.drzenovka.scrolls.common.item;

import com.drzenovka.scrolls.common.util.ColorUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.List;

public class ItemScrollColored extends ItemScroll {

    public ItemScrollColored() {
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("scroll_colored");
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
        for (int i = 1; i < ColorUtils.COLOR_NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    /*
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);
        int meta = stack.getItemDamage();
        if (meta < 0 || meta >= ColorUtils.COLOR_NAMES.length) meta = 0;
        String colorName = StatCollector.translateToLocal("tooltip.scroll.colored." + ColorUtils.COLOR_NAMES[meta]);
        list.add(ColorUtils.COLOR_NAMES[meta] + colorName);
    }
    */

}
