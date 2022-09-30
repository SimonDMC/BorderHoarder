package com.simondmc.borderhoarder.util;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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
    }
}
