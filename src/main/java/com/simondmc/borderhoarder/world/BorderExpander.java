package com.simondmc.borderhoarder.world;

import com.simondmc.borderhoarder.BorderHoarder;
import org.bukkit.World;

public class BorderExpander {

    public static void expandBorder() {
        World w = BorderHoarder.plugin.getServer().getWorld(BorderWorldCreator.worldName);
        w.getWorldBorder().setSize(w.getWorldBorder().getSize() + 2, 1L);
    }
}
