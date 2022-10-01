package com.simondmc.borderhoarder.util;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class PlayerUtil {
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
}
