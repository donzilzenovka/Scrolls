package com.drzenovka.scrolls.common.block;

import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.init.ModOreDict;
import com.drzenovka.scrolls.common.item.ItemInkBottle;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.util.ColorUtils;
import com.drzenovka.scrolls.common.util.DyeColorMap;
import com.drzenovka.scrolls.common.util.Utils;
import net.minecraft.block.BlockCauldron;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInkCauldron extends BlockCauldron {

    public BlockInkCauldron() {
        super();
        this.setHardness(1.0f);
        this.setResistance(30.f);
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
        //System.out.println("Ink Cauldron level: " + inkCauldron.getLevel());

        ItemStack held = player.getHeldItem();

        //----------- Debug ---------------
        /*
        if (held == null) {
            if (inkCauldron.getLevel() > 0){
                inkCauldron.setLevel(inkCauldron.getLevel() - 1);
            }else{
                inkCauldron.setLevel(3);
            }
            return false;
        }

         */
        if (held == null) {
            System.out.println("current water level: " + inkCauldron.getLevel());
            return false;
        }

        // ---------- Fill bottle ----------
        if (held.getItem() == Items.glass_bottle) {
            if (inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    inkCauldron.decrementLevel(1);


                    ItemStack resultBottle;
                    if (inkCauldron.getColorMeta() == -1){
                        resultBottle = new ItemStack(Items.potionitem, 1, 0); // water
                    } else {
                        resultBottle = new ItemStack(ModItems.inkBottle);
                        ItemInkBottle.setUses(resultBottle, 0); //set 0 due to reverse inkbottle logic
                        resultBottle.setItemDamage(inkCauldron.getColorMeta());
                    }

                    held.stackSize--;
                    if (held.stackSize <= 0)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                    if (!player.inventory.addItemStackToInventory(resultBottle)) {
                        player.dropPlayerItemWithRandomChoice(resultBottle, false);
                    }
                    player.openContainer.detectAndSendChanges();

                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }

        // ---------- Water Bucket ----
        if (held.getItem() == Items.water_bucket) {
            if (inkCauldron.isWater() || inkCauldron.getLevel() == 0) {
                if (!world.isRemote) {
                    inkCauldron.setWater();
                    inkCauldron.setLevel(3);
                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                    }

                    player.openContainer.detectAndSendChanges();
                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
            return true;
        }


        // ---------- Add dye ----------
        if (Utils.isOreDictItem(held, ModOreDict.DYE)) {


            if (inkCauldron.isWater() && inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    //System.out.println(held);
                    int dyeMeta = DyeColorMap.getColorForStack(held);
                    inkCauldron.setInk(dyeMeta);  // set color
                    //inkCauldron.incrementLevel(1); // top up level

                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                    player.openContainer.detectAndSendChanges();
                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }


        // ---------- Let vanilla cauldron handle buckets ----------
        /*
        int oldMeta = world.getBlockMetadata(x, y, z);
        boolean result = super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);

        if (result && !world.isRemote) {
            int newMeta = world.getBlockMetadata(x, y, z);

            // If vanilla updated the meta (e.g., bucket filled/emptied it)
            if (newMeta != oldMeta) {
                TileEntity te2 = world.getTileEntity(x, y, z);
                if (te2 instanceof TileEntityInkCauldron) {
                    TileEntityInkCauldron ink = (TileEntityInkCauldron) te2;

                    // When vanilla fills it, color is water, level matches meta
                    if (newMeta > 0) {
                        ink.setWater();         // your method to reset ink/color
                        ink.setLevel(newMeta);  // sync metadata to TE
                    } else {
                        ink.clearInk();         // empty
                        ink.setLevel(0);
                    }

                    ink.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
        }
        return result;

         */
        return true;
    }
}
