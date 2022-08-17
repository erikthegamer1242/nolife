package com.erikb.nolife;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.List;

import static com.erikb.nolife.DiscordCommands.Help.help;
import static com.erikb.nolife.DiscordCommands.Page.page;

public class DiscordEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Message message = event.getMessage();
        String[] args = message.getContentRaw().split("\\s+");
        MessageChannel channel = event.getChannel();
        List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
        players.sort(PlayerSorter::comparePlayers);

        if(!args[0].equals("!nolife"))
            return;
        if(args.length > 1) {
            if (args[1].equals("page")) {
                page(args, players, channel);
            }
            if (args[1].equals("help")) {
                help(channel);
            }
        }
    }
}
