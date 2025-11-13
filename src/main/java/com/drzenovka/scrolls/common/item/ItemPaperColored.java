package com.drzenovka.scrolls.common.item;

import com.drzenovka.scrolls.common.util.ColorUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

import java.util.List;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

public class ItemPaperColored extends Item {

    private IIcon[] icons;

    public ItemPaperColored() {
        this.setUnlocalizedName("colored_paper")
            .setHasSubtypes(true)
            .setMaxDamage(0)
            .setMaxStackSize(64)
            .setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons = new IIcon[COLOR_NAMES.length];
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            icons[i] = reg.registerIcon("scrolls:colored_paper_" + COLOR_NAMES[i]);
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
        return "item.paper." + ColorUtils.COLOR_NAMES[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 1; i < ColorUtils.COLOR_NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
