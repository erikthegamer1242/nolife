package com.erikb.nolife;

import net.dv8tion.jda.api.JDA;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public class Nolife extends JavaPlugin {

    private static Nolife instance;

    private final FileConfiguration config = this.getConfig();

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
        Discord discord = new Discord(Objects.requireNonNull(this.config.getConfigurationSection("discord")));
        if(config.getBoolean("discord.enabled")) {
                discord.setup();
        }
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Goodbye world!");
        Discord.jdaBuilder.shutdown();
    }

    public static Nolife getInstance() {
        return instance;
    }

}
