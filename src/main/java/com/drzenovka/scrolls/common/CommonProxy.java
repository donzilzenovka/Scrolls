package com.drzenovka.scrolls.common;

import com.drzenovka.scrolls.common.block.ModBlocks;
import com.drzenovka.scrolls.gui.ScrollGuiHandler;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import com.drzenovka.scrolls.network.PacketSaveScroll;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraftforge.common.MinecraftForge;

import static com.drzenovka.scrolls.Scrolls.MODID;

public class CommonProxy {

    public SimpleNetworkWrapper NETWORK;

    public void preInit(FMLPreInitializationEvent event) {
        // Initialize items and blocks
        ModItems.init();
        ModBlocks.init(); // optional if you add block-based scrolls

        // Register GUI handler for scrolls
        FMLCommonHandler.instance().bus().register(new ScrollGuiHandler());
        MinecraftForge.EVENT_BUS.register(new ScrollGuiHandler());

        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        NETWORK.registerMessage(PacketSaveScroll.Handler.class, PacketSaveScroll.class, 0, Side.SERVER);
    }

    /**
     * Initialization: register entities, recipes, and event handlers
     */
    public void init(FMLInitializationEvent event) {
        registerEntities();
    }

    /**
     * Post-initialization: handle interaction with other mods
     */
    public void postInit(FMLPostInitializationEvent event) {
        // Currently nothing needed
    }

    /**
     * Server starting: register server commands
     */
    public void serverStarting(FMLServerStartingEvent event) {
        // Currently nothing needed
    }

    /**
     * Entity registration helper
     */
    protected void registerEntities() {
        EntityRegistry.registerModEntity(
            EntityHangingScroll.class,
            "HangingScroll",
            0,
            com.drzenovka.scrolls.Scrolls.instance,
            64, 1, false
        );
    }
}
