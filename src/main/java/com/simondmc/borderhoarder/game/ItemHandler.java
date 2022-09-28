package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.data.DataHandler;
import com.simondmc.borderhoarder.world.BorderExpander;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {

    private static final List<Material> collectedItems = new ArrayList<>();

    public static void gainItem(Material itemType) {
        if (itemType == null) return;
        if (!ItemDictionary.getDict().containsKey(itemType)) return;
        if (!collectedItems.contains(itemType)) {
            // add to list of collected items
            collectedItems.add(itemType);
            // announce to players
            for (Player p : BorderHoarder.plugin.getServer().getOnlinePlayers()) {
                p.sendMessage("§aYou gained a " + ItemDictionary.getDict().get(itemType) + "!");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            // expand border
            BorderExpander.expandBorder();
            // save data
            DataHandler.saveData();
        }
    }

    public static List<Material> getCollectedItems() {
        return collectedItems;
    }

    public static void setCollectedItems(List<String> items) {
        collectedItems.clear();
        for (String item : items) {
            collectedItems.add(Material.valueOf(item));
        }
    }
}
