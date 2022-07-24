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
        if(!(sender instanceof Player)) return true;

        List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
        players.sort(PlayerSorter::comparePlayers);

        for (OfflinePlayer p : players) {
            double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
            Utils.message(sender, "&b" + p.getName() + "&9 wasted &6" + String.format("%.2f", playerHours) + "&9 hours");
        }

        return true;
    }
}
