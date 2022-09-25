package com.simondmc.borderhoarder.cmd;

import com.simondmc.borderhoarder.inventory.InventoryBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewCollectedItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("viewcollecteditems")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                p.openInventory(InventoryBuilder.buildCompletedInventory(1));
            }
            return true;
        }
        return false;
    }
}
