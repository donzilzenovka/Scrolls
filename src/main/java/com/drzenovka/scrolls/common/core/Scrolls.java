package com.drzenovka.scrolls.common.core;

import com.drzenovka.scrolls.common.tileentity.TileEntityStamp;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.drzenovka.scrolls.common.handler.ClientEventHandler;
import com.drzenovka.scrolls.common.handler.ConfigHandler;
import com.drzenovka.scrolls.common.init.ModBlocks;
import com.drzenovka.scrolls.common.init.ModEntities;
import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.init.ModOreDict;
import com.drzenovka.scrolls.common.init.ModRecipes;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.network.PacketSaveScroll;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

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
        serverSide = "com.drzenovka.scrolls.common.core.CommonProxy")
    public static CommonProxy proxy;

    // Creative Tab
    public static final ScrollsTab scrollsTab = new ScrollsTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG.info("Pre-initializing Scrolls mod...");
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance()
            .bus()
            .register(new ConfigHandler());

        FMLCommonHandler.instance()
            .bus()
            .register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        // ModVersionChecker.registerModToUpdate(MODID, VERSION, EnumChatFormatting.DARK_PURPLE, versionUrl);

        NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        NETWORK.registerMessage(PacketSaveScroll.Handler.class, PacketSaveScroll.class, 0, Side.SERVER);

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModRecipes.init();
        ModOreDict.init();

        GameRegistry.registerTileEntity(TileEntityInkCauldron.class, "tileEntityInkCauldron");
        GameRegistry.registerTileEntity(TileEntityStamp.class, "tileEntityStamp");

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOG.info("Initializing Scrolls mod...");
        proxy.init();
    }
}
