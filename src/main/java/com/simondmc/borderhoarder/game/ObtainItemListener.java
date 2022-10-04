package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.world.BorderWorldCreator;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

public class ObtainItemListener implements Listener {

    // picking up any dropped item from ground
    @EventHandler
    public void pickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getEntity().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getEntity().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getEntity().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        ItemHandler.gainItem(e.getItem().getItemStack().getType(), (Player) e.getEntity());
    }

    // filling a water/lava/milk bucket
    @EventHandler
    public void fillBucket(PlayerBucketFillEvent e) {
        if (!(e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        ItemHandler.gainItem(e.getItemStack().getType(), e.getPlayer());
    }

    // placing an obtained filled bucket (village loot chest?)
    @EventHandler
    public void emptyBucket(PlayerBucketEmptyEvent e) {
        if (!(e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        ItemHandler.gainItem(e.getItemStack().getType(), e.getPlayer());
    }

    // catching a fish into a bucket
    @EventHandler
    public void catchFishIntoBucket(PlayerInteractEntityEvent e) {
        if (!(e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        if (!(e.getRightClicked() instanceof Fish)) return;
        if (e.getPlayer().getInventory().getItem(e.getHand()) == null) return;
        if (e.getPlayer().getInventory().getItem(e.getHand()).getType() == Material.WATER_BUCKET) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    // wait a tick to see what the player got
                    ItemHandler.gainItem(e.getPlayer().getInventory().getItem(e.getHand()).getType(), e.getPlayer());
                }
            }.runTaskLater(BorderHoarder.plugin, 1);
        }
    }

    // filling a bottle or map
    @EventHandler
    public void fillBottleOrMap(PlayerInteractEvent e) {
        if (!(e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.GLASS_BOTTLE || e.getItem().getType() == Material.MAP) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    // wait a tick to see what the player got
                    ItemHandler.gainItem(e.getPlayer().getInventory().getItem(e.getHand()).getType(), e.getPlayer());
                }
            }.runTaskLater(BorderHoarder.plugin, 1);
        }
    }

    // trading with a villager
    @EventHandler
    public void trade(PlayerStatisticIncrementEvent e) {
        if (!(e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getPlayer().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName))) return;
        if (e.getStatistic() != Statistic.TRADED_WITH_VILLAGER) return;
        new BukkitRunnable() {

            @Override
            public void run() {
                // run through entire player inventory
                for (int i = 0; i < e.getPlayer().getInventory().getSize(); i++) {
                    if (e.getPlayer().getInventory().getItem(i) == null) continue;
                    ItemHandler.gainItem(e.getPlayer().getInventory().getItem(i).getType(), e.getPlayer());
                }
                // get cursor item
                ItemHandler.gainItem(e.getPlayer().getItemOnCursor().getType(), e.getPlayer());
            }
        }.runTaskLater(BorderHoarder.plugin, 1);
    }

    // crafting any item
    @EventHandler
    public void craft(CraftItemEvent e) {
        if (!(e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName)))
            return;
        Material craftedItem = e.getCurrentItem().getType();
        new BukkitRunnable() {

            @Override
            public void run() {
                // wait a tick to see if the player actually has the item
                if (e.getWhoClicked().getInventory().contains(craftedItem) || e.getWhoClicked().getItemOnCursor().getType() == craftedItem) {
                    ItemHandler.gainItem(craftedItem, (Player) e.getWhoClicked());
                }
            }
        }.runTaskLater(BorderHoarder.plugin, 1);
    }

    // getting an item from a chest/whatever other container
    @EventHandler
    public void inventoryGet(InventoryClickEvent e) {
        if (!(e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.netherWorldName)
                || e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.endWorldName)))
            return;
        if (e.getClickedInventory() == null) return;

        // shift click item from somewhere else
        if (e.getClick().equals(ClickType.SHIFT_LEFT) ||
                e.getClick().equals(ClickType.SHIFT_RIGHT)) {
            if (e.getCurrentItem() == null) return;
            Material clickedItem = e.getCurrentItem().getType();
            new BukkitRunnable() {

                @Override
                public void run() {
                    // wait a tick to see if the player still has the item
                    if (e.getWhoClicked().getInventory().contains(clickedItem)) {
                        ItemHandler.gainItem(clickedItem, (Player) e.getWhoClicked());
                    }
                }
            }.runTaskLater(BorderHoarder.plugin, 1);
            return;
        }

        new BukkitRunnable() {

            @Override
            public void run() {
                // drag over to inventory
                if (e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getCurrentItem() != null) {
                    ItemHandler.gainItem(e.getCurrentItem().getType(), (Player) e.getWhoClicked());
                }
            }
        }.runTaskLater(BorderHoarder.plugin, 1);
    }
}
