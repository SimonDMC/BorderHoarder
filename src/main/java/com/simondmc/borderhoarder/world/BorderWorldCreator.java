package com.simondmc.borderhoarder.world;

import com.simondmc.borderhoarder.BorderHoarder;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Level;

public class BorderWorldCreator {

    public static final String worldName = "border-game";

    // register local plugin instance
    BorderHoarder plugin = BorderHoarder.plugin;

    public BorderWorldCreator(long seed) {
        plugin.getLogger().log(Level.INFO, "Attempting world creation with seed " + seed);

        // display generating to players
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("§aGenerating world", "§7Please wait...", 10, 10000, 10);
        }

        // delete old world if it exists
        String spigotFolder = plugin.getDataFolder().getAbsoluteFile().getParentFile().getParent();
        File oldWorld = new File(spigotFolder + File.separator + worldName);
        if (oldWorld.exists()) {
            plugin.getLogger().log(Level.INFO, "Found old world, attempting to teleport everyone out");
            // tp everyone out so server can unload world
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.getWorld().getName().equals(worldName)) {
                    World w = Bukkit.getWorlds().get(0).getName().equals(worldName) ? Bukkit.getWorlds().get(1) : Bukkit.getWorlds().get(0);
                    Location l = p.getLocation();
                    l.setWorld(w);
                    p.teleport(l);
                }
            }
            plugin.getLogger().log(Level.INFO, "All players have been teleported out, unloading");
            plugin.getServer().unloadWorld(worldName, false);
            plugin.getLogger().log(Level.INFO, "World unloaded, removing folder");
            deleteDir(oldWorld);
            plugin.getLogger().log(Level.INFO, "Old world successfully removed");
        }

        // world settings
        WorldCreator wc = new WorldCreator(worldName);
        wc.seed(seed);

        plugin.getLogger().log(Level.INFO, "World creation starting");

        // create world
        World w = plugin.getServer().createWorld(wc);

        plugin.getLogger().log(Level.INFO, "World creation done, teleporting all players");

        final Location center = w.getSpawnLocation().clone().add(.5, 0, .5);

        // tp everyone in and reset them
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.teleport(center);
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.getInventory().setItemInOffHand(null);
            p.setGameMode(GameMode.SURVIVAL);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.setExp(0);
            p.setLevel(0);
            p.setFireTicks(0);
            p.setFallDistance(0);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setBedSpawnLocation(center, true);
            // clear title
            p.resetTitle();
        }

        // world settings
        w.setGameRule(GameRule.SPAWN_RADIUS, 0);

        // set border
        WorldBorder border = w.getWorldBorder();
        border.setCenter(center);
        border.setSize(1);
    }

    // https://stackoverflow.com/a/29175213
    private static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }
}
