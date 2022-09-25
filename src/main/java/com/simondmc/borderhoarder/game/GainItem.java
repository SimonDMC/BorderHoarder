package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import org.bukkit.Material;

public class GainItem {
    public GainItem(Material itemType) {
        if (itemType == null) return;
        if (itemType.equals(Material.AIR)) return;
        BorderHoarder.plugin.getServer().broadcastMessage(itemType.toString());
    }
}
