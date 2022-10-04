package com.simondmc.borderhoarder.cmd;

import com.simondmc.borderhoarder.game.ItemDictionary;
import com.simondmc.borderhoarder.game.ItemHandler;
import com.simondmc.borderhoarder.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IsCollectedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("iscollected")) {
            if (args.length < 1) {
                sender.sendMessage("§cUsage: /iscollected <item>");
                return true;
            }

            String itemName = StringUtil.joinStringArray(args, " ");
            Material item;

            try {
                item = Material.valueOf(itemName.toUpperCase());
                if (!ItemDictionary.getDict().containsKey(item)) {
                    sender.sendMessage("§c" + itemName + " is unobtainable!");
                    return true;
                }
                if (ItemHandler.getCollectedItems().containsKey(item)) {
                    sender.sendMessage("§a" + itemName + " has been collected!");
                } else {
                    sender.sendMessage("§c" + itemName + " has not been collected!");
                }
            } catch (Exception e) {
                if (ItemDictionary.getDict().containsValue(itemName)) {
                    item = StringUtil.getKeyByValue(ItemDictionary.getDict(), itemName);
                    if (ItemHandler.getCollectedItems().containsKey(item)) {
                        sender.sendMessage("§a" + itemName + " has been collected!");
                    } else {
                        sender.sendMessage("§c" + itemName + " has not been collected!");
                    }
                } else {
                    sender.sendMessage("§c" + itemName + " is not a valid item!");
                }
            }

            return true;
        }
        return false;
    }
}
