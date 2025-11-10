package com.drzenovka.scrolls.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSaveScroll implements IMessage {
    private int slot;
    private String text;

    public PacketSaveScroll() {}
    public PacketSaveScroll(int slot, String text) {
        this.slot = slot;
        this.text = text;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
        ByteBufUtils.writeUTF8String(buf, text);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
        text = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<PacketSaveScroll, IMessage> {
        @Override
        public IMessage onMessage(PacketSaveScroll message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = player.inventory.getStackInSlot(message.slot);
            if (stack != null) {
                if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setString("page", message.text);
                player.inventory.setInventorySlotContents(message.slot, stack);
            }
            return null;
        }
    }
}
