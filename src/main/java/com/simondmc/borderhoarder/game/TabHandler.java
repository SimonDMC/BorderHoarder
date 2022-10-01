package com.simondmc.borderhoarder.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.Collections;
import java.util.stream.Collectors;

public class TabHandler {
    public static void updateTab() {
        Objective o = Bukkit.getScoreboardManager().getMainScoreboard().getObjective("borderhoarder") == null ?
                Bukkit.getScoreboardManager().getMainScoreboard().registerNewObjective("borderhoarder", Criteria.DUMMY, "BorderHoarder") :
                Bukkit.getScoreboardManager().getMainScoreboard().getObjective("borderhoarder");
        o.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        // set player scores
        for (Player p : Bukkit.getOnlinePlayers()) {
            // get amount of items collected by UUID
            o.getScore(p).setScore(Collections.frequency(ItemHandler.getCollectedItems().values().stream().map(
                    Entity::getUniqueId
            ).collect(Collectors.toList()), p.getUniqueId()));
        }
    }
}
