package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.data.DataHandler;
import com.simondmc.borderhoarder.world.BorderExpander;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class ItemHandler {

    private static final Map<Material, UUID> collectedItems = new LinkedHashMap<>();

    public static void gainItem(Material itemType, Player p) {
        if (itemType == null) return;
        if (!ItemDictionary.getDict().containsKey(itemType)) return;
        if (!collectedItems.containsKey(itemType)) {
            // add to list of collected items
            collectedItems.put(itemType, p.getUniqueId());
            // get a/an/blank
            String itemName = ItemDictionary.getDict().get(itemType);
            List<Character> vowels = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
            String conjuction;
            if (itemName.charAt(itemName.length() - 1) == 's') {
                conjuction = "";
            } else if (vowels.contains(itemName.toLowerCase().charAt(0))) {
                conjuction = "an ";
            } else {
                conjuction = "a ";
            }
            // announce to players
            for (Player recipient : BorderHoarder.plugin.getServer().getOnlinePlayers()) {
                recipient.sendMessage("§a" + (recipient == p ? "You" : p.getName()) + " collected " + conjuction + itemName + "!");
                recipient.playSound(recipient.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            // expand border
            BorderExpander.expandBorder();
            // save data
            DataHandler.saveData();
            // update tab
            TabHandler.updateTab();
        }
    }

    public static Map<Material, UUID> getCollectedItems() {
        return collectedItems;
    }

    public static void setCollectedItems(List<String> items, List<String> players) {
        collectedItems.clear();
        for (int i = 0; i < items.size(); i++) {
            collectedItems.put(Material.valueOf(items.get(i)), UUID.fromString(players.get(i)));
        }
    }

    public static void resetCollectedItems() {
        collectedItems.clear();
        DataHandler.saveData();
    }
}
