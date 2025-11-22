package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.tileentity.TileEntityInkCauldron;
import com.drzenovka.scrolls.common.tileentity.TileEntityStamp;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntity {

    public static void init(){
        GameRegistry.registerTileEntity(TileEntityInkCauldron.class, "tileEntityInkCauldron");
        GameRegistry.registerTileEntity(TileEntityStamp.class, "tileEntityStamp");
    }
}
