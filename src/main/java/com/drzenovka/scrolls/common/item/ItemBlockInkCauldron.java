package com.drzenovka.scrolls.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockInkCauldron extends ItemBlock {

    @SideOnly(Side.CLIENT)
    public static IIcon flatIcon;

    public ItemBlockInkCauldron(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        flatIcon = reg.registerIcon("scrolls:ink_cauldron_flat");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return flatIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass) {
        return flatIcon;
    }
}
