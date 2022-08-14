package com.erikb.nolife;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public static String stringBuilder(List<OfflinePlayer> players, int pageNum, int numberOfPages) {
        StringBuilder messageSend = new StringBuilder();
        List<OfflinePlayer> playersSubList = players.subList(((10 * pageNum) - 10) , Math.min(((10 * pageNum) - 1), players.size()));
        messageSend.append("Displaying page ").append(pageNum).append(" of ").append(numberOfPages).append(" pages\n");
        int index = 1;
        for (OfflinePlayer p : playersSubList) {
            double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
            messageSend.append(index).append(". Player ").append(p.getName()).append(" wasted ").append(String.format("%.2f", playerHours)).append(" hours\n");
            index++;
        }
        return messageSend.toString();
    }

}
