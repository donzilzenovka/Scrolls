package com.drzenovka.scrolls.common.block;

import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.item.ItemInkBottle;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.util.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

import static com.drzenovka.scrolls.common.init.ModOreDict.INK;

public class BlockInkCauldron extends BlockCauldron {

    public BlockInkCauldron() {
        super();
        this.setBlockName("inkCauldron");
        this.setBlockTextureName("cauldron"); // base texture, overridden in renderer
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityInkCauldron();
    }

    /**
     * Handle right-click interactions.
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
                                    int side, float hitX, float hitY, float hitZ) {

        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof TileEntityInkCauldron)) return false;

        TileEntityInkCauldron inkCauldron = (TileEntityInkCauldron) te;
        ItemStack held = player.getHeldItem();
        if (held == null) return false;

        // ---------- Fill bottle ----------
        if (held.getItem() == Items.glass_bottle) {
            if (inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    inkCauldron.decrementLevel(1);

                    ItemStack inkBottle = new ItemStack(ModItems.inkBottle);
                    ItemInkBottle.setUses(inkBottle, ItemInkBottle.MAX_USES);
                    inkBottle.setItemDamage(inkCauldron.getColorMeta());

                    if (!player.inventory.addItemStackToInventory(inkBottle)) {
                        player.dropPlayerItemWithRandomChoice(inkBottle, false);
                    }

                    held.stackSize--;
                    if (held.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }

        // ---------- Add dye ----------
        else if (Utils.isOreDictItem(held, "dye")) {
            int dyeMeta = held.getItemDamage();

            if (inkCauldron.isWater() && inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    inkCauldron.setInk(dyeMeta);  // set color
                    inkCauldron.incrementLevel(1); // top up level

                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0) player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }

                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }

        // ---------- Let vanilla cauldron handle buckets ----------
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }



    // Optional: override rendering to handle fluid color/level
}
