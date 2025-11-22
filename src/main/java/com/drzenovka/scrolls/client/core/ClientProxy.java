package com.drzenovka.scrolls.client.core;



import com.drzenovka.scrolls.client.renderer.entity.RenderStamp;
import com.drzenovka.scrolls.common.tileentity.TileEntityStamp;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.MinecraftForgeClient;

import com.drzenovka.scrolls.client.renderer.block.RenderInkCauldron;
import com.drzenovka.scrolls.client.renderer.entity.RenderHangingScroll;
import com.drzenovka.scrolls.client.renderer.item.RenderInkBottle;
import com.drzenovka.scrolls.client.renderer.item.RenderScroll;
import com.drzenovka.scrolls.common.core.CommonProxy;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import com.drzenovka.scrolls.common.init.ModItems;
import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        super.init();
        registerRenderers();
    }

    public void registerRenderers() {
        RenderStamp stampRenderer = new RenderStamp();
        RenderingRegistry.registerEntityRenderingHandler(EntityHangingScroll.class, new RenderHangingScroll());
        MinecraftForgeClient.registerItemRenderer(ModItems.inkBottle, new RenderInkBottle());
        MinecraftForgeClient.registerItemRenderer(ModItems.scroll, new RenderScroll());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInkCauldron.class, new RenderInkCauldron());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStamp.class, stampRenderer);
        TileEntityStamp.globalTESR = stampRenderer;

    }

    public class ClientIconHelper {

        public static IIcon customInkCauldronFluidIcon;
    }

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("scrolls");
}
