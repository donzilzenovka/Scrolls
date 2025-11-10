package com.drzenovka.scrolls;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.drzenovka.scrolls.common.CommonProxy;

@Mod(modid = Scrolls.MODID, name = Scrolls.NAME, version = Scrolls.VERSION)
public class Scrolls {

    public static final String MODID = "scrolls";
    public static final String NAME = "Scrolls";
    public static final String VERSION = "1.0.0";

    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(
        clientSide = "com.drzenovka.scrolls.client.ClientProxy",
        serverSide = "com.drzenovka.scrolls.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance(MODID)
    public static Scrolls instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOG.info("Pre-initializing Scrolls mod...");
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOG.info("Initializing Scrolls mod...");
        proxy.init(event);
    }
}
