package com.erikb.nolife;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class Nolife extends JavaPlugin{
    FileConfiguration config = this.getConfig();
    ScheduledExecutorService executorMySQL = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void onEnable() {
        System.out.println("Hello World!");
        Objects.requireNonNull(getCommand("nolife")).setExecutor(new Commands());
        saveDefaultConfig();
        List<OfflinePlayer> players = Arrays.asList(Bukkit.getOfflinePlayers());
        if(config.getBoolean("mysql.enabled")) {
            MySQL.setupConnection(this);
            executorMySQL.scheduleAtFixedRate(new MySQL(players), 0, config.getInt("mysql.refresh-time"), TimeUnit.MINUTES);
        }

    }

    @Override
    public void onDisable() {
        System.out.println("Goodbye World!");
        if(config.getBoolean("mysql.enabled")) {
            executorMySQL.shutdown();
        }
    }
}
