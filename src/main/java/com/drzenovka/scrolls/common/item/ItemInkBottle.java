package com.drzenovka.scrolls.common.item;

import com.drzenovka.scrolls.common.util.ColorUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

import static com.drzenovka.scrolls.common.util.ColorUtils.COLOR_NAMES;

public class ItemInkBottle extends Item {

    public static final int MAX_USES = 8;   // adjust as you like
    private IIcon bottleIcon;
    private IIcon overlayIcon;


    public ItemInkBottle() {
        this.setHasSubtypes(true)
            .setMaxDamage(0)
            .setMaxStackSize(1)
            .setCreativeTab(net.minecraft.creativetab.CreativeTabs.tabMisc)
            .setUnlocalizedName("ink_bottle");
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public void registerIcons(IIconRegister reg){
        this.bottleIcon = reg.registerIcon("scrolls:ink_bottle_base");
        this.overlayIcon = reg.registerIcon("Scrolls:ink_bottle_overlay");
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        return pass == 0 ? bottleIcon : overlayIcon;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {

        if (pass == 0)
            return 0xFFFFFF; // bottle not tinted

        return ColorUtils.rgbToHex(
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][0],
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][1],
            ColorUtils.GL11_COLOR_VALUES[stack.getItemDamage()][2]);
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    // Create NBT if missing
    public static NBTTagCompound getTag(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("uses", MAX_USES);
            stack.setTagCompound(tag);
        }
        return stack.getTagCompound();
    }

    // Reduce uses by 1
    public static void consumeUse(ItemStack stack, EntityPlayer player) {
        NBTTagCompound tag = getTag(stack);
        int uses = tag.getInteger("uses");

        if (uses > 1) {
            tag.setInteger("uses", uses - 1);
            return;
        }

        // No uses left – replace with empty bottle
        if (!player.worldObj.isRemote) {

            // Remove ink bottle
            stack.stackSize = 0;
            // Try add empty bottle to player inventory
            ItemStack empty = new ItemStack(Items.glass_bottle);
            if (!player.inventory.addItemStackToInventory(empty)) {
                // Inventory full → drop it
                player.dropPlayerItemWithRandomChoice(empty, false);
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            int uses = getUses(stack);
            uses--;

            if (uses <= 0) {
                // Return empty bottle
                return new ItemStack(net.minecraft.init.Items.glass_bottle);
            }

            setUses(stack, uses);
        }

        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, java.util.List list, boolean adv) {
        int uses = getUses(stack);
        list.add(I18n.format("Uses: %s / %s", uses, MAX_USES));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 1; i < COLOR_NAMES.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    // ---------- NBT Handling ----------

    public static int getUses(ItemStack stack) {
        if (!stack.hasTagCompound()) return MAX_USES;
        return stack.getTagCompound().getInteger("uses");
    }

    public static void setUses(ItemStack stack, int uses) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setInteger("uses", uses);
    }

    // Should always start full
    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        setUses(stack, MAX_USES);
    }
}
