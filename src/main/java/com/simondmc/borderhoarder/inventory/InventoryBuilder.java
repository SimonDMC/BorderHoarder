package com.simondmc.borderhoarder.inventory;

import com.simondmc.borderhoarder.game.ItemDictionary;
import com.simondmc.borderhoarder.game.ItemHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryBuilder {

    public static Inventory buildCompletedInventory(int page) {
        final int pages = (int) Math.ceil(ItemHandler.getCollectedItems().size() / 45d);

        Inventory inv = Bukkit.createInventory(null, 54, "Collected Items§a §r- Page " + page + "/" + (pages == 0 ? 1 : pages));

        ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("§k");
        i.setItemMeta(m);
        for (int j = 45; j < 54; j++) {
            inv.setItem(j, i);
        }

        if (page > 1) {
            i = new ItemStack(Material.ARROW);
            m = i.getItemMeta();
            m.setDisplayName("§aPrevious Page");
            i.setItemMeta(m);
            inv.setItem(45, i);
        }

        if (page < pages) {
            i = new ItemStack(Material.ARROW);
            m = i.getItemMeta();
            m.setDisplayName("§aNext Page");
            i.setItemMeta(m);
            inv.setItem(53, i);
        }

        // fill contents
        int start = (page - 1) * 45;
        int end = Math.min(start + 45, ItemHandler.getCollectedItems().size());
        for (int j = start; j < end; j++) {
            Material itemType = ItemHandler.getCollectedItems().get(j);
            i = new ItemStack(itemType);
            inv.addItem(i);
        }

        return inv;
    }

    public static Inventory buildMissingInventory(int page) {
        final List<Material> missingItems = ItemDictionary.getDict().keySet().stream()
                .filter(item -> !ItemHandler.getCollectedItems().contains(item))
                .collect(Collectors.toList());
        // sort list
        Collections.sort(missingItems);
        final int pages = (int) Math.ceil(missingItems.size() / 45d);

        Inventory inv = Bukkit.createInventory(null, 54, "Missing Items§a §r- Page " + page + "/" + pages);

        ItemStack i = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("§k");
        i.setItemMeta(m);
        for (int j = 45; j < 54; j++) {
            inv.setItem(j, i);
        }

        if (page > 1) {
            i = new ItemStack(Material.ARROW);
            m = i.getItemMeta();
            m.setDisplayName("§aPrevious Page");
            i.setItemMeta(m);
            inv.setItem(45, i);
        }

        if (page < pages) {
            i = new ItemStack(Material.ARROW);
            m = i.getItemMeta();
            m.setDisplayName("§aNext Page");
            i.setItemMeta(m);
            inv.setItem(53, i);
        }

        // fill contents
        int start = (page - 1) * 45;
        int end = Math.min(start + 45, missingItems.size());
        for (int j = start; j < end; j++) {
            Material itemType = missingItems.get(j);
            i = new ItemStack(itemType);
            inv.addItem(i);
        }

        return inv;
    }
}
