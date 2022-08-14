package com.erikb.nolife;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class MySQL implements Runnable {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    private final String databaseUrl;
    private final String username;
    private final String password;
    private final String tableName;

    public MySQL(ConfigurationSection section) {
        this.databaseUrl = "jdbc:mysql://" + section.getString("serverHost") + ":" + section.getString("serverPort") + "/" + section.getString("database");
        this.username = section.getString("username");
        this.password = section.getString("password");
        this.tableName = section.getString("tablePrefix") + "_stats";
    }

    void setupConnection() {
        try(Connection conn = DriverManager.getConnection(databaseUrl, username, password)) {
            PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName + " ( " +
                    "playerName VARCHAR(17) NOT NULL," +
                    "playerHours DOUBLE NOT NULL," +
                    "PRIMARY KEY (playerName))");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try(Connection conn = DriverManager.getConnection(databaseUrl, username, password)) {
            for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
                playerHours = Double.parseDouble(df.format(playerHours));
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `" + tableName + "` (`playerName`, `playerHours`) VALUES (?, ?)  ON DUPLICATE KEY UPDATE `playerHours`=?");
                preparedStatement.setString(1, p.getName());
                preparedStatement.setDouble(2, playerHours);
                preparedStatement.setDouble(3, playerHours);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Nolife.getInstance().getLogger().info("Database saved");
    }

}
