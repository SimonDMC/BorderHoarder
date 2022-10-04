package com.simondmc.borderhoarder.cmd;

import com.simondmc.borderhoarder.game.ItemDictionary;
import com.simondmc.borderhoarder.game.ItemHandler;
import com.simondmc.borderhoarder.util.DataTypeUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.stream.Collectors;

public class IsCollectedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("iscollected")) {
            if (args.length < 1) {
                sender.sendMessage("§cUsage: /iscollected <item>");
                return true;
            }

            String itemName = DataTypeUtil.joinStringArray(args, " ");
            Material item;

            try {
                item = Material.valueOf(itemName.toUpperCase());
                if (!ItemDictionary.getDict().containsKey(item)) {
                    sender.sendMessage("§c" + ItemDictionary.getDict().get(item) + " is unobtainable!");
                    return true;
                }
                if (ItemHandler.getCollectedItems().containsKey(item)) {
                    sender.sendMessage("§a" + ItemDictionary.getDict().get(item) + " has been collected!");
                } else {
                    sender.sendMessage("§c" + ItemDictionary.getDict().get(item) + " has not been collected!");
                }
            } catch (Exception e) {
                if (ItemDictionary.getDict().values().stream().map(String::toLowerCase).toList().contains(itemName.toLowerCase())) {
                    item = DataTypeUtil.getKeyByCaseInsensitiveString(ItemDictionary.getDict(), itemName);
                    if (ItemHandler.getCollectedItems().containsKey(item)) {
                        sender.sendMessage("§a" + ItemDictionary.getDict().get(item) + " has been collected!");
                    } else {
                        sender.sendMessage("§c" + ItemDictionary.getDict().get(item) + " has not been collected!");
                    }
                } else {
                    sender.sendMessage("§c'" + itemName + "' is not a valid item!");
                }
            }

            return true;
        }
        return false;
    }
}
