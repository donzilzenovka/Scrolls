package com.drzenovka.scrolls.client;

import com.drzenovka.scrolls.common.CommonProxy;
import com.drzenovka.scrolls.common.ModItems;
import com.drzenovka.scrolls.common.block.ModBlocks;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import com.drzenovka.scrolls.gui.ScrollGuiHandler;
import com.drzenovka.scrolls.network.PacketSaveScroll;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ClientProxy extends CommonProxy {

    public static final SimpleNetworkWrapper NETWORK =
        NetworkRegistry.INSTANCE.newSimpleChannel("scrolls");

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // Initialize items and blocks
        ModItems.init();
        ModBlocks.init();  // optional

        // Register network message for saving scroll text
        NETWORK.registerMessage(PacketSaveScroll.Handler.class, PacketSaveScroll.class, 0, Side.SERVER);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        // Register GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(com.drzenovka.scrolls.Scrolls.instance, new ScrollGuiHandler());

        // Register entity renderer
        RenderingRegistry.registerEntityRenderingHandler(
            EntityHangingScroll.class,
            new com.drzenovka.scrolls.client.render.RenderHangingScroll()
        );


        // Crafting recipe: Paper + Feather + Ink Sac
        GameRegistry.addRecipe(
            new ItemStack(ModItems.scroll),
            " P ",
            " F ",
            " I ",
            'P', Items.paper,
            'F', Items.feather,
            'I', Items.dye // dye 0 is ink sac
        );

        // Register entity server-side (optional here, already in CommonProxy)
        // EntityRegistry.registerModEntity(EntityHangingScroll.class, "HangingScroll", 0, com.drzenovka.scrolls.Scrolls.instance, 64, 1, false);
    }
}
