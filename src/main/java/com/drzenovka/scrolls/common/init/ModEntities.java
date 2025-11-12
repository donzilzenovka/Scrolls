package com.drzenovka.scrolls.common.init;

import com.drzenovka.scrolls.common.core.Scrolls;
import com.drzenovka.scrolls.common.entity.EntityHangingScroll;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {
    private static int entityId = 0;

    public static void init() {
        EntityRegistry.registerModEntity(EntityHangingScroll.class,
            "Scroll", entityId++, Scrolls.instance, 64, 3, true);
    }
}
