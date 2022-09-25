package com.simondmc.borderhoarder;

import com.simondmc.borderhoarder.cmd.StartGameCommand;
import com.simondmc.borderhoarder.cmd.ViewCollectedItemsCommand;
import com.simondmc.borderhoarder.cmd.ViewMissingItemsCommand;
import com.simondmc.borderhoarder.game.ItemDictionary;
import com.simondmc.borderhoarder.game.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BorderHoarder extends JavaPlugin {

    public static BorderHoarder plugin;

    @Override
    public void onEnable() {
        // register plugin for static access
        plugin = this;
        // enable commands
        plugin.getCommand("startbordergame").setExecutor(new StartGameCommand());
        plugin.getCommand("viewcollecteditems").setExecutor(new ViewCollectedItemsCommand());
        plugin.getCommand("viewmissingitems").setExecutor(new ViewMissingItemsCommand());
        // populate item dictionary
        ItemDictionary.populateDict();
        // register listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
