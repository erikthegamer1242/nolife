package com.erikb.nolife;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
            players.sort((p1, p2) -> {
                int playTimeP1 = p1.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int playTimeP2 = p2.getStatistic(Statistic.PLAY_ONE_MINUTE);
                return Integer.compare(playTimeP2, playTimeP1);
            });
            for (OfflinePlayer p : players) {
                double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
                Utils.message(sender, "&b" + p.getName() + "&9 wasted &6" + String.format("%.2f", playerHours) + "&9 hours");
            }
        }
        return true;
    }
}
