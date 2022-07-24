package com.erikb.nolife;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.List;

public class MySQL implements Runnable {
    private static String DB_URL = "";
    private static String USER = "";
    private static String PASS = "";
    private static String TABLE = "";

    static void setupConnection(Nolife config) {
        DB_URL = "jdbc:mysql://" + config.getConfig().getString("mysql.serverHost") + ":" + config.getConfig().getString("mysql.serverPort") + "/" + config.getConfig().getString("mysql.database");
        USER = config.getConfig().getString("mysql.username");
        PASS = config.getConfig().getString("mysql.password");
        TABLE = config.getConfig().getString("mysql.tablePrefix") + "_stats";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) "
                    + "FROM information_schema.tables "
                    + "WHERE table_name = ?"
                    + "LIMIT 1;");
            stmt.setString(1, TABLE);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                String CREATE_TABLE_SQL="CREATE TABLE " + TABLE + " ("
                        + "playerName VARCHAR(17) NOT NULL,"
                        + "playerHours DOUBLE NOT NULL,"
                        + "PRIMARY KEY (playerName))";
                stmt = conn.prepareStatement(CREATE_TABLE_SQL);
                stmt.executeUpdate();
                rs.close();
                stmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private final List<OfflinePlayer> players;
    public MySQL(List<OfflinePlayer> players) {this.players = players;}

    public void run() {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            players.sort((p1, p2) -> {
                int playTimeP1 = p1.getStatistic(Statistic.PLAY_ONE_MINUTE);
                int playTimeP2 = p2.getStatistic(Statistic.PLAY_ONE_MINUTE);
                return Integer.compare(playTimeP2, playTimeP1);
            });
            for (OfflinePlayer p : players) {
                double playerHours = (double) p.getStatistic(Statistic.PLAY_ONE_MINUTE) / (20 * 60 * 60);
                DecimalFormat df = new DecimalFormat("#.##");
                playerHours = Double.parseDouble(df.format(playerHours));
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `" + TABLE + "` (`playerName`, `playerHours`) VALUES (?, ?)  ON DUPLICATE KEY UPDATE `playerHours`=?");
                preparedStatement.setString(1, p.getName());
                preparedStatement.setDouble(2, playerHours);
                preparedStatement.setDouble(3, playerHours);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database updated");
    }

}
