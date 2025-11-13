package com.drzenovka.scrolls.common.core;

import com.drzenovka.scrolls.common.core.Scrolls;
import com.drzenovka.scrolls.common.handler.EntityHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

    public void init() {
        registerHandlers();
    }

    private void registerHandlers(){
        MinecraftForge.EVENT_BUS.register((new EntityHandler()));
        NetworkRegistry.INSTANCE.registerGuiHandler(Scrolls.instance, Scrolls.proxy);
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
