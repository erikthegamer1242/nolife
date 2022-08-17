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

    public static String playerPageBuilder(List<OfflinePlayer> players, String pageNumArg, boolean fromMinecraftConsole) {
        StringBuilder messageSend = new StringBuilder();
        if (onlyDigits(pageNumArg, pageNumArg.length())) {
            int numberOfPages = (players.size() / 10) + 1;
            if (Integer.parseInt(pageNumArg) <= numberOfPages) {
                int pageNum = Integer.parseInt(pageNumArg);
                messageSend.append("Displaying page ").append(pageNum).append(" of ").append(numberOfPages).append(" pages\n");
                List<OfflinePlayer> playersSubList = players.subList(((10 * pageNum) - 10) , Math.min(((10 * pageNum)), players.size()));
                int index = (10 * pageNum) - 9;
                for (OfflinePlayer p : playersSubList) {
                    double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
                    if(fromMinecraftConsole) messageSend.append("&9").append(index).append(". Player&b ").append(p.getName()).append(" &9 wasted &6 ").append(String.format("%.2f", playerHours)).append(" &9 hours\n");
                    else messageSend.append(index).append(". Player ").append(p.getName()).append(" wasted ").append(String.format("%.2f", playerHours)).append(" hours\n");
                    index++;
                }
                return messageSend.toString();
            }
            return "Invalid page number. Valid pages are from 1 to " + numberOfPages;
        }
        return "Syntax error, try again";
    }

    public static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
}
