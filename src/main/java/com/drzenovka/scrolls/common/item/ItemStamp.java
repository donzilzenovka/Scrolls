package com.drzenovka.scrolls.common.item;

import static com.drzenovka.scrolls.common.core.Scrolls.scrollsTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemStamp extends ItemBlock {

    private IIcon icon;

    public ItemStamp(Block block) {
        super(block);
        this.setUnlocalizedName("stamp")
            .setMaxStackSize(1)
            .setContainerItem(this)
            .setFull3D()
            .setCreativeTab(scrollsTab);
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        this.icon = reg.registerIcon("scrolls:stamp_item");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icon;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.copy();
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false;
    }

}
