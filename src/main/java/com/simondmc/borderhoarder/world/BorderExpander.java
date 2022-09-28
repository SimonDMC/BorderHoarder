package com.simondmc.borderhoarder.world;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.game.ItemHandler;
import org.bukkit.World;

public class BorderExpander {

    public static void expandBorder() {
        World w = BorderHoarder.plugin.getServer().getWorld(BorderWorldCreator.worldName);
        w.getWorldBorder().setSize(ItemHandler.getCollectedItems().size() * 2 + 1, 1L);
    }
}
