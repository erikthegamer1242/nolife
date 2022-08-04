package com.erikb.nolife;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.Arrays;
import java.util.List;

public class DiscordEventListener extends ListenerAdapter {

    private Message message;
    private MessageChannel channel;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        message = event.getMessage();
        if(message.getContentRaw().equals("!nolife")) {
            channel = event.getChannel();

            List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
            players.sort(PlayerSorter::comparePlayers);

            for (OfflinePlayer p : players) {
                double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
                channel.sendMessage("Player " + p.getName() + " wasted " + String.format("%.2f", playerHours) + " hours").complete();
            }
        }
    }
}
