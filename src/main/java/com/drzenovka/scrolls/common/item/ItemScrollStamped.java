package com.drzenovka.scrolls.common.item;

import com.drzenovka.scrolls.common.util.ColorUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

public class ItemScrollStamped extends ItemScroll {

    private IIcon[] icons;

    public ItemScrollStamped() {
        this.setUnlocalizedName("stamped_scroll")
            .setHasSubtypes(true)
            .setMaxDamage(0)
            .setMaxStackSize(1)
            .setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabMisc);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        icons = new IIcon[COLOR_NAMES.length];
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            icons[i] = reg.registerIcon("scrolls:stamped_scroll_" + COLOR_NAMES[i]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icons[Math.min(meta, icons.length - 1)];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getItemDamage();
        return "item.scroll.stamped." + COLOR_NAMES[Math.min(meta, COLOR_NAMES.length - 1)];
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);
        int meta = stack.getItemDamage();
        // Get localized stamp text
        String baseText = StatCollector.translateToLocal("tooltip.scroll.stamp." + COLOR_NAMES[meta]);


        EnumChatFormatting color = ColorUtils.COLOR_ENUMS[meta % ColorUtils.COLOR_ENUMS.length];
        list.add(color + baseText);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < ColorUtils.COLOR_NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
