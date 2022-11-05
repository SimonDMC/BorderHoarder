package com.simondmc.borderhoarder.world;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.util.PlayerUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Level;

public class BorderWorldCreator {

    public static final String worldName = "border-game";
    public static final String netherWorldName = "border-game-nether";
    public static final String endWorldName = "border-game-end";

    // register local plugin instance
    private static final BorderHoarder plugin = BorderHoarder.plugin;

    private static final String spigotFolder = plugin.getDataFolder().getAbsoluteFile().getParentFile().getParent();
    private static final File worldFile = new File(spigotFolder + File.separator + worldName);
    private static final File netherWorldFile = new File(spigotFolder + File.separator + netherWorldName);
    private static final File endWorldFile = new File(spigotFolder + File.separator + endWorldName);

    public BorderWorldCreator(long seed) {
        plugin.getLogger().log(Level.INFO, "Attempting world creation with seed " + seed);

        // display generating to players
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("§aGenerating world (1/3)", "§7Please wait...", 10, 10000, 10);
        }

        // delete old world if it exists
        if (worldFile.exists()) {
            plugin.getLogger().log(Level.INFO, "Found old world, attempting to teleport everyone out");
            // tp everyone out so server can unload world
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.getWorld().getName().equals(worldName) || p.getWorld().getName().equals(netherWorldName) || p.getWorld().getName().equals(endWorldName)) {
                    World w = Bukkit.getWorlds().get(0).getName().equals(worldName) ? Bukkit.getWorlds().get(1) : Bukkit.getWorlds().get(0);
                    Location l = p.getLocation();
                    l.setWorld(w);
                    p.teleport(l);
                }
            }
            plugin.getLogger().log(Level.INFO, "All players have been teleported out, unloading worlds");
            plugin.getServer().unloadWorld(worldName, false);
            plugin.getServer().unloadWorld(netherWorldName, false);
            plugin.getServer().unloadWorld(endWorldName, false);
            plugin.getLogger().log(Level.INFO, "Worlds unloaded, removing folder");
            deleteDir(worldFile);
            deleteDir(netherWorldFile);
            deleteDir(endWorldFile);
            plugin.getLogger().log(Level.INFO, "Old worlds successfully removed");
        }

        // overworld settings
        WorldCreator wc = new WorldCreator(worldName);
        wc.seed(seed);

        plugin.getLogger().log(Level.INFO, "Overworld creation starting");

        // create overworld
        World w = plugin.getServer().createWorld(wc);

        plugin.getLogger().log(Level.INFO, "Overworld creation done, nether creation starting");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("§aGenerating world (2/3)", "§7Please wait...", 0, 10000, 10);
        }

        // nether settings
        WorldCreator netherWc = new WorldCreator(netherWorldName);
        netherWc.seed(seed);
        netherWc.environment(World.Environment.NETHER);

        // create nether
        World netherW = plugin.getServer().createWorld(netherWc);

        plugin.getLogger().log(Level.INFO, "Nether creation done, end creation starting");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("§aGenerating world (3/3)", "§7Please wait...", 0, 10000, 10);
        }

        // end settings
        WorldCreator endWc = new WorldCreator(endWorldName);
        endWc.seed(seed);
        endWc.environment(World.Environment.THE_END);

        // create end
        World endW = plugin.getServer().createWorld(endWc);

        plugin.getLogger().log(Level.INFO, "End creation done, teleporting players in");

        final Location center = w.getSpawnLocation().clone().add(.5, 0, .5);

        // tp everyone in and reset them
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.teleport(center);
            p.setBedSpawnLocation(center, true);
            PlayerUtil.preparePlayer(p);
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

    public static void registerWorld() {
        if (worldFile.exists()) {
            plugin.getServer().createWorld(new WorldCreator(worldName));
            plugin.getLogger().log(Level.INFO, "World " + worldName + " registered");
        } else {
            plugin.getLogger().log(Level.WARNING, "World " + worldName + " does not exist");
        }

        if (netherWorldFile.exists()) {
            WorldCreator wc = new WorldCreator(netherWorldName);
            wc.environment(World.Environment.NETHER);
            plugin.getServer().createWorld(wc);
            plugin.getLogger().log(Level.INFO, "World " + netherWorldName + " registered");
        } else {
            plugin.getLogger().log(Level.WARNING, "World " + netherWorldName + " does not exist");
        }

        if (endWorldFile.exists()) {
            WorldCreator wc = new WorldCreator(endWorldName);
            wc.environment(World.Environment.THE_END);
            plugin.getServer().createWorld(wc);
            plugin.getLogger().log(Level.INFO, "World " + endWorldName + " registered");
        } else {
            plugin.getLogger().log(Level.WARNING, "World " + endWorldName + " does not exist");
        }
    }
}
