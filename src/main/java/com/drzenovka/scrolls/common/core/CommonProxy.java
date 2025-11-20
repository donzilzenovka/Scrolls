package com.drzenovka.scrolls.common.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.drzenovka.scrolls.common.handler.FuelHandler;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {

    public void init() {
        registerHandlers();
    }

    private void registerHandlers() {
        GameRegistry.registerFuelHandler(new FuelHandler());
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
