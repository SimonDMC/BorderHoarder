package com.simondmc.borderhoarder.game;

import com.simondmc.borderhoarder.BorderHoarder;
import com.simondmc.borderhoarder.inventory.InventoryBuilder;
import com.simondmc.borderhoarder.world.BorderWorldCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlayerListener implements Listener {

    @EventHandler
    public void cancelViewClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Collected Items§a")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
            // current page
            int page = Integer.parseInt(e.getView().getTitle().split("/")[0].split("-")[1].substring(6));
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aNext Page")) {
                e.getWhoClicked().openInventory(InventoryBuilder.buildCompletedInventory(page + 1));
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aPrevious Page")) {
                e.getWhoClicked().openInventory(InventoryBuilder.buildCompletedInventory(page - 1));
            }
        }
        if (e.getView().getTitle().contains("Missing Items§a")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
            // current page
            int page = Integer.parseInt(e.getView().getTitle().split("/")[0].split("-")[1].substring(6));
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aNext Page")) {
                e.getWhoClicked().openInventory(InventoryBuilder.buildMissingInventory(page + 1));
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aPrevious Page")) {
                e.getWhoClicked().openInventory(InventoryBuilder.buildMissingInventory(page - 1));
            }
        }
    }

    @EventHandler
    public void pickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getEntity().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getEntity().getLocation().getWorld().getName().equals("world_the_nether")
                || e.getEntity().getLocation().getWorld().getName().equals("world_the_end"))) return;
        ItemHandler.gainItem(e.getItem().getItemStack().getType(), (Player) e.getEntity());
    }

    @EventHandler
    public void craft(CraftItemEvent e) {
        if (!(e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getWhoClicked().getLocation().getWorld().getName().equals("world_the_nether")
                || e.getWhoClicked().getLocation().getWorld().getName().equals("world_the_end"))) return;
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

    @EventHandler
    public void inventoryGet(InventoryClickEvent e) {
        if (!(e.getWhoClicked().getLocation().getWorld().getName().equals(BorderWorldCreator.worldName) ||
                e.getWhoClicked().getLocation().getWorld().getName().equals("world_nether")
                || e.getWhoClicked().getLocation().getWorld().getName().equals("world_the_end"))) return;
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

    // clear title if kicked during generation
    @EventHandler
    public void joinBorderWorld(PlayerJoinEvent e) {
        if (e.getPlayer().getWorld().getName().equals(BorderWorldCreator.worldName)) {
            e.getPlayer().resetTitle();
        }
    }

    // force respawn position if bed is destroyed
    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        // bed broken
        if (e.getPlayer().getWorld().getName().equals(BorderWorldCreator.worldName) && e.getPlayer().getBedSpawnLocation() == null) {
            e.setRespawnLocation(Bukkit.getWorld(BorderWorldCreator.worldName).getSpawnLocation());
        }
        // spawn obstructed
        if (e.getPlayer().getWorld().getName().equals(BorderWorldCreator.worldName) && e.getPlayer().getWorld().getBlockAt(e.getPlayer().getWorld().getSpawnLocation()).getType() != Material.AIR) {
            e.setRespawnLocation(Bukkit.getWorld(BorderWorldCreator.worldName).getHighestBlockAt(Bukkit.getWorld(BorderWorldCreator.worldName).getSpawnLocation()).getLocation().add(0.5, 1, 0.5));
        }
    }

    Map<Material, Material> saplings = new HashMap<>() {{
        put(Material.OAK_LEAVES, Material.OAK_SAPLING);
        put(Material.SPRUCE_LEAVES, Material.SPRUCE_SAPLING);
        put(Material.BIRCH_LEAVES, Material.BIRCH_SAPLING);
        put(Material.JUNGLE_LEAVES, Material.JUNGLE_SAPLING);
        put(Material.ACACIA_LEAVES, Material.ACACIA_SAPLING);
        put(Material.DARK_OAK_LEAVES, Material.DARK_OAK_SAPLING);
    }};

    // guarantee sapling and seed on first break
    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (!GameData.getBoolean("dropped-first-seed") &&
                e.getBlock().getWorld().getName().equals(BorderWorldCreator.worldName) &&
                e.getBlock().getType().equals(Material.GRASS)) {
            e.setDropItems(false);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.WHEAT_SEEDS));
            GameData.set("dropped-first-seed", true);
        }
        if (!GameData.getBoolean("dropped-first-sapling") &&
                e.getBlock().getWorld().getName().equals(BorderWorldCreator.worldName) &&
                saplings.containsKey(e.getBlock().getType())) {
            e.setDropItems(false);
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(saplings.get(e.getBlock().getType())));
            GameData.set("dropped-first-sapling", true);
        }
    }
}
