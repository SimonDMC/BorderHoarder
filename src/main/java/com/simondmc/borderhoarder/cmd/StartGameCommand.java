package com.simondmc.borderhoarder.cmd;

import com.simondmc.borderhoarder.game.ItemHandler;
import com.simondmc.borderhoarder.world.BorderWorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class StartGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("startbordergame")) {

            // create world with seed
            long seed;
            if (args.length >= 1) {
                try {
                    seed = Long.parseLong(args[0]);
                } catch (Exception e) {
                    sender.sendMessage("§cThat's not a valid seed!");
                    return true;
                }
            } else {
                seed = new Random().nextLong();
            }

            // reset data
            ItemHandler.resetCollectedItems();

            new BorderWorldCreator(seed);
            return true;
        }
        return false;
    }
}
