package com.drzenovka.scrolls.common.handler;

import com.drzenovka.scrolls.client.core.ClientProxy;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

public class ClientEventHandler {

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Pre event) {

        // Ensure you are registering only onto the BLOCK texture sheet
        if (event.map.getTextureType() == 0) { // 0 is TextureMap.locationBlocksTexture

            // Register your custom texture using the path/name defined above
            IIcon icon = event.map.registerIcon("scrolls:cauldron_water_still");

            // Store the registered IIcon in your static helper field
            ClientProxy.ClientIconHelper.customInkCauldronFluidIcon = icon;
        }
    }
}
