package com.simondmc.borderhoarder.data;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.game.ItemHandler;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    // register plugin for local use
    private static final BorderHoarder plugin = BorderHoarder.plugin;

    public static void saveData() {
        // convert material list to string list
        List<String> items = new ArrayList<>();
        List<String> players = new ArrayList<>();
        for (Material item : ItemHandler.getCollectedItems().keySet()) {
            items.add(item.toString());
            if (ItemHandler.getCollectedItems().get(item) != null) {
                players.add(ItemHandler.getCollectedItems().get(item).getUniqueId().toString());
            } else {
                players.add("null");
            }
        }
        // save data
        plugin.getConfig().set("collected-items", items);
        for (int i = 0; i < items.size(); i++) {
            plugin.getConfig().set("collected-items." + items.get(i), players.get(i));
        }
        plugin.saveConfig();
    }

    public static void loadData() {
        // load data from file
        if (plugin.getConfig().contains("collected-items")) {
            if (plugin.getConfig().getConfigurationSection("collected-items") == null) {
                // pass empty list
                ItemHandler.setCollectedItems(new ArrayList<>(), new ArrayList<>());
                return;
            }
            List<String> items = plugin.getConfig().getConfigurationSection("collected-items").getValues(false).keySet().stream().toList();
            List<String> players = new ArrayList<>();
            for (String item : items) {
                players.add(plugin.getConfig().getString("collected-items." + item));
            }
            ItemHandler.setCollectedItems(items, players);
        }
    }
}
