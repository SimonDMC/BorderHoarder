package com.simondmc.borderhoarder.world;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.game.GameData;
import com.simondmc.borderhoarder.game.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class BorderExpander {

    public static void expandBorder() {
        World w = Bukkit.getWorld(BorderWorldCreator.worldName);
        w.getWorldBorder().setSize(ItemHandler.getCollectedItems().size() * 2 + 1, 1L);
        w = Bukkit.getWorld(BorderWorldCreator.netherWorldName);
        if (GameData.getBoolean("nether-initialized")) w.getWorldBorder().setSize(ItemHandler.getCollectedItems().size() * 2 + 1, 1L);
    }
}
