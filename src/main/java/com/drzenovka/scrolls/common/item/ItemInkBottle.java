package com.drzenovka.scrolls.common.item;

import static com.drzenovka.scrolls.common.core.Scrolls.scrollsTab;
import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.util.ColorUtils;
import com.drzenovka.scrolls.common.util.DyeColorMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInkBottle extends Item {

    public static final int MAX_USES = 8; // number of uses before empty
    private IIcon bottleIcon;
    private IIcon overlayIcon;

    public ItemInkBottle() {
        this.setHasSubtypes(true)
            .setMaxDamage(0)
            .setMaxStackSize(1)
            .setCreativeTab(scrollsTab)
            .setUnlocalizedName("ink_bottle");
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public void registerIcons(IIconRegister reg) {
        this.bottleIcon = reg.registerIcon("scrolls:ink_bottle_base");
        this.overlayIcon = reg.registerIcon("scrolls:ink_bottle_overlay");
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        return pass == 0 ? bottleIcon : overlayIcon;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if (pass == 0) return 0xFFFFFF; // base bottle not tinted

        return ColorUtils.rgbToHex(
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][0],
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][1],
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][2]);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    // ---------------- Container Logic ----------------

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        NBTTagCompound tag = copy.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            copy.setTagCompound(tag);
        }

        int uses = tag.getInteger("uses");
        uses++; // increment on each use

        if (uses >= MAX_USES) {
            // fully used → return empty glass bottle
            return new ItemStack(Items.glass_bottle);
        }

        tag.setInteger("uses", uses);
        return copy;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false; // keep partially used bottle in grid
    }

    // ---------------- NBT Utilities ----------------

    public static NBTTagCompound getTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("uses", 0); // start at 0, counts up
            stack.setTagCompound(tag);
        }
        return stack.getTagCompound();
    }

    public static int getUses(ItemStack stack) {
        if (!stack.hasTagCompound()) return 0;
        return stack.getTagCompound()
            .getInteger("uses");
    }

    public static void setUses(ItemStack stack, int uses) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound()
            .setInteger("uses", uses);
    }

    // ---------------- Right-click use ----------------
    /*
     * @Override
     * public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
     * if (!world.isRemote) {
     * int uses = getUses(stack);
     * uses++;
     * if (uses >= MAX_USES) {
     * return new ItemStack(Items.glass_bottle);
     * }
     * setUses(stack, uses);
     * }
     * return stack;
     * }
     */

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) {
        if (target instanceof EntitySheep) {
            EntitySheep sheep = (EntitySheep) target;
            int dyeColorID = ColorUtils.getColorForStack(stack);

            // Check on the SERVER only
            if (!sheep.worldObj.isRemote && !sheep.getSheared() && sheep.getFleeceColor() != dyeColorID) {
                if (player.worldObj.isRemote) {
                    player.swingItem();
                }

                // Dye it
                sheep.setFleeceColor(dyeColorID);

                // Consume or update the bottle
                int uses = getUses(stack);
                uses++;
                if (uses >= MAX_USES) {
                    // Replace with glass bottle
                    player.inventory
                        .setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.glass_bottle));
                    return true;
                } else {
                    setUses(stack, uses);
                }

                player.inventory.setInventorySlotContents(player.inventory.currentItem, stack.copy());
                return true;
            }
        }

        return false;
    }

    // ---------------- Item Info / Creative ----------------

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
        int uses = MAX_USES - getUses(stack);
        list.add(I18n.format("Uses: %s / %s", uses, MAX_USES));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            ItemStack stack = new ItemStack(item, 1, i);
            setUses(stack, 0); // ensure starts full
            list.add(stack);
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        setUses(stack, 0); // starts empty counter
    }

    public static void consumeEntireBottle(ItemStack stack, InventoryCrafting inv) {
        // Replace this bottle instance directly in the crafting grid
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack slot = inv.getStackInSlot(i);
            if (slot == stack) {
                inv.setInventorySlotContents(i, null); // bottle is gone
                return;
            }
        }
    }

}
