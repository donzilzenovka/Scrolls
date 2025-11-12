package com.drzenovka.scrolls.common.core;

import com.drzenovka.scrolls.common.handler.ConfigHandler;
import com.drzenovka.scrolls.common.init.ModBlocks;
import com.drzenovka.scrolls.common.init.ModEntities;
import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.network.PacketSaveScroll;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Scrolls.MODID, name = Scrolls.NAME, version = Scrolls.VERSION)
public class Scrolls {

    public static final String MODID = "scrolls";
    public static final String NAME = "Scrolls";
    public static final String VERSION = "1.0.0";
    public static final String versionUrl = "";

    public static final Logger LOG = LogManager.getLogger(MODID.toUpperCase());
    public static SimpleNetworkWrapper NETWORK;

        @Mod.Instance(MODID)
    public static Scrolls instance;


    @SidedProxy(
        clientSide = "com.drzenovka.scrolls.client.core.ClientProxy",
        serverSide = "com.drzenovka.scrolls.common.core.CommonProxy"
    )
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG.info("Pre-initializing Scrolls mod...");
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        //ModVersionChecker.registerModToUpdate(MODID, VERSION, EnumChatFormatting.DARK_PURPLE, versionUrl);

        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        NETWORK.registerMessage(PacketSaveScroll.Handler.class, PacketSaveScroll.class, 0, Side.SERVER);

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOG.info("Initializing Scrolls mod...");
        proxy.init();
    }
}
