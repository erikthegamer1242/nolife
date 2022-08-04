package com.erikb.nolife;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Nolife extends JavaPlugin {

    private static Nolife instance;

    private final FileConfiguration config = this.getConfig();

    private DiscordClient discordClient;

    @Override
    public void onEnable() {
        instance = this;
        this.getLogger().info("Hello world!");
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("nolife")).setExecutor(new Commands());

        MySQL mySQL = new MySQL(Objects.requireNonNull(this.config.getConfigurationSection("mysql")));
        if(config.getBoolean("mysql.enabled")) {
            mySQL.setupConnection();

            long period = config.getInt("mysql.refresh-time") * 60 * 20L;
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, mySQL, 0, period);
        }

        this.discordClient = new DiscordClient(Objects.requireNonNull(this.config.getConfigurationSection("discord")));
        if(config.getBoolean("discord.enabled")) {
            this.discordClient.setup();
        }
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Goodbye world!");
        this.discordClient.shutdown();
    }

    public static Nolife getInstance() {
        return instance;
    }

}
