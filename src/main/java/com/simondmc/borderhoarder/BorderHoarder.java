package com.simondmc.borderhoarder;

import com.simondmc.borderhoarder.cmd.JoinGameCommand;
import com.simondmc.borderhoarder.cmd.StartGameCommand;
import com.simondmc.borderhoarder.cmd.ViewCollectedItemsCommand;
import com.simondmc.borderhoarder.cmd.ViewMissingItemsCommand;
import com.simondmc.borderhoarder.data.DataHandler;
import com.simondmc.borderhoarder.game.ItemDictionary;
import com.simondmc.borderhoarder.game.PlayerListener;
import com.simondmc.borderhoarder.world.BorderWorldCreator;
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
        plugin.getCommand("joinbordergame").setExecutor(new JoinGameCommand());
        // populate item dictionary
        ItemDictionary.populateDict();
        // register listener
        getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        // load data
        DataHandler.loadData();
        // register world if it exists
        BorderWorldCreator.registerWorld();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
