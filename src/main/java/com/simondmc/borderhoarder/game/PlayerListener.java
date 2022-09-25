package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

    @EventHandler
    public void pickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        new GainItem(e.getItem().getItemStack().getType());
    }

    @EventHandler
    public void craft(CraftItemEvent e) {
        new GainItem(e.getCurrentItem().getType());
    }

    @EventHandler
    public void inventoryGet(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;

        // shift click item from somewhere else
        if (!e.isCancelled() &&
                (e.getClick().equals(ClickType.SHIFT_LEFT) ||
                        e.getClick().equals(ClickType.SHIFT_RIGHT) ||
                        e.getClick().equals(ClickType.SWAP_OFFHAND))) {
            new GainItem(e.getCurrentItem().getType());
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                // drag over to inventory
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getCurrentItem() != null) {
                    new GainItem(e.getCurrentItem().getType());
                }
            }
        }.runTaskLater(BorderHoarder.plugin, 1);
    }
}
