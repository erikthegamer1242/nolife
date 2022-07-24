package com.erikb.nolife;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class PlayerSorter {

    public static int comparePlayers(OfflinePlayer player1, OfflinePlayer player2) {
        int playTimeP1 = player1.getStatistic(Statistic.PLAY_ONE_MINUTE);
        int playTimeP2 = player2.getStatistic(Statistic.PLAY_ONE_MINUTE);
        return Integer.compare(playTimeP2, playTimeP1);
    }

}
