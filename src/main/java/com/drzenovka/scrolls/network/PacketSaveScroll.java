package com.drzenovka.scrolls.network;

import com.drzenovka.scrolls.common.item.ItemScroll;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSaveScroll implements IMessage {
    private int slot;
    private String text;
    private String author;

    // Default constructor required
    public PacketSaveScroll() {}

    public PacketSaveScroll(int slot, String text, String author) {
        this.slot = slot;
        this.text = text;
        this.author = author;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
        ByteBufUtils.writeUTF8String(buf, text);
        ByteBufUtils.writeUTF8String(buf, author);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
        text = ByteBufUtils.readUTF8String(buf);
        author = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<PacketSaveScroll, IMessage> {
        @Override
        public IMessage onMessage(PacketSaveScroll message, MessageContext ctx) {
            // Directly update the server-side ItemStack
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            ItemStack stack = player.inventory.getStackInSlot(message.slot);
            if (stack != null) {
                if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
                NBTTagCompound tag = stack.getTagCompound();
                tag.setString(ItemScroll.NBT_PAGE, message.text);
                tag.setString(ItemScroll.NBT_AUTHOR, message.author);
                player.inventory.setInventorySlotContents(message.slot, stack);
            }
            return null;
        }
    }
}
