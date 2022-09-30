package com.simondmc.borderhoarder.cmd;

import com.simondmc.borderhoarder.util.PlayerUtil;
import com.simondmc.borderhoarder.world.BorderWorldCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinGameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("joinbordergame")) {
            if (!(sender instanceof Player p)) return true;
            if (p.getWorld().getName().equals(BorderWorldCreator.worldName)) {
                p.sendMessage("§cYou're already in the game!");
                return true;
            }
            if (Bukkit.getWorld(BorderWorldCreator.worldName) != null) {
                p.teleport(Bukkit.getWorld(BorderWorldCreator.worldName).getSpawnLocation().clone().add(.5, 0, .5));
                p.setBedSpawnLocation(Bukkit.getWorld(BorderWorldCreator.worldName).getSpawnLocation().clone().add(.5, 0, .5), true);
                PlayerUtil.preparePlayer(p);
                p.sendMessage("§aSuccessfully joined the game!");
            } else {
                p.sendMessage("§cA border game has not started yet, use §a/startbordergame §cto start one!");
            }
            return true;
        }
        return false;
    }
}
