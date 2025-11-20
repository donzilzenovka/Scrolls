package com.drzenovka.scrolls.common.block;

import static com.drzenovka.scrolls.common.core.Scrolls.scrollsTab;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.init.ModOreDict;
import com.drzenovka.scrolls.common.item.ItemInkBottle;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.util.DyeColorMap;
import com.drzenovka.scrolls.common.util.Utils;

public class BlockInkCauldron extends BlockCauldron {

    public BlockInkCauldron() {
        super();
        this.setHardness(1.0f);
        this.setResistance(30.f);
        this.setBlockName("inkCauldron");
        this.setBlockTextureName("cauldron");
        this.setCreativeTab(scrollsTab);
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityInkCauldron();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {

        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof TileEntityInkCauldron)) return false;
        TileEntityInkCauldron inkCauldron = (TileEntityInkCauldron) te;

        ItemStack held = player.getHeldItem();
        if (held == null) return true;

        // ---------- Fill bottle ----------
        if (held.getItem() == Items.glass_bottle) {
            if (inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    inkCauldron.decrementLevel(1);

                    ItemStack resultBottle;
                    if (inkCauldron.getColorMeta() == -1) {
                        resultBottle = new ItemStack(Items.potionitem, 1, 0); // water
                    } else {
                        resultBottle = new ItemStack(ModItems.inkBottle);
                        ItemInkBottle.setUses(resultBottle, 0); // set 0 due to reverse ink bottle logic
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
                    if (inkCauldron.getLevel() < 3) {
                        inkCauldron.setWater();
                        inkCauldron.setLevel(3);
                    } else {
                        return true;
                    }
                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0) player.inventory
                            .setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                    }

                    player.openContainer.detectAndSendChanges();
                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
            return true;
        }

        // ---------- Water Bottle ----
        if (held.getItem() == Items.potionitem && held.getItemDamage() == 0) {
            if (inkCauldron.isWater() || inkCauldron.getLevel() == 0) {
                if (!world.isRemote) {
                    if (inkCauldron.getLevel() < 3) {
                        inkCauldron.setWater();
                        inkCauldron.setLevel(inkCauldron.getLevel() + 1);
                    } else {
                        return true;
                    }
                    if (!player.capabilities.isCreativeMode) {
                        held.stackSize--;
                        if (held.stackSize <= 0) player.inventory
                            .setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.glass_bottle));
                    }

                    player.openContainer.detectAndSendChanges();
                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
            }
            return true;
        }
        // ---------- White Paper -----
        if (held.getItem() == Items.paper) {
            if (!inkCauldron.isWater() && inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    int dyeMeta = inkCauldron.getColorMeta();
                    inkCauldron.setLevel(inkCauldron.getLevel() - 1);
                    ItemStack result = new ItemStack(ModItems.paperColored, 1, dyeMeta);
                    held.stackSize--;
                    if (held.stackSize <= 0)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                    if (!player.inventory.addItemStackToInventory(result)) {
                        player.dropPlayerItemWithRandomChoice(result, false);
                    }
                    player.openContainer.detectAndSendChanges();

                    inkCauldron.markDirty();
                    world.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        }

        // ---------- Add dye ----------
        if (Utils.isOreDictItem(held, ModOreDict.DYE)) {

            if (inkCauldron.isWater() && inkCauldron.getLevel() > 0) {
                if (!world.isRemote) {
                    // System.out.println(held);
                    int dyeMeta = DyeColorMap.getColorForStack(held);
                    inkCauldron.setInk(dyeMeta); // set color
                    // inkCauldron.incrementLevel(1); // top up level

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
        return true;
    }

    @Override
    public Item getItemDropped(int meta, java.util.Random random, int fortune) {
        return Item.getItemFromBlock(this);
    }
}
