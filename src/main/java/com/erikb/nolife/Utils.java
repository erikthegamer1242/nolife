package com.erikb.nolife;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void message(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

}
