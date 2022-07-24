package com.erikb.nolife;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.Arrays;

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
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) "
                    + "FROM information_schema.tables "
                    + "WHERE table_name = ?"
                    + "LIMIT 1;");
            stmt.setString(1, tableName);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                String CREATE_TABLE_SQL = "CREATE TABLE " + tableName + " ("
                        + "playerName VARCHAR(17) NOT NULL,"
                        + "playerHours DOUBLE NOT NULL,"
                        + "PRIMARY KEY (playerName))";
                stmt = conn.prepareStatement(CREATE_TABLE_SQL);
                stmt.executeUpdate();
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try(Connection conn = DriverManager.getConnection(databaseUrl, username, password)) {
            Arrays.sort(Bukkit.getOfflinePlayers(), PlayerSorter::comparePlayers);

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
