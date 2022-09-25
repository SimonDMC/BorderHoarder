package com.simondmc.borderhoarder;

import com.simondmc.borderhoarder.cmd.StartGameCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BorderHoarder extends JavaPlugin {

    public static BorderHoarder plugin;

    @Override
    public void onEnable() {
        // register plugin for static access
        plugin = this;
        // enable commands
        this.getCommand("startbordergame").setExecutor(new StartGameCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
