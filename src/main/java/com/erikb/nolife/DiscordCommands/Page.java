package com.erikb.nolife.DiscordCommands;

import com.erikb.nolife.Utils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class Page {

    public static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static void page(String[] args, Message message, List<OfflinePlayer> players, MessageChannel channel) {
        String messageSend = "";
        if (args[1].equals("page")) {
            if (message.getContentRaw().matches(".*[0-9].*")) {
                if (onlyDigits(args[2], args[2].length())) {
                    int numberOfPages = (players.size() / 10) + 1;
                    if (Integer.parseInt(args[2]) <= numberOfPages) {
                        int pageNum = Integer.parseInt(args[2]);
                        messageSend = Utils.stringBuilder(players, pageNum, numberOfPages);
                    }
                    else messageSend = messageSend + "Invalid page number. Valid pages are from 1 to " + numberOfPages;
                }
                channel.sendMessage(messageSend).complete();
            }
        }
    }
}
