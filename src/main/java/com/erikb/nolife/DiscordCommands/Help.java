package com.erikb.nolife.DiscordCommands;

import net.dv8tion.jda.api.entities.MessageChannel;

public class Help {
    public static void help(MessageChannel channel) {
        channel.sendMessage("Use ``!nolife page <pageNum>`` to display up to 10 players on a certain page sorted by time played.").complete();
    }
}
