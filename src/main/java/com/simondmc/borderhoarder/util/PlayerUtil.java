package com.simondmc.borderhoarder.util;

import com.simondmc.borderhoarder.BorderHoarder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class PlayerUtil {

    private static final Map<UUID, String> usernameCache = new java.util.HashMap<>();

    public static void preparePlayer(Player p) {
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
        // https://www.spigotmc.org/threads/clearing-every-advancement-a-player-has-on-the-server.473875/
        Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
        while (iterator.hasNext()) {
            AdvancementProgress progress = p.getAdvancementProgress(iterator.next());
            for (String criteria : progress.getAwardedCriteria()) {
                progress.revokeCriteria(criteria);
            }
        }
    }

    public static String getNameFromUUID(UUID uuid) {
        if (usernameCache.containsKey(uuid)) {
            return usernameCache.get(uuid);
        }
        try {
            String name = new com.google.gson.JsonParser().parse(new java.io.InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")).openStream())).getAsJsonObject().get("name").getAsString();
            usernameCache.put(uuid, name);
            BorderHoarder.plugin.getLogger().info("Cached username " + name + " for UUID " + uuid);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
