package com.erikb.nolife.DiscordCommands;

import com.erikb.nolife.Utils;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class Page {

    public static void page(String[] args, List<OfflinePlayer> players, MessageChannel channel) {
        if (args.length == 3) {
            channel.sendMessage(Utils.playerPageBuilder(players, args[2], false)).complete();
        }
    }
}
