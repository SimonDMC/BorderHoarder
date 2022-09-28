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
        for (Material item : ItemHandler.getCollectedItems()) {
            items.add(item.toString());
        }
        // save data
        plugin.getConfig().set("collected-items", items);
        plugin.saveConfig();
    }

    public static void loadData() {
        // load data from file
        if (plugin.getConfig().contains("collected-items")) {
            ItemHandler.setCollectedItems(plugin.getConfig().getStringList("collected-items"));
        }
    }
}
